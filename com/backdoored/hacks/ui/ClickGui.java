package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.f.*;
import net.minecraft.client.gui.*;

@b.a(name = "ClickGui", description = "Backdoored's main GUI", category = CategoriesInit.UI, defaultIsVisible = false, defaultBind = "SEMICOLON")
public class ClickGui extends BaseHack
{
    public ClickGui() {
        super();
    }
    
    public void onEnabled() {
        ClickGui.mc.displayGuiScreen((GuiScreen)new a());
        this.setEnabled(false);
    }
}
