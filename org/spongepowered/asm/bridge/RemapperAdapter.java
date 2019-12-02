package org.spongepowered.asm.bridge;

import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.util.*;
import org.objectweb.asm.commons.*;
import org.apache.logging.log4j.*;

public abstract class RemapperAdapter implements IRemapper, ObfuscationUtil.IClassRemapper
{
    protected final Logger logger;
    protected final Remapper remapper;
    
    public RemapperAdapter(final Remapper a1) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.remapper = a1;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public String mapMethodName(final String a1, final String a2, final String a3) {
        this.logger.debug("{} is remapping method {}{} for {}", new Object[] { this, a2, a3, a1 });
        final String v1 = this.remapper.mapMethodName(a1, a2, a3);
        if (!v1.equals(a2)) {
            return v1;
        }
        final String v2 = this.unmap(a1);
        final String v3 = this.unmapDesc(a3);
        this.logger.debug("{} is remapping obfuscated method {}{} for {}", new Object[] { this, a2, v3, v2 });
        return this.remapper.mapMethodName(v2, a2, v3);
    }
    
    @Override
    public String mapFieldName(final String a1, final String a2, final String a3) {
        this.logger.debug("{} is remapping field {}{} for {}", new Object[] { this, a2, a3, a1 });
        final String v1 = this.remapper.mapFieldName(a1, a2, a3);
        if (!v1.equals(a2)) {
            return v1;
        }
        final String v2 = this.unmap(a1);
        final String v3 = this.unmapDesc(a3);
        this.logger.debug("{} is remapping obfuscated field {}{} for {}", new Object[] { this, a2, v3, v2 });
        return this.remapper.mapFieldName(v2, a2, v3);
    }
    
    @Override
    public String map(final String a1) {
        this.logger.debug("{} is remapping class {}", new Object[] { this, a1 });
        return this.remapper.map(a1);
    }
    
    @Override
    public String unmap(final String a1) {
        return a1;
    }
    
    @Override
    public String mapDesc(final String a1) {
        return this.remapper.mapDesc(a1);
    }
    
    @Override
    public String unmapDesc(final String a1) {
        return ObfuscationUtil.unmapDescriptor(a1, this);
    }
}
