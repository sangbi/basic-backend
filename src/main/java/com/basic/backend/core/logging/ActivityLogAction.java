package com.basic.backend.core.logging;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActivityLogAction {
    String actionType();
    String message() default "";

    boolean includeArgs() default true;
    boolean includeResult() default false;
}
