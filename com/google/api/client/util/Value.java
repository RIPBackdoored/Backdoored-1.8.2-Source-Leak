package com.google.api.client.util;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value() default "##default";
}
