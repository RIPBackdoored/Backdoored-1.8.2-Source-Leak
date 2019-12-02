package com.backdoored.gui;

public enum CategoriesInit
{
    gz("MOVEMENT", 0, "Movement"), 
    ha("RENDER", 1, "Render"), 
    hb("PLAYER", 2, "Player"), 
    hc("COMBAT", 3, "Combat"), 
    hd("MISC", 4, "Misc"), 
    he("EXPLOIT", 5, "Exploits"), 
    hf("CLIENT", 6, "Client"), 
    hg("UI", 7, "UIs"), 
    hh("CHATBOT", 8, "ChatBot");
    
    public Category hi;
    private static final /* synthetic */ CategoriesInit[] $VALUES;
    
    public static CategoriesInit[] values() {
        return CategoriesInit.$VALUES.clone();
    }
    
    public static CategoriesInit valueOf(final String s) {
        return Enum.valueOf(CategoriesInit.class, s);
    }
    
    private CategoriesInit(final String s, final int n, final String s2) {
        this.hi = new Category(s2);
    }
    
    static {
        $VALUES = new CategoriesInit[] { CategoriesInit.gz, CategoriesInit.ha, CategoriesInit.hb, CategoriesInit.hc, CategoriesInit.hd, CategoriesInit.he, CategoriesInit.hf, CategoriesInit.hg, CategoriesInit.hh };
    }
}
