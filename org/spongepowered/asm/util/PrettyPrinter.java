package org.spongepowered.asm.util;

import com.google.common.base.*;
import java.util.*;
import java.util.regex.*;
import org.apache.logging.log4j.*;
import java.io.*;

public class PrettyPrinter
{
    private final HorizontalRule horizontalRule;
    private final List<Object> lines;
    private Table table;
    private boolean recalcWidth;
    protected int width;
    protected int wrapWidth;
    protected int kvKeyWidth;
    protected String kvFormat;
    
    public PrettyPrinter() {
        this(100);
    }
    
    public PrettyPrinter(final int a1) {
        super();
        this.horizontalRule = new HorizontalRule(new char[] { '*' });
        this.lines = new ArrayList<Object>();
        this.recalcWidth = false;
        this.width = 100;
        this.wrapWidth = 80;
        this.kvKeyWidth = 10;
        this.kvFormat = makeKvFormat(this.kvKeyWidth);
        this.width = a1;
    }
    
    public PrettyPrinter wrapTo(final int a1) {
        this.wrapWidth = a1;
        return this;
    }
    
    public int wrapTo() {
        return this.wrapWidth;
    }
    
    public PrettyPrinter table() {
        this.table = new Table();
        return this;
    }
    
    public PrettyPrinter table(final String... v2) {
        this.table = new Table();
        for (final String a1 : v2) {
            this.table.addColumn(a1);
        }
        return this;
    }
    
    public PrettyPrinter table(final Object... v-4) {
        this.table = new Table();
        Column column = null;
        for (final Object v1 : v-4) {
            if (v1 instanceof String) {
                column = this.table.addColumn((String)v1);
            }
            else if (v1 instanceof Integer && column != null) {
                final int a1 = (int)v1;
                if (a1 > 0) {
                    column.setWidth(a1);
                }
                else if (a1 < 0) {
                    column.setMaxWidth(-a1);
                }
            }
            else if (v1 instanceof Alignment && column != null) {
                column.setAlignment((Alignment)v1);
            }
            else if (v1 != null) {
                column = this.table.addColumn(v1.toString());
            }
        }
        return this;
    }
    
    public PrettyPrinter spacing(final int a1) {
        if (this.table == null) {
            this.table = new Table();
        }
        this.table.setColSpacing(a1);
        return this;
    }
    
    public PrettyPrinter th() {
        return this.th(false);
    }
    
    private PrettyPrinter th(final boolean a1) {
        if (this.table == null) {
            this.table = new Table();
        }
        if (!a1 || this.table.addHeader) {
            this.table.headerAdded();
            this.addLine(this.table);
        }
        return this;
    }
    
    public PrettyPrinter tr(final Object... a1) {
        this.th(true);
        this.addLine(this.table.addRow(a1));
        this.recalcWidth = true;
        return this;
    }
    
    public PrettyPrinter add() {
        this.addLine("");
        return this;
    }
    
    public PrettyPrinter add(final String a1) {
        this.addLine(a1);
        this.width = Math.max(this.width, a1.length());
        return this;
    }
    
    public PrettyPrinter add(final String a1, final Object... a2) {
        final String v1 = String.format(a1, a2);
        this.addLine(v1);
        this.width = Math.max(this.width, v1.length());
        return this;
    }
    
    public PrettyPrinter add(final Object[] a1) {
        return this.add(a1, "%s");
    }
    
    public PrettyPrinter add(final Object[] v1, final String v2) {
        for (final Object a1 : v1) {
            this.add(v2, a1);
        }
        return this;
    }
    
    public PrettyPrinter addIndexed(final Object[] v2) {
        final int v3 = String.valueOf(v2.length - 1).length();
        final String v4 = "[%" + v3 + "d] %s";
        for (int a1 = 0; a1 < v2.length; ++a1) {
            this.add(v4, a1, v2[a1]);
        }
        return this;
    }
    
    public PrettyPrinter addWithIndices(final Collection<?> a1) {
        return this.addIndexed(a1.toArray());
    }
    
    public PrettyPrinter add(final IPrettyPrintable a1) {
        if (a1 != null) {
            a1.print(this);
        }
        return this;
    }
    
    public PrettyPrinter add(final Throwable a1) {
        return this.add(a1, 4);
    }
    
    public PrettyPrinter add(Throwable a1, final int a2) {
        while (a1 != null) {
            this.add("%s: %s", a1.getClass().getName(), a1.getMessage());
            this.add(a1.getStackTrace(), a2);
            a1 = a1.getCause();
        }
        return this;
    }
    
    public PrettyPrinter add(final StackTraceElement[] v1, final int v2) {
        final String v3 = Strings.repeat(" ", v2);
        for (final StackTraceElement a1 : v1) {
            this.add("%s%s", v3, a1);
        }
        return this;
    }
    
    public PrettyPrinter add(final Object a1) {
        return this.add(a1, 0);
    }
    
    public PrettyPrinter add(final Object a1, final int a2) {
        final String v1 = Strings.repeat(" ", a2);
        return this.append(a1, a2, v1);
    }
    
