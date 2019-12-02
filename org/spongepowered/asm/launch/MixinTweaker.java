package org.spongepowered.asm.launch;

import java.util.*;
import java.io.*;
import net.minecraft.launchwrapper.*;

public class MixinTweaker implements ITweaker
{
    public MixinTweaker() {
        super();
        MixinBootstrap.start();
    }
    
    public final void acceptOptions(final List<String> a1, final File a2, final File a3, final String a4) {
        MixinBootstrap.doInit(a1);
    }
    
    public final void injectIntoClassLoader(final LaunchClassLoader a1) {
        MixinBootstrap.inject();
    }
    
    public String getLaunchTarget() {
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }
    
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
