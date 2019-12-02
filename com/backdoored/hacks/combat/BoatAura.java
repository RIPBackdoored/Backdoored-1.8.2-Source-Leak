package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.*;

@b.a(name = "Boat Aura", description = "Attack nearby boats", category = CategoriesInit.COMBAT)
public class BoatAura extends BaseHack
{
    private final Setting<Double> nf;
    private final Setting<Boolean> boats;
    private final Setting<Boolean> minecarts;
    
    public BoatAura() {
        super();
        this.nf = new DoubleSetting("Range", this, 5.0, 0.0, 10.0);
        this.boats = new BooleanSetting("Boats", this, true);
        this.minecarts = new BooleanSetting("Minecarts", this, true);
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            for (final Entity entity : BoatAura.mc.world.loadedEntityList) {
                if (!entity.getUniqueID().equals(BoatAura.mc.player.getUniqueID()) && ((entity instanceof EntityBoat && this.boats.getValInt()) || (entity instanceof EntityMinecart && this.minecarts.getValInt())) && BoatAura.mc.player.getDistance(entity) <= this.nf.getValInt()) {
                    BoatAura.mc.playerController.attackEntity((EntityPlayer)BoatAura.mc.player, entity);
                }
            }
        }
    }
}
