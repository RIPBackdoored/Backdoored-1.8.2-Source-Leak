package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.regex.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.util.perf.*;
import org.apache.logging.log4j.*;

public class ExtensionClassExporter implements IExtension
{
    private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
    private static final String EXPORT_CLASS_DIR = "class";
    private static final String EXPORT_JAVA_DIR = "java";
    private static final Logger logger;
    private final File classExportDir;
    private final IDecompiler decompiler;
    
    public ExtensionClassExporter(final MixinEnvironment v2) {
        super();
        this.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
        this.decompiler = this.initDecompiler(v2, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
        try {
            FileUtils.deleteDirectory(this.classExportDir);
        }
        catch (IOException a1) {
            ExtensionClassExporter.logger.warn("Error cleaning class output directory: {}", new Object[] { a1.getMessage() });
        }
    }
    
    public boolean isDecompilerActive() {
        return this.decompiler != null;
    }
    
    private IDecompiler initDecompiler(final MixinEnvironment v-3, final File v-2) {
        if (!v-3.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
        }
        try {
            final boolean a1 = v-3.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            ExtensionClassExporter.logger.info("Attempting to load Fernflower decompiler{}", new Object[] { a1 ? " (Threaded mode)" : "" });
            final String a2 = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (a1 ? "Async" : "");
            final Class<? extends IDecompiler> v1 = (Class<? extends IDecompiler>)Class.forName(a2);
            final Constructor<? extends IDecompiler> v2 = v1.getDeclaredConstructor(File.class);
            final IDecompiler v3 = (IDecompiler)v2.newInstance(v-2);
            ExtensionClassExporter.logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { a1 ? " in a separate thread" : "" });
            return v3;
        }
        catch (Throwable t) {
            ExtensionClassExporter.logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { t.getClass().getSimpleName(), t.getMessage() });
            return null;
        }
    }
    
    private String prepareFilter(String a1) {
        a1 = "^\\Q" + a1.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
        return a1.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
    }
    
    private boolean applyFilter(final String a1, final String a2) {
        return Pattern.compile(this.prepareFilter(a1), 2).matcher(a2).matches();
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment a1) {
        return true;
    }
    
    @Override
    public void preApply(final ITargetClassContext a1) {
    }
    
    @Override
    public void postApply(final ITargetClassContext a1) {
    }
    
    @Override
    public void export(final MixinEnvironment v1, final String v2, final boolean v3, final byte[] v4) {
        if (v3 || v1.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            final String a3 = v1.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
            if (v3 || a3 == null || this.applyFilter(a3, v2)) {
                final Profiler.Section a4 = MixinEnvironment.getProfiler().begin("debug.export");
                final File a5 = this.dumpClass(v2.replace('.', '/'), v4);
                if (this.decompiler != null) {
                    this.decompiler.decompile(a5);
                }
                a4.end();
            }
        }
    }
    
    public File dumpClass(final String a1, final byte[] a2) {
        final File v1 = new File(this.classExportDir, a1 + ".class");
        try {
            FileUtils.writeByteArrayToFile(v1, a2);
        }
        catch (IOException ex) {}
        return v1;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
