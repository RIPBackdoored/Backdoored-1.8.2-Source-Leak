package a.a.d.b.d.a;

import com.backdoored.event.*;
import java.awt.*;

public class b extends BackdooredEvent
{
    public Color cp;
    
    public b(final int n) {
        this(new Color(n));
    }
    
    public b(final Color cp) {
        super();
        this.cp = cp;
    }
}
