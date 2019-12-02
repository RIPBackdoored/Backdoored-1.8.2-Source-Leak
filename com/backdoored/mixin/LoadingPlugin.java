package com.backdoored.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class LoadingPlugin implements IFMLLoadingPlugin
{
    public LoadingPlugin() {
        super();
        System.out.println("Backdoored Coremod initialising");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.backdoored.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> a1) {
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
