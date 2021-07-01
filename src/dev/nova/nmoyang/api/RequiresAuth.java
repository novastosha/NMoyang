package dev.nova.nmoyang.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAuth {

    boolean required() default true;

}
