package com.epam.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.annotation.HTTPMethod;
import com.epam.processor.AuthProcessor;
import com.epam.processor.ErrorProcessor;

import static com.epam.utils.CodeHandler.*;

/**
 * The class is used to find the method that matches the incoming request.
 * @author Sergey Ahmetshin
 *
 */
public class MethodAnnotationController {
    private static final Logger log = Logger.getLogger(MethodAnnotationController.class);

    /**
     * The main class method. Checks the methods of the passed object for compliance with the request.
     * If it finds a suitable method, then it calls it. Otherwise, redirects to the error page.
     * 
     * @param obj - Processor object
     * @param request
     * @param response
     */
    public void redirectToMethod(Object obj, HttpServletRequest request, HttpServletResponse response) {
        if (obj.getClass().isAnnotationPresent(Processor.class)) {
            Method[] methods = obj.getClass().getMethods();
            Method targetMethod = null;
            for (Method method : methods) {
                if (method.isAnnotationPresent(HTTPMethod.class) && method.isAnnotationPresent(RequestMapping.class)) {
                    String methodType = method.getAnnotation(HTTPMethod.class).method().toString();
                    if (request.getMethod().equals(methodType)) {
                        if (checkMethod(obj.getClass().getAnnotation(Processor.class),
                                method.getAnnotation(RequestMapping.class), request)) {
                            targetMethod = method;
                        }
                    }
                }
            }
            if (targetMethod != null) {
                try {
                    log.debug("Method found. Invoking method - > " + targetMethod);
                    targetMethod.invoke(obj, request, response);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    log.debug("Error in method. Wrong invoke parameters. invoking method - > " + targetMethod);
                    ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
                }
            } else {
                log.debug("Error in method. Requesting method not found. Parameter request -> " + AuthProcessor.getInstance().getFullURL(request));
                ErrorProcessor.getInstance().getErrorPage(request, response, METHOD_NOT_SUPPORT);
            }
        } else {
            log.debug("Could not check object methods, because it is not procesor");
            ErrorProcessor.getInstance().getErrorPage(request, response, ERROR);
        }
    }

    /**
     * Checks the method against the request.
     * 
     * @param procAnnotation - Processor annotation class
     * @param requestAnnotation - RequestMapping annotation class 
     * @param request - http request
     * @return true if method matches the request, else - false
     */
    private boolean checkMethod(Processor procAnnotation, RequestMapping requestAnnotation, HttpServletRequest request) {
        if (checkPath(procAnnotation.path(), requestAnnotation.path(), request.getRequestURI(),
                !(requestAnnotation.pathVariable().length() == 0))) {
            if (requestAnnotation.requiredParam().length != 0) {
                if (checkParam(request.getParameterMap(), requestAnnotation.requiredParam(),
                        requestAnnotation.requiredParamVariable())) {
                    setPathVariable(procAnnotation.path(), requestAnnotation.path(), requestAnnotation.pathVariable(),
                            request);
                    return true;
                }
            } else {
                setPathVariable(procAnnotation.path(), requestAnnotation.path(), requestAnnotation.pathVariable(),
                        request);
                return true;
            }
        }
        return false;
    }

    /**
     * Method parse and set the path variable to the request attributes, if necessary.
     * @param procPath - processor path
     * @param methodPath - method path
     * @param pathVariable - method pathVariable
     * @param request - http request
     */
    private void setPathVariable(String procPath, String methodPath, String pathVariable, HttpServletRequest request) {
        if (!pathVariable.isEmpty()) {
            String variable = request.getRequestURI().replace(procPath + methodPath.replace("/{variable}", "") + "/",
                    "");
            request.setAttribute(pathVariable, variable);
        }
    }

    /**
     * The method checks the compliance of parameters.
     * 
     * @param webParam - map of request parameters
     * @param annoatationParam - array of method parameters
     * @param annotationVariable - 
     * @return true if parameters compliance, else - false
     */
    private boolean checkParam(Map<String, String[]> webParam, String[] annoatationParam, String[] annotationVariable) {
        if (webParam.keySet().containsAll(Arrays.asList(annoatationParam))) {
            if (!(annoatationParam.length < annotationVariable.length)) {
                for (int i = 0; i < annotationVariable.length; i++) {
                    if (!webParam.get(annoatationParam[i])[0].equals(annotationVariable[i])) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The method checks the path for compliance with the request.
     * 
     * @param procPath - Processor path
     * @param methodPath - method path
     * @param webPath - requested path
     * @param pathVariable - âoes the method contain a path variable
     * @return true if path matches the request, else - false
     */
    private boolean checkPath(String procPath, String methodPath, String webPath, boolean pathVariable) {
        String testPath = "^" + procPath
                + (pathVariable ? methodPath.replace("/{variable}", "") + "(\\/[\\w\\-_]{0,50}){1,1}$"
                        : methodPath + "$");
        return Pattern.compile(testPath).matcher(webPath).find();
    }
}