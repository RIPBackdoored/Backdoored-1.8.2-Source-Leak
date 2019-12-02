package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;
import java.util.regex.*;

public static class ConstructYamlTimestamp extends AbstractConstruct
{
    private Calendar calendar;
    
    public ConstructYamlTimestamp() {
        super();
    }
    
    public Calendar getCalendar() {
        return this.calendar;
    }
    
    @Override
    public Object construct(final Node node) {
        final ScalarNode scalar = (ScalarNode)node;
        final String nodeValue = scalar.getValue();
        Matcher match = SafeConstructor.access$200().matcher(nodeValue);
        if (match.matches()) {
            final String year_s = match.group(1);
            final String month_s = match.group(2);
            final String day_s = match.group(3);
            (this.calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))).clear();
            this.calendar.set(1, Integer.parseInt(year_s));
            this.calendar.set(2, Integer.parseInt(month_s) - 1);
            this.calendar.set(5, Integer.parseInt(day_s));
            return this.calendar.getTime();
        }
        match = SafeConstructor.access$300().matcher(nodeValue);
        if (!match.matches()) {
            throw new YAMLException("Unexpected timestamp: " + nodeValue);
        }
        final String year_s = match.group(1);
        final String month_s = match.group(2);
        final String day_s = match.group(3);
        final String hour_s = match.group(4);
        final String min_s = match.group(5);
        String seconds = match.group(6);
        final String millis = match.group(7);
        if (millis != null) {
            seconds = seconds + "." + millis;
        }
        final double fractions = Double.parseDouble(seconds);
        final int sec_s = (int)Math.round(Math.floor(fractions));
        final int usec = (int)Math.round((fractions - sec_s) * 1000.0);
        final String timezoneh_s = match.group(8);
        final String timezonem_s = match.group(9);
        TimeZone timeZone;
        if (timezoneh_s != null) {
            final String time = (timezonem_s != null) ? (":" + timezonem_s) : "00";
            timeZone = TimeZone.getTimeZone("GMT" + timezoneh_s + time);
        }
        else {
            timeZone = TimeZone.getTimeZone("UTC");
        }
        (this.calendar = Calendar.getInstance(timeZone)).set(1, Integer.parseInt(year_s));
        this.calendar.set(2, Integer.parseInt(month_s) - 1);
        this.calendar.set(5, Integer.parseInt(day_s));
        this.calendar.set(11, Integer.parseInt(hour_s));
        this.calendar.set(12, Integer.parseInt(min_s));
        this.calendar.set(13, sec_s);
        this.calendar.set(14, usec);
        return this.calendar.getTime();
    }
}
