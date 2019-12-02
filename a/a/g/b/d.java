package a.a.g.b;

import java.util.*;
import com.backdoored.hacks.*;
import a.a.e.*;

public class d implements Comparator<b>
{
    public d() {
        super();
    }
    
    public int a(final BaseHack baseHack, final BaseHack baseHack2) {
        return Integer.compare(c.fontRenderer.getStringWidth(baseHack2.name), c.fontRenderer.getStringWidth(baseHack.name));
    }
    
    @Override
    public /* bridge */ int compare(final Object o, final Object o2) {
        return this.a((BaseHack)o, (BaseHack)o2);
    }
}
