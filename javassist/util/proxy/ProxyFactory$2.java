package javassist.util.proxy;

static final class ProxyFactory$2 implements UniqueName {
    private final String sep = "_$$_jvst" + Integer.toHexString(this.hashCode() & 0xFFF) + "_";
    private int counter = 0;
    
    ProxyFactory$2() {
        super();
    }
    
    @Override
    public String get(final String a1) {
        return a1 + this.sep + Integer.toHexString(this.counter++);
    }
}