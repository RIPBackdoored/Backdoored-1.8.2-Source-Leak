package a.a.k.a.a;

public class f extends a
{
    public f() {
        super();
    }
    
    @Override
    public String b() {
        return "JustLearntEngrish";
    }
    
    @Override
    public String b(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final String s2 : s.split(" ")) {
            sb.append(s2.substring(0, 1).toUpperCase()).append(s2.substring(1)).append(" ");
        }
        return sb.toString();
    }
}
