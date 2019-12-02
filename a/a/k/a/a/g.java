package a.a.k.a.a;

import java.util.*;

public class g extends a
{
    private Map<String, String> bbk;
    
    public g() {
        super();
        this.bbk = new HashMap<String, String>() {
            public final /* synthetic */ g bbj;
            
            public g$a() {
                this.bbj = bbj;
                super();
            }
            
            {
                this.put("a", "4");
                this.put("b", "8");
                this.put("e", "3");
                this.put("i", "1");
                this.put("l", "1");
                this.put("o", "0");
                this.put("s", "5");
                this.put("t", "7");
                this.put("z", "5");
            }
        };
    }
    
    @Override
    public String b() {
        return "L33t";
    }
    
    @Override
    public String b(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final String s2 : s.split("")) {
            sb.append(this.bbk.getOrDefault(s2.toLowerCase(), s2));
        }
        return sb.toString();
    }
}
