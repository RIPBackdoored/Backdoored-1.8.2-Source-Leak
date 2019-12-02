package com.google.api.client.repackaged.com.google.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@GwtCompatible
public @interface GwtCompatible {
    boolean serializable() default false;
    
    boolean emulated() default false;
}
