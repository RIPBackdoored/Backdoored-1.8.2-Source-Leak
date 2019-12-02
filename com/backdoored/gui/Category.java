package com.backdoored.gui;

import java.util.*;
import com.backdoored.hacks.*;
import a.a.g.b.*;

public class Category
{
    private static ArrayList<Category> gw;
    private ArrayList<BaseHack> gx;
    private String gy;
    
    public Category(final String gy) {
        super();
        this.gx = new ArrayList<BaseHack>();
        this.gy = gy;
        Category.gw.add(this);
    }
    
    public String a() {
        return this.gy;
    }
    
    public ArrayList<b> b() {
        return (ArrayList<b>)this.gx;
    }
    
    @Override
    public String toString() {
        return this.a();
    }
    
    public static ArrayList<Category> c() {
        System.out.println("Categories: " + Category.gw.toString());
        return Category.gw;
    }
    
    static {
        Category.gw = new ArrayList<Category>();
    }
}
