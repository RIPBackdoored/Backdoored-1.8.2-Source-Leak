package a.a.a.a;

import net.minecraft.launchwrapper.*;
import java.util.*;
import java.net.*;

public class a
{
    public final LaunchClassLoader launchClassLoader;
    
    public a() {
        super();
        this.launchClassLoader = Launch.classLoader;
    }
    
    public List<Class> a(final String s, final String s2, final String... array) throws MalformedURLException, ClassNotFoundException {
        this.launchClassLoader.addURL(new URL(s + "/" + s2));
        final ArrayList<Class> list = new ArrayList<Class>();
        for (int length = array.length, i = 0; i < length; ++i) {
            list.add(this.launchClassLoader.loadClass(array[i]));
        }
        return list;
    }
}
