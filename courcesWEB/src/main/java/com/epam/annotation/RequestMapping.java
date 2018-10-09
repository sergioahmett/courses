package com.epam.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation that contains request to method mapping.
 * Consists of:
 *  path - path to method. If you need to add path variable you should add /{variable} to your path
 *  pathVariable - name of variavle where the path variable will be saved
 *  requairedPAram - parameters that the request must have for it to access the method
 *  requiredParamVariable - Sets the values of the required parameters. To access the method,
 *  the parameters must match the request. The number of parameters must be <= required parameters count
 * @author Sergey Ahmetshin
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RequestMapping {
    String path() default "";

    String pathVariable() default "";

    String[] requiredParam() default {};

    String[] requiredParamVariable() default {};
}
