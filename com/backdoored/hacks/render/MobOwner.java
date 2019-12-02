package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import a.a.a.*;
import java.util.*;

@b.a(name = "Mob Owner", description = "Show you owners of mobs", category = CategoriesInit.RENDER)
public class MobOwner extends BaseHack
{
    public MobOwner() {
        super();
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        for (final Entity entity : MobOwner.mc.world.loadedEntityList) {
            if (entity instanceof EntityTameable) {
                final EntityTameable entityTameable = (EntityTameable)entity;
                if (entityTameable.isTamed() && entityTameable.getOwner() != null) {
                    entityTameable.setAlwaysRenderNameTag(true);
                    entityTameable.setCustomNameTag("Owner: " + entityTameable.getOwner().getDisplayName().getFormattedText());
                }
            }
            if (entity instanceof AbstractHorse) {
                final AbstractHorse abstractHorse = (AbstractHorse)entity;
                if (!abstractHorse.isTame() || abstractHorse.getOwnerUniqueId() == null) {
                    continue;
                }
                abstractHorse.setAlwaysRenderNameTag(true);
                abstractHorse.setCustomNameTag("Owner: " + d.a(abstractHorse.getOwnerUniqueId().toString()));
            }
        }
    }
    
    public void onDisabled() {
        for (final Entity entity : MobOwner.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityTameable)) {
                if (!(entity instanceof AbstractHorse)) {
                    continue;
                }
            }
            try {
                entity.setAlwaysRenderNameTag(false);
            }
            catch (Exception ex) {}
        }
    }
}
