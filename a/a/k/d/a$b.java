package a.a.k.d;

static class b extends a
{
    private c bcq;
    
    private b(final float n, final float n2, final float n3) {
        super(n, n2, n3, null);
        this.bcq = null;
    }
    
    @Override
    public boolean a() {
        return true;
    }
    
    @Override
    public a b() {
        return this.a(a.a.k.d.b.b(this.e()), a.a.k.d.b.b(this.f()), a.a.k.d.b.b(this.g()));
    }
    
    @Override
    public a c() {
        return (this.bcq == null) ? (this.bcq = (c)a.a(Math.toRadians(this.e()), Math.toRadians(this.f()), Math.toRadians(this.g()))) : this.bcq;
    }
    
    @Override
    public a d() {
        return this;
    }
    
    @Override
    protected a a(final float n, final float n2, final float n3) {
        return new b(n, n2, n3);
    }
    
    public b(final float n, final float n2, final float n3, final a$a object) {
        this(n, n2, n3);
    }
}
