package a.a.k.a.a;

import java.util.*;

public class b extends a
{
    private Map<String, String> bbh;
    
    public b() {
        super();
        this.bbh = new HashMap<String, String>() {
            public final /* synthetic */ b bbg;
            
            public b$a() {
                this.bbg = bbg;
                super();
            }
            
            {
                this.put("the", "te");
                this.put("check", "czech");
                this.put("your", "ur");
                this.put("who ", "hoo");
                this.put("me", " i");
                this.put("i", " me");
                this.put("ok", "k");
                this.put("inter", "intre");
                this.put("family", "fam");
                this.put("crystal", "cyrtal");
                this.put("i'm", "me is");
                this.put("im", "me is");
                this.put("racist", "rasist");
                this.put("to", "2");
                this.put("have", "av");
                this.put("32k", "32kay");
                this.put("32ks", "32kays");
                this.put("hause", "horse");
                this.put("house", "horse");
                this.put("hausemaster", "horsemaster");
                this.put("housemaster", "horsemaster");
                this.put("jesus", "jebus");
                this.put("spawn", "spwan");
                this.put("cookiedragon", "cookiewagon");
                this.put("cookiedragon234", "cookiewagon234");
                this.put("tigermouthbear", "tigressmouthbear");
                this.put("carbolemons", "carbonlenons");
                this.put("give", "gib");
            }
        };
    }
    
    @Override
    public String b() {
        return "Chav";
    }
    
    @Override
    public String b(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final String s2 : s.split(" ")) {
            sb.append(this.bbh.getOrDefault(s2.toLowerCase(), s2)).append(" ");
        }
        return sb.toString();
    }
}
