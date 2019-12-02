package a.a.f.b;

import org.lwjgl.input.*;
import a.a.f.a.*;
import java.util.*;

public class a extends c
{
    private b go;
    
    public a() {
        super();
    }
    
    @Override
    public void a() {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            final Iterator<b> iterator = b.d().iterator();
            while (iterator.hasNext() && !iterator.next().fv) {}
            for (final b b : b.d()) {
                if (a.a.f.a.gh > b.fe && a.a.f.a.gi > b.ff && a.a.f.a.gh < b.fe + 100 && a.a.f.a.gi < b.ff + 20) {
                    this.a(b);
                    break;
                }
                for (final a.a.f.a.c c2 : b.b()) {
                    if (a.a.f.a.gh > c2.fe && a.a.f.a.gi > c2.ff && a.a.f.a.gh < c2.fe + c2.fg && a.a.f.a.gi < c2.ff + c2.fh) {
                        this.a(b);
                        break;
                    }
                    for (final e e2 : c2.c()) {
                        if (a.a.f.a.gh > e2.fe && a.a.f.a.gi > e2.ff && a.a.f.a.gh < e2.fe + e2.fg && a.a.f.a.gi < e2.ff + e2.fh) {
                            this.a(b);
                            break;
                        }
                    }
                }
            }
        }
        this.b();
        for (final b b2 : b.d()) {
            if (Mouse.isButtonDown(0) && a.a.f.a.gh > b2.fe && a.a.f.a.gi > b2.ff && a.a.f.a.gh < b2.fe + 100 && a.a.f.a.gi < b2.ff + 20 && !b2.fv) {
                b2.fw = a.a.f.a.gh - b2.fe;
                b2.fx = a.a.f.a.gi - b2.ff;
                b2.fv = true;
            }
            if (b2.fv) {
                b2.fe = a.a.f.a.gh - b2.fw;
                b2.ff = a.a.f.a.gi - b2.fx;
            }
            if (!Mouse.isButtonDown(0)) {
                b2.fv = false;
            }
            if (b2.fv && Mouse.isButtonDown(0)) {
                break;
            }
        }
        for (final b b3 : b.d()) {
            if (!b3.fd) {
                for (final a.a.f.a.c c3 : b3.b()) {
                    c3.fj = false;
                    c3.c().forEach(e -> e.fj = false);
                }
            }
            else {
                b3.b().forEach(c -> c.fj = true);
            }
            a.a.f.a.a a = null;
            for (final a.a.f.a.c c4 : b3.b()) {
                c4.fe = b3.fe;
                if (a != null) {
                    c4.ff = a.ff + a.fh;
                }
                else {
                    c4.ff = b3.ff + 20;
                }
                a = c4;
            }
        }
    }
    
    private void a(final b go) {
        this.go = go;
    }
    
    private void b() {
        if (this.go != null) {
            b.c().remove(this.go);
            b.c().add(this.go);
        }
    }
    
    private static /* synthetic */ void a(final a.a.f.a.c c) {
        c.fj = true;
    }
    
    private static /* synthetic */ void a(final e e) {
        e.fj = false;
    }
}
