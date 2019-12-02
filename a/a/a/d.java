package a.a.a;

import com.google.common.collect.*;
import org.json.simple.*;
import java.net.*;
import java.io.*;
import org.json.simple.parser.*;
import javax.net.ssl.*;
import com.backdoored.*;

public class d
{
    public static HashBiMap<String, String> e;
    private static final Boolean[] f;
    
    public d() {
        super();
    }
    
    public static String a(final String s) {
        if (d.e.containsKey((Object)s)) {
            System.out.println("Grabbing username from hash map");
            return (String)d.e.get((Object)s);
        }
        System.out.println("Grabbing username from Mojang Web Api");
        return a(s, true);
    }
    
    public static String b(final String s) {
        if (d.e.containsValue((Object)s)) {
            System.out.println("Grabbing UUID from hash map");
            return d.e.inverse().get(s);
        }
        System.out.println("Grabbing UUID from Mojang Web Api");
        return a(s, false);
    }
    
    private static String a(final String s, final Boolean b) {
        if (b) {
            try {
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/user/profiles/" + s.replace("-", "") + "/names").openStream()));
                String string = "Popbob";
                final String line = bufferedReader.readLine();
                System.out.println(line);
                bufferedReader.close();
                if (line != null) {
                    final JSONArray jsonArray = (JSONArray)new JSONParser().parse(line);
                    string = jsonArray.get(jsonArray.size() - 1).get("name").toString();
                }
                bufferedReader.close();
                d.e.put((Object)s, (Object)string);
                return string;
            }
            catch (MalformedURLException ex) {
                System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
                return "";
            }
            catch (IOException ex2) {
                System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
                return "";
            }
            catch (ParseException ex3) {
                System.out.println("JSON userdata was parsed wrong, shit.");
                return "";
            }
        }
        try {
            final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + s).openStream()));
            String string2 = "00000000-0000-0000-0000-000000000000";
            final String line2 = bufferedReader2.readLine();
            bufferedReader2.close();
            if (line2 != null) {
                final StringBuilder sb = new StringBuilder(new String(((JSONObject)new JSONParser().parse(line2)).get("id").toString()));
                sb.insert(8, '-');
                sb.insert(13, '-');
                sb.insert(18, '-');
                sb.insert(23, '-');
                string2 = sb.toString();
            }
            d.e.put((Object)string2, (Object)s);
            return string2;
        }
        catch (MalformedURLException ex4) {
            System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
            return "";
        }
        catch (IOException ex5) {
            System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
            return "";
        }
        catch (ParseException ex6) {
            System.out.println("JSON userdata was parsed wrong, shit.");
            return "";
        }
    }
    
    private static boolean a() {
        return c("https://authserver.mojang.com/authenticate");
    }
    
    private static boolean b() {
        return c("https://sessionserver.mojang.com/");
    }
    
    public static boolean c() {
        synchronized (d.f) {
            return d.f[0];
        }
    }
    
    public static boolean d() {
        synchronized (d.f) {
            return d.f[1];
        }
    }
    
    private static boolean c(final String s) {
        try {
            final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL(s).openConnection();
            httpsURLConnection.connect();
            httpsURLConnection.disconnect();
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private static /* synthetic */ void e() {
        while (true) {
            try {
                while (true) {
                    if (Globals.mc.currentScreen instanceof a.a.j.d) {
                        final boolean a = a();
                        final boolean b = b();
                        synchronized (d.f) {
                            d.f[0] = a;
                            d.f[1] = b;
                        }
                    }
                    Thread.sleep(7500L);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: putstatic       a/a/a/d.e:Lcom/google/common/collect/HashBiMap;
        //     6: iconst_2       
        //     7: anewarray       Ljava/lang/Boolean;
        //    10: dup            
        //    11: iconst_0       
        //    12: iconst_1       
        //    13: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    16: aastore        
        //    17: dup            
        //    18: iconst_1       
        //    19: iconst_1       
        //    20: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    23: aastore        
        //    24: putstatic       a/a/a/d.f:[Ljava/lang/Boolean;
        //    27: new             Ljava/lang/Thread;
        //    30: dup            
        //    31: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //    36: ldc_w           "Status checker"
        //    39: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;Ljava/lang/String;)V
        //    42: invokevirtual   java/lang/Thread.start:()V
        //    45: return         
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
