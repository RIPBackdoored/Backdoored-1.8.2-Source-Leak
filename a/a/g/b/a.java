package a.a.g.b;

import java.util.*;
import com.backdoored.hacks.*;

public class a implements Comparator<b>
{
    public a() {
        super();
    }
    
    public int a(final BaseHack baseHack, final BaseHack baseHack2) {
        return baseHack.name.compareTo(baseHack2.name);
    }
    
    @Override
    public /* bridge */ int compare(final Object o, final Object o2) {
        return this.a((BaseHack)o, (BaseHack)o2);
    }
}
