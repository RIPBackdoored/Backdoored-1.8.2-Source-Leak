package a.a.f.b;

import a.a.f.a.*;
import a.a.f.*;
import org.lwjgl.input.*;
import java.util.*;

public class d extends c
{
    public d() {
        super();
    }
    
    @Override
    public void a() {
        for (final a.a.f.a.c c : a.a.f.a.c.d()) {
            a.a.f.a.a a = null;
            for (final e e : Objects.requireNonNull(c.c())) {
                final Double value = (a.a.f.a.gh - e.fe) / Integer.valueOf(e.fg).doubleValue();
                switch (d$a.gp[e.a().c().ordinal()]) {
                    case 1: {
                        if (e.fc) {
                            e.fi = "Bind: ...";
                            if (Keyboard.getEventKeyState()) {
                                e.a().a(Keyboard.getKeyName(Keyboard.getEventKey()));
                                e.fi = "Bind: " + e.a().getValInt();
                                e.fc = false;
                                c.fc = false;
                                e.gg = true;
                            }
                            if (e.fd) {
                                e.a().a("NONE");
                                e.fi = "Bind: " + e.a().getValInt();
                                e.fc = false;
                            }
                        }
                        else {
                            e.fi = "Bind: " + e.a().getValInt();
                        }
                        if (e.fd) {
                            e.fd = false;
                            break;
                        }
                        break;
                    }
                    case 2: {
                        if (e.fc) {
                            int i = 0;
                            while (i < e.a().f().length) {
                                if (e.a().f()[i].equals(e.a().getValInt())) {
                                    if (i == 0) {
                                        e.a().a(e.a().f()[e.a().f().length - 1]);
                                        break;
                                    }
                                    e.a().a(e.a().f()[i - 1]);
                                    break;
                                }
                                else {
                                    ++i;
                                }
                            }
                            e.fc = false;
                        }
                        e.fi = e.a().a() + ": " + e.a().getValInt().toString();
                        break;
                    }
                    case 3: {
                        if (e.fc) {
                            e.a().a(!(boolean)e.a().getValInt());
                            e.fc = false;
                        }
                        e.fi = e.a().a() + ": " + e.a().getValInt();
                        break;
                    }
                    case 4: {
                        if (e.fc) {
                            e.gg = true;
                            e.fc = false;
                        }
                        if (!Mouse.isButtonDown(0)) {
                            e.gg = false;
                        }
                        if (e.gg && a.a.f.a.gh >= e.fe && a.a.f.a.gh <= e.fe + e.fg) {
                            e.a().a(value.doubleValue() * (e.a().g().doubleValue() - e.a().i().doubleValue()) + e.a().i().doubleValue());
                        }
                        e.fi = e.a().a() + ": " + a.a.k.e.a(((Number)e.a().getValInt()).doubleValue(), 2);
                        break;
                    }
                    case 5: {
                        if (e.fc) {
                            e.gg = true;
                            e.fc = false;
                        }
                        if (!Mouse.isButtonDown(0)) {
                            e.gg = false;
                        }
                        if (e.gg && a.a.f.a.gh >= e.fe && a.a.f.a.gh <= e.fe + e.fg) {
                            e.a().a(value.floatValue() * (e.a().g().floatValue() - e.a().i().floatValue()) + e.a().i().floatValue());
                        }
                        e.fi = e.a().a() + ": " + a.a.k.e.a(((Number)e.a().getValInt()).doubleValue(), 2);
                        break;
                    }
                    case 6: {
                        if (e.fc) {
                            e.gg = true;
                            e.fc = false;
                        }
                        if (!Mouse.isButtonDown(0)) {
                            e.gg = false;
                        }
                        if (e.gg && a.a.f.a.gh >= e.fe && a.a.f.a.gh <= e.fe + e.fg) {
                            e.a().a(Double.valueOf(value.doubleValue() * (e.a().g().intValue() - e.a().i().intValue()) + e.a().i().intValue()).intValue());
                        }
                        e.fi = e.a().a() + ": " + ((Number)e.a().getValInt()).intValue();
                        break;
                    }
                }
                e.fe = e.b().fe + e.fg;
                if (a != null) {
                    e.ff = a.ff + a.fh;
                }
                else {
                    e.ff = c.ff;
                }
                a = e;
            }
        }
    }
}
