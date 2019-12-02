package com.google.api.client.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public final class DateTime implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final TimeZone GMT;
    private static final Pattern RFC3339_PATTERN;
    private final long value;
    private final boolean dateOnly;
    private final int tzShift;
    
    public DateTime(final Date a1, final TimeZone a2) {
        this(false, a1.getTime(), (a2 == null) ? null : Integer.valueOf(a2.getOffset(a1.getTime()) / 60000));
    }
    
    public DateTime(final long a1) {
        this(false, a1, null);
    }
    
    public DateTime(final Date a1) {
        this(a1.getTime());
    }
    
    public DateTime(final long a1, final int a2) {
        this(false, a1, a2);
    }
    
    public DateTime(final boolean a1, final long a2, final Integer a3) {
        super();
        this.dateOnly = a1;
        this.value = a2;
        this.tzShift = (a1 ? 0 : ((a3 == null) ? (TimeZone.getDefault().getOffset(a2) / 60000) : a3));
    }
    
    public DateTime(final String a1) {
        super();
        final DateTime v1 = parseRfc3339(a1);
        this.dateOnly = v1.dateOnly;
        this.value = v1.value;
        this.tzShift = v1.tzShift;
    }
    
    public long getValue() {
        return this.value;
    }
    
    public boolean isDateOnly() {
        return this.dateOnly;
    }
    
    public int getTimeZoneShift() {
        return this.tzShift;
    }
    
    public String toStringRfc3339() {
        final StringBuilder a2 = new StringBuilder();
        final Calendar calendar = new GregorianCalendar(DateTime.GMT);
        final long timeInMillis = this.value + this.tzShift * 60000L;
        calendar.setTimeInMillis(timeInMillis);
        appendInt(a2, calendar.get(1), 4);
        a2.append('-');
        appendInt(a2, calendar.get(2) + 1, 2);
        a2.append('-');
        appendInt(a2, calendar.get(5), 2);
        if (!this.dateOnly) {
            a2.append('T');
            appendInt(a2, calendar.get(11), 2);
            a2.append(':');
            appendInt(a2, calendar.get(12), 2);
            a2.append(':');
            appendInt(a2, calendar.get(13), 2);
            if (calendar.isSet(14)) {
                a2.append('.');
                appendInt(a2, calendar.get(14), 3);
            }
            if (this.tzShift == 0) {
                a2.append('Z');
            }
            else {
                int v1 = this.tzShift;
                if (this.tzShift > 0) {
                    a2.append('+');
                }
                else {
                    a2.append('-');
                    v1 = -v1;
                }
                final int v2 = v1 / 60;
                final int v3 = v1 % 60;
                appendInt(a2, v2, 2);
                a2.append(':');
                appendInt(a2, v3, 2);
            }
        }
        return a2.toString();
    }
    
    @Override
    public String toString() {
        return this.toStringRfc3339();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (!(a1 instanceof DateTime)) {
            return false;
        }
        final DateTime v1 = (DateTime)a1;
        return this.dateOnly == v1.dateOnly && this.value == v1.value && this.tzShift == v1.tzShift;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(new long[] { this.value, this.dateOnly ? 1 : 0, this.tzShift });
    }
    
    public static DateTime parseRfc3339(final String v-15) throws NumberFormatException {
        final Matcher matcher = DateTime.RFC3339_PATTERN.matcher(v-15);
        if (!matcher.matches()) {
            throw new NumberFormatException("Invalid date/time format: " + v-15);
        }
        final int int1 = Integer.parseInt(matcher.group(1));
        final int n = Integer.parseInt(matcher.group(2)) - 1;
        final int int2 = Integer.parseInt(matcher.group(3));
        final boolean b = matcher.group(4) != null;
        final String group = matcher.group(9);
        final boolean b2 = group != null;
        int int3 = 0;
        int int4 = 0;
        int int5 = 0;
        int int6 = 0;
        Integer value = null;
        if (b2 && !b) {
            throw new NumberFormatException("Invalid date/time format, cannot specify time zone shift without specifying time: " + v-15);
        }
        if (b) {
            int3 = Integer.parseInt(matcher.group(5));
            int4 = Integer.parseInt(matcher.group(6));
            int5 = Integer.parseInt(matcher.group(7));
            if (matcher.group(8) != null) {
                int6 = Integer.parseInt(matcher.group(8).substring(1));
                final int a1 = matcher.group(8).substring(1).length() - 3;
                int6 = (int)((float)int6 / Math.pow(10.0, a1));
            }
        }
        final Calendar calendar = new GregorianCalendar(DateTime.GMT);
        calendar.set(int1, n, int2, int3, int4, int5);
        calendar.set(14, int6);
        long timeInMillis = calendar.getTimeInMillis();
        if (b && b2) {
            int v1;
            if (Character.toUpperCase(group.charAt(0)) == 'Z') {
                v1 = 0;
            }
            else {
                v1 = Integer.parseInt(matcher.group(11)) * 60 + Integer.parseInt(matcher.group(12));
                if (matcher.group(10).charAt(0) == '-') {
                    v1 = -v1;
                }
                timeInMillis -= v1 * 60000L;
            }
            value = v1;
        }
        return new DateTime(!b, timeInMillis, value);
    }
    
    private static void appendInt(final StringBuilder a2, int a3, int v1) {
        if (a3 < 0) {
            a2.append('-');
            a3 = -a3;
        }
        for (int v2 = a3; v2 > 0; v2 /= 10, --v1) {}
        for (int a4 = 0; a4 < v1; ++a4) {
            a2.append('0');
        }
        if (a3 != 0) {
            a2.append(a3);
        }
    }
    
    static {
        GMT = TimeZone.getTimeZone("GMT");
        RFC3339_PATTERN = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})([Tt](\\d{2}):(\\d{2}):(\\d{2})(\\.\\d+)?)?([Zz]|([+-])(\\d{2}):(\\d{2}))?");
    }
}
