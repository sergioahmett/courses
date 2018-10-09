package com.epam.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.epam.enums.HttpMethod;

/**
 * The annotation stores the http request method.
 * @author Sergey Ahmetshin
 *
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface HTTPMethod {
    HttpMethod method();
}
