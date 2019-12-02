package a.a.h.b.a;

import a.a.h.a.*;
import a.a.k.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;

public class h extends b
{
    public h() {
        super("Ping");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        int responseTime = 0;
        if (h.mc.world != null && h.mc.world.isRemote && h.mc.getConnection() != null) {
            final EntityPlayerSP player = h.mc.player;
            if (player != null) {
                final NetworkPlayerInfo playerInfo = h.mc.getConnection().getPlayerInfo(((EntityPlayer)player).getUniqueID());
                if (playerInfo != null) {
                    responseTime = playerInfo.getResponseTime();
                }
            }
        }
        final String string = responseTime + " ping";
        a.a.k.a.a(string, this.d().bco, this.d().bcp);
        this.e().bco = h.mc.fontRenderer.getStringWidth(string);
        this.e().bcp = h.mc.fontRenderer.FONT_HEIGHT;
    }
}
