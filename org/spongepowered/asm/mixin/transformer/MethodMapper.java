package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import com.google.common.base.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class MethodMapper
{
    private static final Logger logger;
    private static final List<String> classes;
    private static final Map<String, Counter> methods;
    private final ClassInfo info;
    
    public MethodMapper(final MixinEnvironment a1, final ClassInfo a2) {
        super();
        this.info = a2;
    }
    
    public ClassInfo getClassInfo() {
        return this.info;
    }
    
    public void remapHandlerMethod(final MixinInfo a1, final MethodNode a2, final ClassInfo.Method a3) {
        if (!(a2 instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode)a2).isInjector()) {
            return;
        }
        if (a3.isUnique()) {
            MethodMapper.logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[] { a3, a1 });
        }
        if (a3.isRenamed()) {
            a2.name = a3.getName();
            return;
        }
        final String v1 = this.getHandlerName((MixinInfo.MixinMethodNode)a2);
        a2.name = a3.renameTo(v1);
    }
    
    public String getHandlerName(final MixinInfo.MixinMethodNode a1) {
        final String v1 = InjectionInfo.getInjectorPrefix(a1.getInjectorAnnotation());
        final String v2 = getClassUID(a1.getOwner().getClassRef());
        final String v3 = getMethodUID(a1.name, a1.desc, !a1.isSurrogate());
        return String.format("%s$%s$%s%s", v1, a1.name, v2, v3);
    }
    
    private static String getClassUID(final String a1) {
        int v1 = MethodMapper.classes.indexOf(a1);
        if (v1 < 0) {
            v1 = MethodMapper.classes.size();
            MethodMapper.classes.add(a1);
        }
        return finagle(v1);
    }
    
    private static String getMethodUID(final String a1, final String a2, final boolean a3) {
        final String v1 = String.format("%s%s", a1, a2);
        Counter v2 = MethodMapper.methods.get(v1);
        if (v2 == null) {
            v2 = new Counter();
            MethodMapper.methods.put(v1, v2);
        }
        else if (a3) {
            final Counter counter = v2;
            ++counter.value;
        }
        return String.format("%03x", v2.value);
    }
    
    private static String finagle(final int v-2) {
        final String hexString = Integer.toHexString(v-2);
        final StringBuilder v0 = new StringBuilder();
        for (int v2 = 0; v2 < hexString.length(); ++v2) {
            char a1 = hexString.charAt(v2);
            v0.append(a1 += ((a1 < ':') ? '1' : '\n'));
        }
        return Strings.padStart(v0.toString(), 3, 'z');
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        classes = new ArrayList<String>();
        methods = new HashMap<String, Counter>();
    }
}
