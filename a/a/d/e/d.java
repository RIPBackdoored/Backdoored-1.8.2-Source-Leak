package a.a.d.e;

import com.backdoored.event.*;
import javax.annotation.*;

public class d extends BackdooredEvent
{
    @Nullable
    public Integer el;
    
    public d(@Nullable final Integer el) {
        super();
        this.el = el;
    }
}
