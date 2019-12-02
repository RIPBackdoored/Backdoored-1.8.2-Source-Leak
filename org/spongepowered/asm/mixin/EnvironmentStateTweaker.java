package org.spongepowered.asm.mixin;

import java.util.*;
import java.io.*;
import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.launch.*;

public class EnvironmentStateTweaker implements ITweaker
{
    public EnvironmentStateTweaker() {
        super();
    }
    
    public void acceptOptions(final List<String> a1, final File a2, final File a3, final String a4) {
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader a1) {
        MixinBootstrap.getPlatform().inject();
    }
    
    public String getLaunchTarget() {
        return "";
    }
    
    public String[] getLaunchArguments() {
        MixinEnvironment.gotoPhase(MixinEnvironment.Phase.DEFAULT);
        return new String[0];
    }
}
