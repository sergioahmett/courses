package com.epam.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Contain the path by which the processor will process requests
 * 
 * @author Sergey Ahmetshin
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
@Documented
public @interface Processor {
    String path();
}
