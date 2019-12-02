package a.a.g.b;

import java.lang.annotation.*;
import javax.annotation.*;
import com.backdoored.gui.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface a {
    @Nonnull
    String name();
    
    @Nonnull
    String description();
    
    @Nonnull
    CategoriesInit category();
    
    boolean defaultOn() default false;
    
    boolean defaultIsVisible() default true;
    
    String defaultBind() default "NONE";
}
