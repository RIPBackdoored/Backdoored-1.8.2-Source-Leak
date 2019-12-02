package cookiedragon.licensegen;

import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import com.backdoored.utils.*;

public class CopyPaste
{
    public CopyPaste() {
        super();
    }
    
    private static void checkDRM() {
        final String v1 = Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
        final String v2 = Hashing.sha1().hashString((CharSequence)v1, StandardCharsets.UTF_8).toString();
        final String v3 = Hashing.sha512().hashString((CharSequence)("john" + v2 + v1 + v2), StandardCharsets.UTF_8).toString();
        if (!v3.equalsIgnoreCase(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + v1);
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
