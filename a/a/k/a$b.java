package a.a.k;

public enum b
{
    bbl("DARKRED", 0, "Dark Red"), 
    bbm("RED", 1, "Red"), 
    bbn("GOLD", 2, "Gold"), 
    bbo("YELLOW", 3, "Yellow"), 
    bbp("DARKGREEN", 4, "Dark Green"), 
    bbq("GREEN", 5, "Green"), 
    bbr("AQUA", 6, "Aqua"), 
    bbs("DARKAQUA", 7, "Dark Aqua"), 
    bbt("DARKBLUE", 8, "Dark Blue"), 
    bbu("BLUE", 9, "Blue"), 
    bbv("LIGHTPURPLE", 10, "Light Purple"), 
    bbw("DARKPURPLE", 11, "Dark Purple"), 
    bbx("WHITE", 12, "White"), 
    bby("GRAY", 13, "Gray"), 
    bbz("DARKGRAY", 14, "Dark Gray"), 
    bca("BLACK", 15, "Black");
    
    private String bcb;
    private static final /* synthetic */ b[] $VALUES;
    
    public static b[] values() {
        return b.$VALUES.clone();
    }
    
    public static b valueOf(final String s) {
        return Enum.valueOf(b.class, s);
    }
    
    private b(final String s, final int n, final String bcb) {
        this.bcb = bcb;
    }
    
    @Override
    public String toString() {
        return this.bcb;
    }
    
    static {
        $VALUES = new b[] { b.bbl, b.bbm, b.bbn, b.bbo, b.bbp, b.bbq, b.bbr, b.bbs, b.bbt, b.bbu, b.bbv, b.bbw, b.bbx, b.bby, b.bbz, b.bca };
    }
}
