package com.backdoored.mixin.minecraftforge;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.fml.common.*;
import java.io.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.libraries.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Loader.class })
public class MixinLoader
{
    public MixinLoader() {
        super();
    }
    
    @Redirect(method = { "identifyMods" }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/relauncher/libraries/LibraryManager;gatherLegacyCanidates(Ljava/io/File;)Ljava/util/List;", remap = false), remap = false)
    protected static List<File> gatherLegacyCanidates(final File a1) {
        System.out.println("Called gatherLegacyCandidates mixin");
        return (List<File>)LibraryManager.gatherLegacyCanidates(a1);
    }
}
