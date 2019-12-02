package com.google.api.client.util;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    String value() default "##default";
}
