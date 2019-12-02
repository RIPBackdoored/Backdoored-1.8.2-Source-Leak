package org.spongepowered.asm.util;

static class Column
{
    private final Table table;
    private Alignment align;
    private int minWidth;
    private int maxWidth;
    private int size;
    private String title;
    private String format;
    
    Column(final Table a1) {
        super();
        this.align = Alignment.LEFT;
        this.minWidth = 1;
        this.maxWidth = 0;
        this.size = 0;
        this.title = "";
        this.format = "%s";
        this.table = a1;
    }
    
    Column(final Table a1, final String a2) {
        this(a1);
        this.title = a2;
        this.minWidth = a2.length();
        this.updateFormat();
    }
    
    Column(final Table a1, final Alignment a2, final int a3, final String a4) {
        this(a1, a4);
        this.align = a2;
        this.size = a3;
    }
    
    void setAlignment(final Alignment a1) {
        this.align = a1;
        this.updateFormat();
    }
    
    void setWidth(final int a1) {
        if (a1 > this.size) {
            this.size = a1;
            this.updateFormat();
        }
    }
    
    void setMinWidth(final int a1) {
        if (a1 > this.minWidth) {
            this.minWidth = a1;
            this.updateFormat();
        }
    }
    
    void setMaxWidth(final int a1) {
        this.size = Math.min(this.size, this.maxWidth);
        this.maxWidth = Math.max(1, a1);
        this.updateFormat();
    }
    
    void setTitle(final String a1) {
        this.title = a1;
        this.setWidth(a1.length());
    }
    
    private void updateFormat() {
        final int v1 = Math.min(this.maxWidth, (this.size == 0) ? this.minWidth : this.size);
        this.format = "%" + ((this.align == Alignment.RIGHT) ? "" : "-") + v1 + "s";
        this.table.updateFormat();
    }
    
    int getMaxWidth() {
        return this.maxWidth;
    }
    
    String getTitle() {
        return this.title;
    }
    
    String getFormat() {
        return this.format;
    }
    
    @Override
    public String toString() {
        if (this.title.length() > this.maxWidth) {
            return this.title.substring(0, this.maxWidth);
        }
        return this.title;
    }
}
