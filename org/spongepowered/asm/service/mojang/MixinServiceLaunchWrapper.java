package org.spongepowered.asm.service.mojang;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.launch.*;
import com.google.common.collect.*;
import org.spongepowered.asm.mixin.throwables.*;
import java.util.*;
import java.net.*;
import org.apache.commons.io.*;
import java.io.*;
import org.spongepowered.asm.util.perf.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import net.minecraft.launchwrapper.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.*;

public class MixinServiceLaunchWrapper implements IMixinService, IClassProvider, IClassBytecodeProvider
{
    public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
    public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
    private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
    private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
    private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
    private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
    private static final Logger logger;
    private final LaunchClassLoaderUtil classLoaderUtil;
    private final ReEntranceLock lock;
    private IClassNameTransformer nameTransformer;
    
    public MixinServiceLaunchWrapper() {
        super();
        this.classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
        this.lock = new ReEntranceLock(1);
    }
    
    @Override
    public String getName() {
        return "LaunchWrapper";
    }
    
    @Override
    public boolean isValid() {
        try {
            Launch.classLoader.hashCode();
        }
        catch (Throwable v1) {
            return false;
        }
        return true;
    }
    
    @Override
    public void prepare() {
        Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
    }
    
    @Override
    public MixinEnvironment.Phase getInitialPhase() {
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
            return MixinEnvironment.Phase.DEFAULT;
        }
        return MixinEnvironment.Phase.PREINIT;
    }
    
    @Override
    public void init() {
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
            MixinServiceLaunchWrapper.logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
        }
        final List<String> v1 = GlobalProperties.get("TweakClasses");
        if (v1 != null) {
            v1.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
        }
    }
    
    @Override
    public ReEntranceLock getReEntranceLock() {
        return this.lock;
    }
    
    @Override
    public Collection<String> getPlatformAgents() {
        return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
    }
    
    @Override
    public IClassProvider getClassProvider() {
        return this;
    }
    
    @Override
    public IClassBytecodeProvider getBytecodeProvider() {
        return this;
    }
    
    @Override
    public Class<?> findClass(final String a1) throws ClassNotFoundException {
        return (Class<?>)Launch.classLoader.findClass(a1);
    }
    
    @Override
    public Class<?> findClass(final String a1, final boolean a2) throws ClassNotFoundException {
        return Class.forName(a1, a2, (ClassLoader)Launch.classLoader);
    }
    
    @Override
    public Class<?> findAgentClass(final String a1, final boolean a2) throws ClassNotFoundException {
        return Class.forName(a1, a2, Launch.class.getClassLoader());
    }
    
    @Override
    public void beginPhase() {
        Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
    }
    
    @Override
    public void checkEnv(final Object a1) {
        if (a1.getClass().getClassLoader() != Launch.class.getClassLoader()) {
            throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
        }
    }
    
    @Override
    public InputStream getResourceAsStream(final String a1) {
        return Launch.classLoader.getResourceAsStream(a1);
    }
    
    @Override
    public void registerInvalidClass(final String a1) {
        this.classLoaderUtil.registerInvalidClass(a1);
    }
    
    @Override
    public boolean isClassLoaded(final String a1) {
        return this.classLoaderUtil.isClassLoaded(a1);
    }
    
    @Override
    public String getClassRestrictions(final String a1) {
        String v1 = "";
        if (this.classLoaderUtil.isClassClassLoaderExcluded(a1, null)) {
            v1 = "PACKAGE_CLASSLOADER_EXCLUSION";
        }
        if (this.classLoaderUtil.isClassTransformerExcluded(a1, null)) {
            v1 = ((v1.length() > 0) ? (v1 + ",") : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
        }
        return v1;
    }
    
    @Override
    public URL[] getClassPath() {
        return Launch.classLoader.getSources().toArray(new URL[0]);
    }
    
    @Override
    public Collection<ITransformer> getTransformers() {
        final List<IClassTransformer> transformers = (List<IClassTransformer>)Launch.classLoader.getTransformers();
        final List<ITransformer> list = new ArrayList<ITransformer>(transformers.size());
        for (final IClassTransformer v1 : transformers) {
            if (v1 instanceof ITransformer) {
                list.add((ITransformer)v1);
            }
            else {
                list.add(new LegacyTransformerHandle(v1));
            }
            if (v1 instanceof IClassNameTransformer) {
                MixinServiceLaunchWrapper.logger.debug("Found name transformer: {}", new Object[] { v1.getClass().getName() });
                this.nameTransformer = (IClassNameTransformer)v1;
            }
        }
        return list;
    }
    
    @Override
    public byte[] getClassBytes(final String v2, final String v3) throws IOException {
        final byte[] v4 = Launch.classLoader.getClassBytes(v2);
        if (v4 != null) {
            return v4;
        }
        final URLClassLoader v5 = (URLClassLoader)Launch.class.getClassLoader();
        InputStream v6 = null;
        try {
            final String a1 = v3.replace('.', '/').concat(".class");
            v6 = v5.getResourceAsStream(a1);
            return IOUtils.toByteArray(v6);
        }
        catch (Exception a2) {
            return null;
        }
        finally {
            IOUtils.closeQuietly(v6);
        }
    }
    
    @Override
    public byte[] getClassBytes(final String v1, final boolean v2) throws ClassNotFoundException, IOException {
        final String v3 = v1.replace('/', '.');
        final String v4 = this.unmapClassName(v3);
        final Profiler v5 = MixinEnvironment.getProfiler();
        final Profiler.Section v6 = v5.begin(1, "class.load");
        byte[] v7 = this.getClassBytes(v4, v3);
        v6.end();
        if (v2) {
            final Profiler.Section a1 = v5.begin(1, "class.transform");
            v7 = this.applyTransformers(v4, v3, v7, v5);
            a1.end();
        }
        if (v7 == null) {
            throw new ClassNotFoundException(String.format("The specified class '%s' was not found", v3));
        }
        return v7;
    }
    
    private byte[] applyTransformers(final String v2, final String v3, byte[] v4, final Profiler v5) {
        if (this.classLoaderUtil.isClassExcluded(v2, v3)) {
            return v4;
        }
        final MixinEnvironment v6 = MixinEnvironment.getCurrentEnvironment();
        for (final ILegacyClassTransformer a4 : v6.getTransformers()) {
            this.lock.clear();
            final int a5 = a4.getName().lastIndexOf(46);
            final String a6 = a4.getName().substring(a5 + 1);
            final Profiler.Section a7 = v5.begin(2, a6.toLowerCase());
            a7.setInfo(a4.getName());
            v4 = a4.transformClassBytes(v2, v3, v4);
            a7.end();
            if (this.lock.isSet()) {
                v6.addTransformerExclusion(a4.getName());
                this.lock.clear();
                MixinServiceLaunchWrapper.logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[] { a4.getName() });
            }
        }
        return v4;
    }
    
    private String unmapClassName(final String a1) {
        if (this.nameTransformer == null) {
            this.findNameTransformer();
        }
        if (this.nameTransformer != null) {
            return this.nameTransformer.unmapClassName(a1);
        }
        return a1;
    }
    
    private void findNameTransformer() {
        final List<IClassTransformer> transformers = (List<IClassTransformer>)Launch.classLoader.getTransformers();
        for (final IClassTransformer v1 : transformers) {
            if (v1 instanceof IClassNameTransformer) {
                MixinServiceLaunchWrapper.logger.debug("Found name transformer: {}", new Object[] { v1.getClass().getName() });
                this.nameTransformer = (IClassNameTransformer)v1;
            }
        }
    }
    
    @Override
    public ClassNode getClassNode(final String a1) throws ClassNotFoundException, IOException {
        return this.getClassNode(this.getClassBytes(a1, true), 0);
    }
    
    private ClassNode getClassNode(final byte[] a1, final int a2) {
        final ClassNode v1 = new ClassNode();
        final ClassReader v2 = new ClassReader(a1);
        v2.accept(v1, a2);
        return v1;
    }
    
    @Override
    public final String getSideName() {
        for (final ITweaker v1 : GlobalProperties.get("Tweaks")) {
            if (v1.getClass().getName().endsWith(".common.launcher.FMLServerTweaker")) {
                return "SERVER";
            }
            if (v1.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
                return "CLIENT";
            }
        }
        String v2 = this.getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
        if (v2 != null) {
            return v2;
        }
        v2 = this.getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
        if (v2 != null) {
            return v2;
        }
        v2 = this.getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
        if (v2 != null) {
            return v2;
        }
        return "UNKNOWN";
    }
    
    private String getSideName(final String v-1, final String v0) {
        try {
            final Class<?> a1 = Class.forName(v-1, false, (ClassLoader)Launch.classLoader);
            final Method a2 = a1.getDeclaredMethod(v0, (Class<?>[])new Class[0]);
            return ((Enum)a2.invoke(null, new Object[0])).name();
        }
        catch (Exception v) {
            return null;
        }
    }
    
    private static int findInStackTrace(final String a2, final String v1) {
        final Thread v2 = Thread.currentThread();
        if (!"main".equals(v2.getName())) {
            return 0;
        }
        final StackTraceElement[] stackTrace;
        final StackTraceElement[] v3 = stackTrace = v2.getStackTrace();
        for (final StackTraceElement a3 : stackTrace) {
            if (a2.equals(a3.getClassName()) && v1.equals(a3.getMethodName())) {
                return a3.getLineNumber();
            }
        }
        return 0;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
