package a.a.j.a;

import com.backdoored.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class a extends GuiIngameMenu
{
    public a() {
        super();
    }
    
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(69, this.width / 2 - 100, this.height / 4 - 16, "Backdoored"));
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 69) {
            Globals.mc.displayGuiScreen((GuiScreen)c.bbe);
        }
        else {
            super.actionPerformed(guiButton);
        }
    }
}
