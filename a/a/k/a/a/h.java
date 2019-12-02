package a.a.k.a.a;

public class h extends a
{
    public h() {
        super();
    }
    
    @Override
    public String b() {
        return "Reverse";
    }
    
    @Override
    public String b(final String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
