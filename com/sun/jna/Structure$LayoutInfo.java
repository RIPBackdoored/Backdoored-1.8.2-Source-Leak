package com.sun.jna;

import java.util.*;

private static class LayoutInfo
{
    private int size;
    private int alignment;
    private final Map<String, StructField> fields;
    private int alignType;
    private TypeMapper typeMapper;
    private boolean variable;
    private StructField typeInfoField;
    
    private LayoutInfo() {
        super();
        this.size = -1;
        this.alignment = 1;
        this.fields = Collections.synchronizedMap(new LinkedHashMap<String, StructField>());
        this.alignType = 0;
    }
    
    static /* synthetic */ boolean access$000(final LayoutInfo a1) {
        return a1.variable;
    }
    
    static /* synthetic */ int access$100(final LayoutInfo a1) {
        return a1.size;
    }
    
    static /* synthetic */ int access$200(final LayoutInfo a1) {
        return a1.alignType;
    }
    
    static /* synthetic */ TypeMapper access$300(final LayoutInfo a1) {
        return a1.typeMapper;
    }
    
    static /* synthetic */ int access$400(final LayoutInfo a1) {
        return a1.alignment;
    }
    
    static /* synthetic */ Map access$500(final LayoutInfo a1) {
        return a1.fields;
    }
    
    LayoutInfo(final Structure$1 a1) {
        this();
    }
    
    static /* synthetic */ int access$202(final LayoutInfo a1, final int a2) {
        return a1.alignType = a2;
    }
    
    static /* synthetic */ TypeMapper access$302(final LayoutInfo a1, final TypeMapper a2) {
        return a1.typeMapper = a2;
    }
    
    static /* synthetic */ boolean access$002(final LayoutInfo a1, final boolean a2) {
        return a1.variable = a2;
    }
    
    static /* synthetic */ int access$402(final LayoutInfo a1, final int a2) {
        return a1.alignment = a2;
    }
    
    static /* synthetic */ StructField access$700(final LayoutInfo a1) {
        return a1.typeInfoField;
    }
    
    static /* synthetic */ StructField access$702(final LayoutInfo a1, final StructField a2) {
        return a1.typeInfoField = a2;
    }
    
    static /* synthetic */ int access$102(final LayoutInfo a1, final int a2) {
        return a1.size = a2;
    }
}