    private PrettyPrinter append(final Object a3, final int v1, final String v2) {
        if (a3 instanceof String) {
            return this.add("%s%s", v2, a3);
        }
        if (a3 instanceof Iterable) {
            for (final Object a4 : (Iterable)a3) {
                this.append(a4, v1, v2);
            }
            return this;
        }
        if (a3 instanceof Map) {
            this.kvWidth(v1);
            return this.add((Map<?, ?>)a3);
        }
        if (a3 instanceof IPrettyPrintable) {
            return this.add((IPrettyPrintable)a3);
        }
        if (a3 instanceof Throwable) {
            return this.add((Throwable)a3, v1);
        }
        if (a3.getClass().isArray()) {
            return this.add((Object[])a3, v1 + "%s");
        }
        return this.add("%s%s", v2, a3);
    }
    
    public PrettyPrinter addWrapped(final String a1, final Object... a2) {
        return this.addWrapped(this.wrapWidth, a1, a2);
    }
    
    public PrettyPrinter addWrapped(final int v1, final String v2, final Object... v3) {
        String v4 = "";
        final String v5 = String.format(v2, v3).replace("\t", "    ");
        final Matcher v6 = Pattern.compile("^(\\s+)(.*)$").matcher(v5);
        if (v6.matches()) {
            v4 = v6.group(1);
        }
        try {
            for (final String a1 : this.getWrapped(v1, v5, v4)) {
                this.addLine(a1);
            }
        }
        catch (Exception a2) {
            this.add(v5);
        }
        return this;
    }
    
    private List<String> getWrapped(final int v1, String v2, final String v3) {
        final List<String> v4 = new ArrayList<String>();
        while (v2.length() > v1) {
            int a1 = v2.lastIndexOf(32, v1);
            if (a1 < 10) {
                a1 = v1;
            }
            final String a2 = v2.substring(0, a1);
            v4.add(a2);
            v2 = v3 + v2.substring(a1 + 1);
        }
        if (v2.length() > 0) {
            v4.add(v2);
        }
        return v4;
    }
    
    public PrettyPrinter kv(final String a1, final String a2, final Object... a3) {
        return this.kv(a1, (Object)String.format(a2, a3));
    }
    
    public PrettyPrinter kv(final String a1, final Object a2) {
        this.addLine(new KeyValue(a1, a2));
        return this.kvWidth(a1.length());
    }
    
    public PrettyPrinter kvWidth(final int a1) {
        if (a1 > this.kvKeyWidth) {
            this.kvKeyWidth = a1;
            this.kvFormat = makeKvFormat(a1);
        }
        this.recalcWidth = true;
        return this;
    }
    
    public PrettyPrinter add(final Map<?, ?> v-1) {
        for (final Map.Entry<?, ?> v1 : v-1.entrySet()) {
            final String a1 = (v1.getKey() == null) ? "null" : v1.getKey().toString();
            this.kv(a1, v1.getValue());
        }
        return this;
    }
    
    public PrettyPrinter hr() {
        return this.hr('*');
    }
    
    public PrettyPrinter hr(final char a1) {
        this.addLine(new HorizontalRule(new char[] { a1 }));
        return this;
    }
    
    public PrettyPrinter centre() {
        if (!this.lines.isEmpty()) {
            final Object v1 = this.lines.get(this.lines.size() - 1);
            if (v1 instanceof String) {
                this.addLine(new CentredText(this.lines.remove(this.lines.size() - 1)));
            }
        }
        return this;
    }
    
    private void addLine(final Object a1) {
        if (a1 == null) {
            return;
        }
        this.lines.add(a1);
        this.recalcWidth |= (a1 instanceof IVariableWidthEntry);
    }
    
    public PrettyPrinter trace() {
        return this.trace(getDefaultLoggerName());
    }
    
    public PrettyPrinter trace(final Level a1) {
        return this.trace(getDefaultLoggerName(), a1);
    }
    
    public PrettyPrinter trace(final String a1) {
        return this.trace(System.err, LogManager.getLogger(a1));
    }
    
    public PrettyPrinter trace(final String a1, final Level a2) {
        return this.trace(System.err, LogManager.getLogger(a1), a2);
    }
    
    public PrettyPrinter trace(final Logger a1) {
        return this.trace(System.err, a1);
    }
    
    public PrettyPrinter trace(final Logger a1, final Level a2) {
        return this.trace(System.err, a1, a2);
    }
    
    public PrettyPrinter trace(final PrintStream a1) {
        return this.trace(a1, getDefaultLoggerName());
    }
    
    public PrettyPrinter trace(final PrintStream a1, final Level a2) {
        return this.trace(a1, getDefaultLoggerName(), a2);
    }
    
    public PrettyPrinter trace(final PrintStream a1, final String a2) {
        return this.trace(a1, LogManager.getLogger(a2));
    }
    
    public PrettyPrinter trace(final PrintStream a1, final String a2, final Level a3) {
        return this.trace(a1, LogManager.getLogger(a2), a3);
    }
    
    public PrettyPrinter trace(final PrintStream a1, final Logger a2) {
        return this.trace(a1, a2, Level.DEBUG);
    }
    
