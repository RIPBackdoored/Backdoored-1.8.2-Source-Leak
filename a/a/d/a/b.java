package a.a.d.a;

import com.backdoored.event.*;
import javax.annotation.*;

public class b extends BackdooredEvent
{
    @Nullable
    public Boolean by;
    
    public b(@Nullable final Boolean by) {
        super();
        this.by = by;
    }
}
