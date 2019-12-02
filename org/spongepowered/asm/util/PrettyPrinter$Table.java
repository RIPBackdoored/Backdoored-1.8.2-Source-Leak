package org.spongepowered.asm.util;

import com.google.common.base.*;
import java.util.*;

static class Table implements IVariableWidthEntry
{
    final List<Column> columns;
    final List<Row> rows;
    String format;
    int colSpacing;
    boolean addHeader;
    
    Table() {
        super();
        this.columns = new ArrayList<Column>();
        this.rows = new ArrayList<Row>();
        this.format = "%s";
        this.colSpacing = 2;
        this.addHeader = true;
    }
    
    void headerAdded() {
        this.addHeader = false;
    }
    
    void setColSpacing(final int a1) {
        this.colSpacing = Math.max(0, a1);
        this.updateFormat();
    }
    
    Table grow(final int a1) {
        while (this.columns.size() < a1) {
            this.columns.add(new Column(this));
        }
        this.updateFormat();
        return this;
    }
    
    Column add(final Column a1) {
        this.columns.add(a1);
        return a1;
    }
    
    Row add(final Row a1) {
        this.rows.add(a1);
        return a1;
    }
    
    Column addColumn(final String a1) {
        return this.add(new Column(this, a1));
    }
    
    Column addColumn(final Alignment a1, final int a2, final String a3) {
        return this.add(new Column(this, a1, a2, a3));
    }
    
    Row addRow(final Object... a1) {
        return this.add(new Row(this, a1));
    }
    
    void updateFormat() {
        final String repeat = Strings.repeat(" ", this.colSpacing);
        final StringBuilder sb = new StringBuilder();
        boolean b = false;
        for (final Column v1 : this.columns) {
            if (b) {
                sb.append(repeat);
            }
            b = true;
            sb.append(v1.getFormat());
        }
        this.format = sb.toString();
    }
    
    String getFormat() {
        return this.format;
    }
    
    Object[] getTitles() {
        final List<Object> list = new ArrayList<Object>();
        for (final Column v1 : this.columns) {
            list.add(v1.getTitle());
        }
        return list.toArray();
    }
    
    @Override
    public String toString() {
        boolean b = false;
        final String[] v0 = new String[this.columns.size()];
        for (int v2 = 0; v2 < this.columns.size(); ++v2) {
            v0[v2] = this.columns.get(v2).toString();
            b |= !v0[v2].isEmpty();
        }
        return b ? String.format(this.format, (Object[])v0) : null;
    }
    
    @Override
    public int getWidth() {
        final String v1 = this.toString();
        return (v1 != null) ? v1.length() : 0;
    }
}
