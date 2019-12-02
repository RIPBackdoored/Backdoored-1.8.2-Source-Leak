package a.a.c;

import org.json.*;
import com.backdoored.hacks.*;
import a.a.g.c.*;
import com.backdoored.setting.*;
import com.backdoored.*;
import a.a.h.a.*;
import com.backdoored.commands.*;
import java.util.*;
import java.io.*;

public class c
{
    public c() {
        super();
    }
    
    public static void a(final File file) throws FileNotFoundException {
        final JSONObject jsonObject = new JSONObject(new JSONTokener(new FileInputStream(file)));
        try {
            final JSONObject jsonObject2 = jsonObject.getJSONObject("Hacks");
            for (final BaseHack baseHack : a.a.g.b.c.hacks()) {
                try {
                    final JSONObject jsonObject3 = jsonObject2.getJSONObject(baseHack.name);
                    baseHack.setEnabled(jsonObject3.getBoolean("Enabled"));
                    final ArrayList a = d.a(baseHack);
                    if (a == null) {
                        continue;
                    }
                    for (final Setting<Enum<?>> setting : a) {
                        try {
                            if (setting instanceof EnumSetting) {
                                for (final Enum enum1 : (Enum[])setting.getValInt().getClass().getEnumConstants()) {
                                    if (enum1.name().equalsIgnoreCase(jsonObject3.getString(setting.a()))) {
                                        setting.a(enum1);
                                    }
                                }
                            }
                            else {
                                setting.a((Enum<?>)setting.j().cast(jsonObject3.get(setting.a())));
                            }
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
        }
        catch (Exception ex8) {}
        try {
            final JSONObject jsonObject4 = jsonObject.getJSONObject("Hud");
            for (final a a2 : Backdoored.k.yx) {
                try {
                    final JSONObject jsonObject5 = jsonObject4.getJSONObject(a2.b());
                    a2.a(jsonObject5.getBoolean("Visible"));
                    if (!(a2 instanceof b)) {
                        continue;
                    }
                    a2.d().bco = jsonObject5.getInt("x");
                    a2.d().bcp = jsonObject5.getInt("y");
                }
                catch (Exception ex3) {
                    ex3.printStackTrace();
                }
            }
        }
        catch (Exception ex4) {
            ex4.printStackTrace();
        }
        try {
            Command.x = jsonObject.getJSONObject("Commands").getString("Prefix");
        }
        catch (Exception ex5) {
            ex5.printStackTrace();
        }
        try {
            final JSONObject jsonObject6 = jsonObject.getJSONObject("Categories");
            for (final a.a.f.a.b b : a.a.f.a.b.c()) {
                try {
                    final JSONObject jsonObject7 = jsonObject6.getJSONObject(b.fi);
                    b.fd = jsonObject7.getBoolean("Open");
                    b.fe = jsonObject7.getInt("x");
                    b.ff = jsonObject7.getInt("y");
                }
                catch (Exception ex6) {
                    ex6.printStackTrace();
                }
            }
        }
        catch (Exception ex7) {
            ex7.printStackTrace();
        }
    }
}
