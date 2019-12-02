package a.a.h.b.a;

import a.a.h.a.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.*;
import a.a.k.*;

public class j extends b
{
    public j() {
        super("Time");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        final String format = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        a.a.k.a.a(format, this.d().bco, this.d().bcp);
        this.e().bco = j.mc.fontRenderer.getStringWidth(format);
        this.e().bcp = j.mc.fontRenderer.FONT_HEIGHT;
    }
}