    public PrettyPrinter trace(final PrintStream a1, final Logger a2, final Level a3) {
        this.log(a2, a3);
        this.print(a1);
        return this;
    }
    
    public PrettyPrinter print() {
        return this.print(System.err);
    }
    
    public PrettyPrinter print(final PrintStream v2) {
        this.updateWidth();
        this.printSpecial(v2, this.horizontalRule);
        for (final Object a1 : this.lines) {
            if (a1 instanceof ISpecialEntry) {
                this.printSpecial(v2, (ISpecialEntry)a1);
            }
            else {
                this.printString(v2, a1.toString());
            }
        }
        this.printSpecial(v2, this.horizontalRule);
        return this;
    }
    
    private void printSpecial(final PrintStream a1, final ISpecialEntry a2) {
        a1.printf("/*%s*/\n", a2.toString());
    }
    
    private void printString(final PrintStream a1, final String a2) {
        if (a2 != null) {
            a1.printf("/* %-" + this.width + "s */\n", a2);
        }
    }
    
    public PrettyPrinter log(final Logger a1) {
        return this.log(a1, Level.INFO);
    }
    
    public PrettyPrinter log(final Logger v1, final Level v2) {
        this.updateWidth();
        this.logSpecial(v1, v2, this.horizontalRule);
        for (final Object a1 : this.lines) {
            if (a1 instanceof ISpecialEntry) {
                this.logSpecial(v1, v2, (ISpecialEntry)a1);
            }
            else {
                this.logString(v1, v2, a1.toString());
            }
        }
        this.logSpecial(v1, v2, this.horizontalRule);
        return this;
    }
    
    private void logSpecial(final Logger a1, final Level a2, final ISpecialEntry a3) {
        a1.log(a2, "/*{}*/", new Object[] { a3.toString() });
    }
    
    private void logString(final Logger a1, final Level a2, final String a3) {
        if (a3 != null) {
            a1.log(a2, String.format("/* %-" + this.width + "s */", a3));
        }
    }
    
    private void updateWidth() {
        if (this.recalcWidth) {
            this.recalcWidth = false;
            for (final Object v1 : this.lines) {
                if (v1 instanceof IVariableWidthEntry) {
                    this.width = Math.min(4096, Math.max(this.width, ((IVariableWidthEntry)v1).getWidth()));
                }
            }
        }
    }
    
    private static String makeKvFormat(final int a1) {
        return String.format("%%%ds : %%s", a1);
    }
    
    private static String getDefaultLoggerName() {
        final String v1 = new Throwable().getStackTrace()[2].getClassName();
        final int v2 = v1.lastIndexOf(46);
        return (v2 == -1) ? v1 : v1.substring(v2 + 1);
    }
    
    public static void dumpStack() {
        new PrettyPrinter().add(new Exception("Stack trace")).print(System.err);
    }
    
    public static void print(final Throwable a1) {
        new PrettyPrinter().add(a1).print(System.err);
    }
    
    class KeyValue implements IVariableWidthEntry
    {
        private final String key;
        private final Object value;
        final /* synthetic */ PrettyPrinter this$0;
        
        public KeyValue(final PrettyPrinter a1, final String a2, final Object a3) {
            this.this$0 = a1;
            super();
            this.key = a2;
            this.value = a3;
        }
        
        @Override
        public String toString() {
            return String.format(this.this$0.kvFormat, this.key, this.value);
        }
        
        @Override
        public int getWidth() {
            return this.toString().length();
        }
    }
    
    class HorizontalRule implements ISpecialEntry
    {
        private final char[] hrChars;
        final /* synthetic */ PrettyPrinter this$0;
        
        public HorizontalRule(final PrettyPrinter a1, final char... a2) {
            this.this$0 = a1;
            super();
            this.hrChars = a2;
        }
        
        @Override
        public String toString() {
            return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
        }
    }
    
    class CentredText
    {
        private final Object centred;
        final /* synthetic */ PrettyPrinter this$0;
        
        public CentredText(final PrettyPrinter a1, final Object a2) {
            this.this$0 = a1;
            super();
            this.centred = a2;
        }
        
        @Override
        public String toString() {
            final String v1 = this.centred.toString();
            return String.format("%" + ((this.this$0.width - v1.length()) / 2 + v1.length()) + "s", v1);
        }
    }
    
    public enum Alignment
    {
        LEFT, 
        RIGHT;
        
        private static final /* synthetic */ Alignment[] $VALUES;
        
        public static Alignment[] values() {
            return Alignment.$VALUES.clone();
        }
        
        public static Alignment valueOf(final String a1) {
            return Enum.valueOf(Alignment.class, a1);
        }
        
        static {
            $VALUES = new Alignment[] { Alignment.LEFT, Alignment.RIGHT };
        }
    }
    
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
    
    interface IVariableWidthEntry
    {
        int getWidth();
    }
    
    interface ISpecialEntry
    {
    }
    
    public interface IPrettyPrintable
    {
        void print(final PrettyPrinter p0);
    }
}
