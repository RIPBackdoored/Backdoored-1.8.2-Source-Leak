package org.spongepowered.asm.util;

static class Row implements IVariableWidthEntry
{
    final Table table;
    final String[] args;
    
    public Row(final Table v1, final Object... v2) {
        super();
        this.table = v1.grow(v2.length);
        this.args = new String[v2.length];
        for (int a1 = 0; a1 < v2.length; ++a1) {
            this.args[a1] = v2[a1].toString();
            this.table.columns.get(a1).setMinWidth(this.args[a1].length());
        }
    }
    
    @Override
    public String toString() {
        final Object[] array = new Object[this.table.columns.size()];
        for (int v0 = 0; v0 < array.length; ++v0) {
            final Column v2 = this.table.columns.get(v0);
            if (v0 >= this.args.length) {
                array[v0] = "";
            }
            else {
                array[v0] = ((this.args[v0].length() > v2.getMaxWidth()) ? this.args[v0].substring(0, v2.getMaxWidth()) : this.args[v0]);
            }
        }
        return String.format(this.table.format, array);
    }
    
    @Override
    public int getWidth() {
        return this.toString().length();
    }
}
