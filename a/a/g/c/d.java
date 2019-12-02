package a.a.g.c;

import com.backdoored.setting.*;
import a.a.g.b.*;
import java.util.*;

public class d
{
    public static ArrayList<Setting> ym;
    
    public d() {
        super();
    }
    
    public static ArrayList<Setting> a() {
        return d.ym;
    }
    
    public static ArrayList<Setting> a(final b b) {
        final ArrayList<Setting> list = new ArrayList<Setting>();
        for (final Setting setting : d.ym) {
            if (setting.b() == b) {
                list.add(setting);
            }
        }
        return (ArrayList<Setting>)list;
    }
    
    static {
        d.ym = new ArrayList<Setting>();
    }
}
