package a.a.h;

import a.a.*;
import java.util.*;
import a.a.h.b.a.*;
import javax.annotation.*;

public class a implements e
{
    public final List<a.a.h.a.a> yx;
    
    public a() {
        super();
        (this.yx = new ArrayList<a.a.h.a.a>()).add(new d());
        this.yx.add(new l());
        this.yx.add(new f());
        this.yx.add(new i());
        this.yx.add(new k());
        this.yx.add(new g());
        this.yx.add(new c());
        this.yx.add(new j());
        this.yx.add(new a.a.h.b.a.e());
        this.yx.add(new h());
        this.yx.add(new b());
    }
    
    public void a(final a.a.h.a.a a) {
        if (this.yx.contains(a)) {
            this.yx.remove(a);
            this.yx.add(a);
        }
    }
    
    @Nullable
    public a.a.h.a.a a(final int n, final int n2) {
        for (int i = this.yx.size() - 1; i >= 0; --i) {
            final a.a.h.a.a a = this.yx.get(i);
            if (a.a(n, n2)) {
                return a;
            }
        }
        return null;
    }
}
