package a.a.a;

import a.a.*;
import com.backdoored.natives.*;
import a.a.k.*;
import net.minecraft.client.*;
import com.backdoored.*;
import net.minecraftforge.fml.common.*;
import com.mojang.authlib.exceptions.*;

public class a implements e
{
    public a() {
        super();
    }
    
    public static boolean a(final String s, final String s2) throws AuthenticationException {
        System.out.println(EncryptedStringPool.poolGet(13) + s);
        final g g = new g(s, s2);
        if (g.a()) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)Globals.mc, (Object)g.b(), new String[] { EncryptedStringPool.poolGet(14), EncryptedStringPool.poolGet(15) });
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
            System.out.println(EncryptedStringPool.poolGet(16));
            System.out.println(EncryptedStringPool.poolGet(17) + Globals.mc.getSession().getSessionID());
            System.out.println(EncryptedStringPool.poolGet(18) + a());
            return true;
        }
        return false;
    }
    
    public static String a() {
        return Globals.mc.getSession().getUsername();
    }
    
    public static String b() {
        return Globals.mc.getSession().getProfile().getId().toString();
    }
}
