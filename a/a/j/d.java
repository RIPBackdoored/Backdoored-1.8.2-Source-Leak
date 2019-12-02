package a.a.j;

import com.backdoored.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.awt.*;

public class d extends GuiMainMenu
{
    public d() {
        super();
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(62, 2, 2, 98, 20, "Login"));
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.displayString.equals("Login")) {
            Globals.mc.displayGuiScreen((GuiScreen)new c((GuiScreen)this));
        }
        else {
            super.actionPerformed(guiButton);
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        String s = "[ONLINE]";
        Color color = Color.GREEN;
        if (!a.a.a.d.c()) {
            s = "[OFFLINE]";
            color = Color.RED;
        }
        String s2 = "[ONLINE]";
        Color color2 = Color.GREEN;
        if (!a.a.a.d.d()) {
            s2 = "[OFFLINE]";
            color2 = Color.RED;
        }
        this.mc.fontRenderer.drawString("Auth Server:     " + s, 2.0f, 25.0f, color.getRGB(), true);
        this.mc.fontRenderer.drawString("Session Server: " + s2, 2.0f, (float)(25 + this.mc.fontRenderer.FONT_HEIGHT + 2), color2.getRGB(), true);
    }
}
