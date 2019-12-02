package a.a.k.d;

static class c extends a
{
    private b bcr;
    
    private c(final float n, final float n2, final float n3) {
        super(n, n2, n3, null);
        this.bcr = null;
    }
    
    @Override
    public boolean a() {
        return false;
    }
    
    @Override
    public a b() {
        return this.a(a.a.k.d.b.a(this.e()), a.a.k.d.b.a(this.f()), a.a.k.d.b.a(this.g()));
    }
    
    @Override
    public a c() {
        return this;
    }
    
    @Override
    public a d() {
        return (this.bcr == null) ? (this.bcr = (b)a.b(Math.toDegrees(this.e()), Math.toDegrees(this.f()), Math.toDegrees(this.g()))) : this.bcr;
    }
    
    @Override
    protected a a(final float n, final float n2, final float n3) {
        return new c(n, n2, n3);
    }
    
    public c(final float n, final float n2, final float n3, final a$a object) {
        this(n, n2, n3);
    }
}
