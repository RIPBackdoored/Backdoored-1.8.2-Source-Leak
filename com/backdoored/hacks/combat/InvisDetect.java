package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.init.*;
import a.a.k.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "InvisDetect", description = "Can help locate people in entity god mode", category = CategoriesInit.COMBAT)
public class InvisDetect extends BaseHack
{
    public InvisDetect() {
        super();
    }
    
    @SubscribeEvent
    public void a(final PlaySoundAtEntityEvent playSoundAtEntityEvent) {
        if (playSoundAtEntityEvent.getEntity() == null) {
            return;
        }
        if (playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_PIG_STEP) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_HORSE_STEP) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_HORSE_STEP_WOOD) || playSoundAtEntityEvent.getSound().equals(SoundEvents.ENTITY_LLAMA_STEP)) {
            Utils.printMessage("Invis PlayerPreviewElement at: " + e.a(playSoundAtEntityEvent.getEntity().getPositionVector(), new boolean[0]));
        }
    }
}
