package org.spongepowered.asm.bridge;

import org.objectweb.asm.commons.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.lang.reflect.*;

public final class RemapperAdapterFML extends RemapperAdapter
{
    private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String INSTANCE_FIELD = "INSTANCE";
    private static final String UNMAP_METHOD = "unmap";
    private final Method mdUnmap;
    
    private RemapperAdapterFML(final Remapper a1, final Method a2) {
        super(a1);
        this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[] { a1 });
        this.mdUnmap = a2;
    }
    
    @Override
    public String unmap(final String v2) {
        try {
            return this.mdUnmap.invoke(this.remapper, v2).toString();
        }
        catch (Exception a1) {
            return v2;
        }
    }
    
    public static IRemapper create() {
        try {
            final Class<?> v1 = getFMLDeobfuscatingRemapper();
            final Field v2 = v1.getDeclaredField("INSTANCE");
            final Method v3 = v1.getDeclaredMethod("unmap", String.class);
            final Remapper v4 = (Remapper)v2.get(null);
            return new RemapperAdapterFML(v4, v3);
        }
        catch (Exception v5) {
            v5.printStackTrace();
            return null;
        }
    }
    
    private static Class<?> getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
        try {
            return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
        catch (ClassNotFoundException v1) {
            return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
    }
}
