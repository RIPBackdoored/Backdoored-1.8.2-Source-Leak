package com.sun.jna;

import java.util.*;

static class StructureSet extends AbstractCollection<Structure> implements Set<Structure>
{
    Structure[] elements;
    private int count;
    
    StructureSet() {
        super();
    }
    
    private void ensureCapacity(final int v2) {
        if (this.elements == null) {
            this.elements = new Structure[v2 * 3 / 2];
        }
        else if (this.elements.length < v2) {
            final Structure[] a1 = new Structure[v2 * 3 / 2];
            System.arraycopy(this.elements, 0, a1, 0, this.elements.length);
            this.elements = a1;
        }
    }
    
    public Structure[] getElements() {
        return this.elements;
    }
    
    @Override
    public int size() {
        return this.count;
    }
    
    @Override
    public boolean contains(final Object a1) {
        return this.indexOf((Structure)a1) != -1;
    }
    
    @Override
    public boolean add(final Structure a1) {
        if (!this.contains(a1)) {
            this.ensureCapacity(this.count + 1);
            this.elements[this.count++] = a1;
        }
        return true;
    }
    
    private int indexOf(final Structure v0) {
        for (int v = 0; v < this.count; ++v) {
            final Structure a1 = this.elements[v];
            if (v0 == a1 || (v0.getClass() == a1.getClass() && v0.size() == a1.size() && v0.getPointer().equals(a1.getPointer()))) {
                return v;
            }
        }
        return -1;
    }
    
    @Override
    public boolean remove(final Object a1) {
        final int v1 = this.indexOf((Structure)a1);
        if (v1 != -1) {
            if (--this.count >= 0) {
                this.elements[v1] = this.elements[this.count];
                this.elements[this.count] = null;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public Iterator<Structure> iterator() {
        final Structure[] v1 = new Structure[this.count];
        if (this.count > 0) {
            System.arraycopy(this.elements, 0, v1, 0, this.count);
        }
        return Arrays.asList(v1).iterator();
    }
    
    @Override
    public /* bridge */ boolean add(final Object o) {
        return this.add((Structure)o);
    }
}
