package a.a.k.a.a;

public class i extends a
{
    public i() {
        super();
    }
    
    @Override
    public String b() {
        return "Soviet";
    }
    
    @Override
    public String b(final String s) {
        return "\u262d" + s.replace("my", "our") + "\u262d";
    }
}
