package org.yaml.snakeyaml.constructor;

private static class RecursiveTuple<T, K>
{
    private final T _1;
    private final K _2;
    
    public RecursiveTuple(final T _1, final K _2) {
        super();
        this._1 = _1;
        this._2 = _2;
    }
    
    public K _2() {
        return this._2;
    }
    
    public T _1() {
        return this._1;
    }
}
