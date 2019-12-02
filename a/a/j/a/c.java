package a.a.j.a;

import net.minecraft.client.gui.*;
import java.util.*;

public class c extends GuiScreen
{
    public static c bbe;
    private GuiScreen guiScreen;
    
    public c(final GuiScreen guiScreen) {
        super();
        this.guiScreen = guiScreen;
    }
    
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 60 + 76, "Exit"));
        b.a();
        final b b = new b(this, "test1", true);
        final b b2 = new b(this, "test2", false);
        final b b3 = new b(this, "test3", false);
        final b b4 = new b(this, "test4", true);
        super.initGui();
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(this.guiScreen);
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Backdoored Client Performance Options", this.width / 2, 40, 16777215);
        b.b(this);
        super.drawScreen(n, n2, n3);
    }
    
    public List<GuiButton> a() {
        return (List<GuiButton>)this.buttonList;
    }
    
    static {
        c.bbe = new c((GuiScreen)new a());
    }
}
