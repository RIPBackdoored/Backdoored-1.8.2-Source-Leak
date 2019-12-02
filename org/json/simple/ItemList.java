package org.json.simple;

import java.util.*;

public class ItemList
{
    private String sp;
    List items;
    
    public ItemList() {
        super();
        this.sp = ",";
        this.items = new ArrayList();
    }
    
    public ItemList(final String a1) {
        super();
        this.sp = ",";
        this.items = new ArrayList();
        this.split(a1, this.sp, this.items);
    }
    
    public ItemList(final String a1, final String a2) {
        super();
        this.sp = ",";
        this.items = new ArrayList();
        this.split(this.sp = a1, a2, this.items);
    }
    
    public ItemList(final String a1, final String a2, final boolean a3) {
        super();
        this.sp = ",";
        this.split(a1, a2, this.items = new ArrayList(), a3);
    }
    
    public List getItems() {
        return this.items;
    }
    
    public String[] getArray() {
        return (String[])this.items.toArray();
    }
    
    public void split(final String a3, final String a4, final List v1, final boolean v2) {
        if (a3 == null || a4 == null) {
            return;
        }
        if (v2) {
            final StringTokenizer a5 = new StringTokenizer(a3, a4);
            while (a5.hasMoreTokens()) {
                v1.add(a5.nextToken().trim());
            }
        }
        else {
            this.split(a3, a4, v1);
        }
    }
    
    public void split(final String a1, final String a2, final List a3) {
        if (a1 == null || a2 == null) {
            return;
        }
        int v1 = 0;
        int v2 = 0;
        do {
            v2 = v1;
            v1 = a1.indexOf(a2, v1);
            if (v1 == -1) {
                break;
            }
            a3.add(a1.substring(v2, v1).trim());
            v1 += a2.length();
        } while (v1 != -1);
        a3.add(a1.substring(v2).trim());
    }
    
    public void setSP(final String a1) {
        this.sp = a1;
    }
    
    public void add(final int a1, final String a2) {
        if (a2 == null) {
            return;
        }
        this.items.add(a1, a2.trim());
    }
    
    public void add(final String a1) {
        if (a1 == null) {
            return;
        }
        this.items.add(a1.trim());
    }
    
    public void addAll(final ItemList a1) {
        this.items.addAll(a1.items);
    }
    
    public void addAll(final String a1) {
        this.split(a1, this.sp, this.items);
    }
    
    public void addAll(final String a1, final String a2) {
        this.split(a1, a2, this.items);
    }
    
    public void addAll(final String a1, final String a2, final boolean a3) {
        this.split(a1, a2, this.items, a3);
    }
    
    public String get(final int a1) {
        return this.items.get(a1);
    }
    
    public int size() {
        return this.items.size();
    }
    
    public String toString() {
        return this.toString(this.sp);
    }
    
    public String toString(final String v2) {
        final StringBuffer v3 = new StringBuffer();
        for (int a1 = 0; a1 < this.items.size(); ++a1) {
            if (a1 == 0) {
                v3.append(this.items.get(a1));
            }
            else {
                v3.append(v2);
                v3.append(this.items.get(a1));
            }
        }
        return v3.toString();
    }
    
    public void clear() {
        this.items.clear();
    }
    
    public void reset() {
        this.sp = ",";
        this.items.clear();
    }
}
