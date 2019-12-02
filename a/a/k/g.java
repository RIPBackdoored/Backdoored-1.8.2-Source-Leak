package a.a.k;

import net.minecraft.util.*;

public class g
{
    public h bey;
    public String bez;
    public String bfa;
    private Session session;
    
    public g(final String bez, final String bfa) {
        super();
        this.bey = new h();
        this.bez = bez;
        this.bfa = bfa;
    }
    
    public boolean a() {
        if (this.bfa == null || this.bfa.equals("")) {
            final h bey = this.bey;
            this.session = h.a(this.bez);
            return true;
        }
        final h bey2 = this.bey;
        final Session a = h.a(this.bez, this.bfa);
        if (a != null) {
            this.session = a;
            return true;
        }
        return false;
    }
    
    public Session b() {
        return this.session;
    }
}
