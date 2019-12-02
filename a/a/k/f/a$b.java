package a.a.k.f;

public static class b
{
    public d bdg;
    public d bdh;
    
    public b(final double bdp, final double bdq, final double bdr, final double bdp2, final double bdq2, final double bdr2) {
        super();
        this.bdg = new d(0.0, 0.0, 0.0);
        this.bdh = new d(0.0, 0.0, 0.0);
        this.bdg.bdp = bdp;
        this.bdg.bdq = bdq;
        this.bdg.bdr = bdr;
        this.bdh.bdp = bdp2;
        this.bdh.bdq = bdq2;
        this.bdh.bdr = bdr2;
    }
    
    public d a(final b b) {
        final double bdp = this.bdg.bdp;
        final double bdp2 = this.bdh.bdp;
        final double bdp3 = b.bdg.bdp;
        final double bdp4 = b.bdh.bdp;
        final double bdq = this.bdg.bdq;
        final double bdq2 = this.bdh.bdq;
        final double bdq3 = b.bdg.bdq;
        final double bdq4 = b.bdh.bdq;
        final double n = -(bdp * bdq4 - bdp3 * bdq4 - bdp4 * (bdq - bdq3));
        final double n2 = bdp2 * bdq4 - bdp4 * bdq2;
        if (n2 == 0.0) {
            return this.b(b);
        }
        final double n3 = n / n2;
        final d d = new d(0.0, 0.0, 0.0);
        d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
        d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
        d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
        return d;
    }
    
    private d b(final b b) {
        final double bdp = this.bdg.bdp;
        final double bdp2 = this.bdh.bdp;
        final double bdp3 = b.bdg.bdp;
        final double bdp4 = b.bdh.bdp;
        final double bdr = this.bdg.bdr;
        final double bdr2 = this.bdh.bdr;
        final double bdr3 = b.bdg.bdr;
        final double bdr4 = b.bdh.bdr;
        final double n = -(bdp * bdr4 - bdp3 * bdr4 - bdp4 * (bdr - bdr3));
        final double n2 = bdp2 * bdr4 - bdp4 * bdr2;
        if (n2 == 0.0) {
            return this.c(b);
        }
        final double n3 = n / n2;
        final d d = new d(0.0, 0.0, 0.0);
        d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
        d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
        d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
        return d;
    }
    
    private d c(final b b) {
        final double bdq = this.bdg.bdq;
        final double bdq2 = this.bdh.bdq;
        final double bdq3 = b.bdg.bdq;
        final double bdq4 = b.bdh.bdq;
        final double bdr = this.bdg.bdr;
        final double bdr2 = this.bdh.bdr;
        final double bdr3 = b.bdg.bdr;
        final double bdr4 = b.bdh.bdr;
        final double n = -(bdq * bdr4 - bdq3 * bdr4 - bdq4 * (bdr - bdr3));
        final double n2 = bdq2 * bdr4 - bdq4 * bdr2;
        if (n2 == 0.0) {
            return null;
        }
        final double n3 = n / n2;
        final d d = new d(0.0, 0.0, 0.0);
        d.bdp = this.bdg.bdp + this.bdh.bdp * n3;
        d.bdq = this.bdg.bdq + this.bdh.bdq * n3;
        d.bdr = this.bdg.bdr + this.bdh.bdr * n3;
        return d;
    }
    
    public d a(final d d, final d d2) {
        final d d3 = new d(this.bdg.bdp, this.bdg.bdq, this.bdg.bdr);
        d3.e(this.bdh.a(d.b(this.bdg).c(d2) / this.bdh.c(d2)));
        if (this.bdh.c(d2) == 0.0) {
            return null;
        }
        return d3;
    }
}
