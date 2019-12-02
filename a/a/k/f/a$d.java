package a.a.k.f;

public static class d
{
    public double bdp;
    public double bdq;
    public double bdr;
    
    public d(final double bdp, final double bdq, final double bdr) {
        super();
        this.bdp = bdp;
        this.bdq = bdq;
        this.bdr = bdr;
    }
    
    public d a(final d d) {
        return new d(this.bdp + d.bdp, this.bdq + d.bdq, this.bdr + d.bdr);
    }
    
    public d a(final double n, final double n2, final double n3) {
        return new d(this.bdp + n, this.bdq + n2, this.bdr + n3);
    }
    
    public d b(final d d) {
        return new d(this.bdp - d.bdp, this.bdq - d.bdq, this.bdr - d.bdr);
    }
    
    public d b(final double n, final double n2, final double n3) {
        return new d(this.bdp - n, this.bdq - n2, this.bdr - n3);
    }
    
    public d a() {
        final double sqrt = Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
        return new d(this.bdp / sqrt, this.bdq / sqrt, this.bdr / sqrt);
    }
    
    public double c(final d d) {
        return this.bdp * d.bdp + this.bdq * d.bdq + this.bdr * d.bdr;
    }
    
    public d d(final d d) {
        return new d(this.bdq * d.bdr - this.bdr * d.bdq, this.bdr * d.bdp - this.bdp * d.bdr, this.bdp * d.bdq - this.bdq * d.bdp);
    }
    
    public d a(final double n) {
        return new d(this.bdp * n, this.bdq * n, this.bdr * n);
    }
    
    public d b(final double n) {
        return new d(this.bdp / n, this.bdq / n, this.bdr / n);
    }
    
    public double b() {
        return Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
    }
    
    public d e(final d d) {
        this.bdp += d.bdp;
        this.bdq += d.bdq;
        this.bdr += d.bdr;
        return this;
    }
    
    public d c(final double n, final double n2, final double n3) {
        this.bdp += n;
        this.bdq += n2;
        this.bdr += n3;
        return this;
    }
    
    public d f(final d d) {
        this.bdp -= d.bdp;
        this.bdq -= d.bdq;
        this.bdr -= d.bdr;
        return this;
    }
    
    public d d(final double n, final double n2, final double n3) {
        this.bdp -= n;
        this.bdq -= n2;
        this.bdr -= n3;
        return this;
    }
    
    public d c() {
        final double sqrt = Math.sqrt(this.bdp * this.bdp + this.bdq * this.bdq + this.bdr * this.bdr);
        this.bdp /= sqrt;
        this.bdq /= sqrt;
        this.bdr /= sqrt;
        return this;
    }
    
    public d g(final d d) {
        this.bdp = this.bdq * d.bdr - this.bdr * d.bdq;
        this.bdq = this.bdr * d.bdp - this.bdp * d.bdr;
        this.bdr = this.bdp * d.bdq - this.bdq * d.bdp;
        return this;
    }
    
    public d c(final double n) {
        this.bdp *= n;
        this.bdq *= n;
        this.bdr *= n;
        return this;
    }
    
    public d d(final double n) {
        this.bdp /= n;
        this.bdq /= n;
        this.bdr /= n;
        return this;
    }
    
    @Override
    public String toString() {
        return "(X: " + this.bdp + " Y: " + this.bdq + " Z: " + this.bdr + ")";
    }
}
