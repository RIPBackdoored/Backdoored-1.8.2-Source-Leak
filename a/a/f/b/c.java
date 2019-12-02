package a.a.f.b;

import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class c
{
    public c() {
        super();
    }
    
    public void a() {
    }
    
    @SubscribeEvent
    public void a(final RenderGameOverlayEvent.Post post) {
        if (post.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        this.a();
    }
}
