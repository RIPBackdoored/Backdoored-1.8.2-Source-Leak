package a.a.c;

import org.json.*;
import a.a.g.b.*;
import com.backdoored.hacks.*;
import com.backdoored.setting.*;
import com.backdoored.*;
import a.a.h.a.*;
import com.backdoored.commands.*;
import java.util.*;
import java.io.*;

public class d
{
    public d() {
        super();
    }
    
    public static void a(final File file) throws IOException {
        final JSONObject jsonObject = new JSONObject();
        final JSONObject a2 = new JSONObject();
        for (final BaseHack baseHack : c.hacks()) {
            final JSONObject a3 = new JSONObject();
            a3.put("Enabled", baseHack.i());
            final ArrayList a4 = a.a.g.c.d.a(baseHack);
            if (a4 != null) {
                for (final Setting<Enum> setting : a4) {
                    if (setting.getValInt() instanceof Enum) {
                        a3.put(setting.a(), setting.getValInt().name());
                    }
                    else {
                        a3.put(setting.a(), setting.getValInt());
                    }
                }
            }
            a2.put(baseHack.name, a3);
        }
        jsonObject.put("Hacks", a2);
        final JSONObject a5 = new JSONObject();
        for (final a a6 : Backdoored.k.yx) {
            final JSONObject a7 = new JSONObject();
            a7.put("Visible", a6.c());
            if (a6 instanceof b) {
                a7.put("x", a6.d().bco);
                a7.put("y", a6.d().bcp);
            }
            a5.put(a6.b(), a7);
        }
        jsonObject.put("Hud", a5);
        final JSONObject a8 = new JSONObject();
        a8.put("Prefix", Command.x);
        jsonObject.put("Commands", a8);
        final JSONObject a9 = new JSONObject();
        for (final a.a.f.a.b b : a.a.f.a.b.c()) {
            final JSONObject a10 = new JSONObject();
            a10.put("Open", b.fd);
            a10.put("x", b.fe);
            a10.put("y", b.ff);
            a9.put(b.fi, a10);
        }
        jsonObject.put("Categories", a9);
        final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        printWriter.print(jsonObject.toString(4));
        printWriter.close();
    }
}
