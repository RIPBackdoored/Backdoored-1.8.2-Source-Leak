package a.a.d.b.g;

import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import net.minecraft.client.shader.*;
import net.minecraft.util.text.*;

@Cancelable
public class e extends BackdooredEvent
{
    public File do;
    public String dp;
    public int dq;
    public int dr;
    public Framebuffer framebuffer;
    public ITextComponent iTextComponent;
    
    public e(final File do1, final String dp, final int dq, final int dr, final Framebuffer framebuffer) {
        super();
        this.do = do1;
        this.dp = dp;
        this.dq = dq;
        this.dr = dr;
        this.framebuffer = framebuffer;
        this.iTextComponent = (ITextComponent)new TextComponentString("Screenshot meant to be here?");
    }
}
