package a.a.g.b.b;

import java.text.*;
import net.minecraft.client.shader.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import javax.imageio.*;
import java.awt.image.*;
import net.minecraft.util.text.event.*;
import com.backdoored.utils.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import java.util.*;

public class o implements Runnable
{
    private static final SimpleDateFormat jx;
    private int jy;
    private int jz;
    private int[] ka;
    private Framebuffer framebuffer;
    private File kc;
    
    public o(final int jy, final int jz, final int[] ka, final Framebuffer framebuffer, final File kc) {
        super();
        this.jy = jy;
        this.jz = jz;
        this.ka = ka;
        this.framebuffer = framebuffer;
        this.kc = kc;
    }
    
    @Override
    public void run() {
        a(this.ka, this.jy, this.jz);
        try {
            BufferedImage bufferedImage;
            if (OpenGlHelper.isFramebufferEnabled()) {
                bufferedImage = new BufferedImage(this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 1);
                int n;
                for (int i = n = this.framebuffer.framebufferTextureHeight - this.framebuffer.framebufferHeight; i < this.framebuffer.framebufferTextureHeight; ++i) {
                    for (int j = 0; j < this.framebuffer.framebufferWidth; ++j) {
                        bufferedImage.setRGB(j, i - n, this.ka[i * this.framebuffer.framebufferTextureWidth + j]);
                    }
                }
            }
            else {
                bufferedImage = new BufferedImage(this.jy, this.jz, 1);
                bufferedImage.setRGB(0, 0, this.jy, this.jz, this.ka, 0, this.jy);
            }
            final File a = a(this.kc);
            ImageIO.write(bufferedImage, "png", a);
            final TextComponentString textComponentString = new TextComponentString(a.getName());
            ((ITextComponent)textComponentString).getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, a.getAbsolutePath()));
            ((ITextComponent)textComponentString).getStyle().setUnderlined(Boolean.valueOf(true));
            Utils.a((ITextComponent)new TextComponentTranslation("screenshot.success", new Object[] { textComponentString }));
        }
        catch (Exception ex) {
            FMLLog.log.info("Failed to save screenshot");
            ex.printStackTrace();
            Utils.a((ITextComponent)new TextComponentTranslation("screenshot.failure", new Object[] { ex.getMessage() }));
        }
    }
    
    private static void a(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n];
        for (int n3 = n2 / 2, i = 0; i < n3; ++i) {
            System.arraycopy(array, i * n, array2, 0, n);
            System.arraycopy(array, (n2 - 1 - i) * n, array, i * n, n);
            System.arraycopy(array2, 0, array, (n2 - 1 - i) * n, n);
        }
    }
    
    private static File a(final File file) {
        final String format = o.jx.format(new Date());
        int n = 1;
        File file2;
        while (true) {
            file2 = new File(file, format + ((n == 1) ? "" : ("_" + n)) + ".png");
            if (!file2.exists()) {
                break;
            }
            ++n;
        }
        return file2;
    }
    
    static {
        jx = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
