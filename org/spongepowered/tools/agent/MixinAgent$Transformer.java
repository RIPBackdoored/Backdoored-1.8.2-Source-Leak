package org.spongepowered.tools.agent;

import java.security.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import java.lang.instrument.*;
import org.spongepowered.asm.service.*;
import java.util.*;

class Transformer implements ClassFileTransformer
{
    final /* synthetic */ MixinAgent this$0;
    
    Transformer(final MixinAgent a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public byte[] transform(final ClassLoader a4, final String a5, final Class<?> v1, final ProtectionDomain v2, final byte[] v3) throws IllegalClassFormatException {
        if (v1 == null) {
            return null;
        }
        final byte[] v4 = MixinAgent.classLoader.getFakeMixinBytecode(v1);
        if (v4 != null) {
            final List<String> a6 = this.reloadMixin(a5, v3);
            if (a6 == null || !this.reApplyMixins(a6)) {
                return MixinAgent.ERROR_BYTECODE;
            }
            return v4;
        }
        else {
            try {
                MixinAgent.logger.info("Redefining class " + a5);
                return this.this$0.classTransformer.transformClassBytes(null, a5, v3);
            }
            catch (Throwable a7) {
                MixinAgent.logger.error("Error while re-transforming class " + a5, a7);
                return MixinAgent.ERROR_BYTECODE;
            }
        }
    }
    
    private List<String> reloadMixin(final String v2, final byte[] v3) {
        MixinAgent.logger.info("Redefining mixin {}", new Object[] { v2 });
        try {
            return this.this$0.classTransformer.reload(v2.replace('/', '.'), v3);
        }
        catch (MixinReloadException a1) {
            MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[] { a1.getMixinInfo(), a1.getMessage() });
        }
        catch (Throwable a2) {
            MixinAgent.logger.error("Error while finding targets for mixin " + v2, a2);
        }
        return null;
    }
    
    private boolean reApplyMixins(final List<String> v-5) {
        final IMixinService service = MixinService.getService();
        for (final String s : v-5) {
            final String replace = s.replace('/', '.');
            MixinAgent.logger.debug("Re-transforming target class {}", new Object[] { s });
            try {
                final Class<?> a1 = service.getClassProvider().findClass(replace);
                byte[] v1 = MixinAgent.classLoader.getOriginalTargetBytecode(replace);
                if (v1 == null) {
                    MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[] { replace });
                    return false;
                }
                v1 = this.this$0.classTransformer.transformClassBytes(null, replace, v1);
                MixinAgent.instrumentation.redefineClasses(new ClassDefinition(a1, v1));
            }
            catch (Throwable v2) {
                MixinAgent.logger.error("Error while re-transforming target class " + s, v2);
                return false;
            }
        }
        return true;
    }
}
