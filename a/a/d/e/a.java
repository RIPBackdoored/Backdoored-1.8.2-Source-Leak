package a.a.d.e;

import com.backdoored.event.*;
import net.minecraft.client.renderer.chunk.*;

public abstract class a extends BackdooredEvent
{
    public a() {
        super();
    }
    
    public static class b extends a
    {
        public RenderChunk renderChunk;
        public int eg;
        public int eh;
        public int ei;
        
        public b(final RenderChunk renderChunk, final int eg, final int eh, final int ei) {
            super();
            this.renderChunk = renderChunk;
            this.eg = eg;
            this.eh = eh;
            this.ei = ei;
        }
    }
    
    public static class a extends a.a.d.e.a
    {
        public RenderChunk renderChunk;
        
        public a(final RenderChunk renderChunk) {
            super();
            this.renderChunk = renderChunk;
        }
    }
}
