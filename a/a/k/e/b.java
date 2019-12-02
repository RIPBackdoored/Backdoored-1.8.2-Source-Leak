package a.a.k.e;

import a.a.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.realms.*;
import net.minecraft.client.gui.*;

public class b implements e
{
    public b() {
        super();
    }
    
    public double b() {
        double n = 0.2873;
        final PotionEffect activePotionEffect = b.mc.player.getActivePotionEffect(MobEffects.SPEED);
        if (b.mc.player.isPotionActive(MobEffects.SPEED) && activePotionEffect != null) {
            n *= 1.0 + 0.2 * (activePotionEffect.getAmplifier() + 1);
        }
        return n;
    }
    
    public static void c() {
        b.mc.world.sendQuittingDisconnectingPacket();
        b.mc.loadWorld((WorldClient)null);
        if (b.mc.isIntegratedServerRunning()) {
            b.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
        }
        else if (b.mc.isConnectedToRealms()) {
            new RealmsBridge().switchToRealms((GuiScreen)new GuiMainMenu());
        }
        else {
            b.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
        }
    }
}
