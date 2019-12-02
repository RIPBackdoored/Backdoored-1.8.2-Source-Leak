package org.yaml.snakeyaml.representer;

import java.util.*;
import org.yaml.snakeyaml.nodes.*;

protected class RepresentDate implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentDate(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        Calendar calendar;
        if (data instanceof Calendar) {
            calendar = (Calendar)data;
        }
        else {
            calendar = Calendar.getInstance((this.this$0.getTimeZone() == null) ? TimeZone.getTimeZone("UTC") : this.this$0.timeZone);
            calendar.setTime((Date)data);
        }
        final int years = calendar.get(1);
        final int months = calendar.get(2) + 1;
        final int days = calendar.get(5);
        final int hour24 = calendar.get(11);
        final int minutes = calendar.get(12);
        final int seconds = calendar.get(13);
        final int millis = calendar.get(14);
        final StringBuilder buffer = new StringBuilder(String.valueOf(years));
        while (buffer.length() < 4) {
            buffer.insert(0, "0");
        }
        buffer.append("-");
        if (months < 10) {
            buffer.append("0");
        }
        buffer.append(String.valueOf(months));
        buffer.append("-");
        if (days < 10) {
            buffer.append("0");
        }
        buffer.append(String.valueOf(days));
        buffer.append("T");
        if (hour24 < 10) {
            buffer.append("0");
        }
        buffer.append(String.valueOf(hour24));
        buffer.append(":");
        if (minutes < 10) {
            buffer.append("0");
        }
        buffer.append(String.valueOf(minutes));
        buffer.append(":");
        if (seconds < 10) {
            buffer.append("0");
        }
        buffer.append(String.valueOf(seconds));
        if (millis > 0) {
            if (millis < 10) {
                buffer.append(".00");
            }
            else if (millis < 100) {
                buffer.append(".0");
            }
            else {
                buffer.append(".");
            }
            buffer.append(String.valueOf(millis));
        }
        if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
            buffer.append("Z");
        }
        else {
            final int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
            final int minutesOffset = gmtOffset / 60000;
            final int hoursOffset = minutesOffset / 60;
            final int partOfHour = minutesOffset % 60;
            buffer.append(((hoursOffset > 0) ? "+" : "") + hoursOffset + ":" + ((partOfHour < 10) ? ("0" + partOfHour) : Integer.valueOf(partOfHour)));
        }
        return this.this$0.representScalar(this.this$0.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
    }
}
