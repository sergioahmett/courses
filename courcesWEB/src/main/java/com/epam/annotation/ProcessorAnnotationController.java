package com.epam.annotation;

import java.util.regex.Pattern;

/**
 * @author Sergey Ahmetshin
 *
 */
public class ProcessorAnnotationController {
    
    /**
     * The method checks whether the passed class is the correct processor or not.
     * 
     * @param clazz - class of checking object
     * @param webPath - requested path
     * @return true if class necessary processor, else - false
     */
    
    public boolean checkPath(Class<?> clazz, String webPath){
        if (clazz.isAnnotationPresent(Processor.class)) {
            Processor proc = clazz.getAnnotation(Processor.class);
            String path = proc.path();
            if (path != null && !path.isEmpty()) {
                return Pattern.compile("^" + path + ".*").matcher(webPath).find();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
