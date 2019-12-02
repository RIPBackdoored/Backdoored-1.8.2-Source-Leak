package javassist.bytecode.analysis;

import java.util.*;

public class Subroutine
{
    private List callers;
    private Set access;
    private int start;
    
    public Subroutine(final int a1, final int a2) {
        super();
        this.callers = new ArrayList();
        this.access = new HashSet();
        this.start = a1;
        this.callers.add(new Integer(a2));
    }
    
    public void addCaller(final int a1) {
        this.callers.add(new Integer(a1));
    }
    
    public int start() {
        return this.start;
    }
    
    public void access(final int a1) {
        this.access.add(new Integer(a1));
    }
    
    public boolean isAccessed(final int a1) {
        return this.access.contains(new Integer(a1));
    }
    
    public Collection accessed() {
        return this.access;
    }
    
    public Collection callers() {
        return this.callers;
    }
    
    @Override
    public String toString() {
        return "start = " + this.start + " callers = " + this.callers.toString();
    }
}
