package a.a.f.b;

import java.util.*;
import a.a.f.a.*;

public class b extends c
{
    public b() {
        super();
    }
    
    @Override
    public void a() {
        for (final a.a.f.a.c c : a.a.f.a.c.d()) {
            if (c.a().getEnabled()) {
                c.fl = "FF0000";
            }
            else {
                c.fl = "FFFFFF";
            }
            if (c.fd) {
                c.c().forEach(e -> e.fj = !e.fj);
                for (final a.a.f.a.c c2 : c.b().b()) {
                    if (c2 != c) {
                        c2.c().forEach(e2 -> e2.fj = false);
                    }
                }
                c.fd = false;
            }
        }
    }
    
    private static /* synthetic */ void a(final e e) {
        e.fj = false;
    }
    
    private static /* synthetic */ void b(final e e) {
        e.fj = !e.fj;
    }
}
