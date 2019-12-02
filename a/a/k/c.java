package a.a.k;

import java.time.*;
import java.time.temporal.*;

public class c
{
    public c() {
        super();
    }
    
    public static Instant a() {
        return Instant.now();
    }
    
    public static long a(final Instant instant, final Instant instant2) {
        return Duration.between(instant, instant2).getSeconds();
    }
    
    public static boolean a(final Instant instant, final Instant instant2, final long n) {
        return a(instant, instant2) >= n;
    }
    
    public static long b(final Instant instant, final Instant instant2) {
        return Duration.between(instant, instant2).toMillis();
    }
    
    public static boolean b(final Instant instant, final Instant instant2, final long n) {
        return b(instant, instant2) >= n;
    }
}
