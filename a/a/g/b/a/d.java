package a.a.g.b.a;

import com.backdoored.hacks.chatbot.ChatBotScriptHandler.*;
import javax.script.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class d
{
    public static final String ik = "Backdoored/chatbot.js";
    public ScriptHandler il;
    public static final Logger im;
    
    public d() throws Exception {
        super();
        this.il = new ScriptHandler().a(a("Backdoored/chatbot.js")).a(d.im);
    }
    
    public String a(final String s, final String s2) throws ScriptException, NoSuchMethodException, IllegalStateException {
        return (String)this.il.a("onChatRecieved", s, s2);
    }
    
    public static String a(final String s) throws IOException {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(s));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            d.im.info("Successfully read chatbot script");
            return sb.toString();
        }
        catch (FileNotFoundException ex) {
            final File file = new File(s);
            try {
                file.createNewFile();
            }
            catch (Exception ex2) {}
            throw new IllegalStateException("Could not find chatbot script file");
        }
    }
    
    static {
        im = LogManager.getLogger("BackdooredChatBot");
    }
}
