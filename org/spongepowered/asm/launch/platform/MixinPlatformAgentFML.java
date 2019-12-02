package org.spongepowered.asm.launch.platform;

import java.net.*;
import org.spongepowered.asm.launch.*;
import java.io.*;
import net.minecraft.launchwrapper.*;
import java.lang.reflect.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;
import java.util.*;

public class MixinPlatformAgentFML extends MixinPlatformAgentAbstract
{
    private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
    private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
    private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
    private static final String CORE_MOD_MANAGER_CLASS_LEGACY = "cpw.mods.fml.relauncher.CoreModManager";
    private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
    private static final String GET_IGNORED_MODS_METHOD_LEGACY = "getLoadedCoremods";
    private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
    private static final String FML_CMDLINE_COREMODS = "fml.coreMods.load";
    private static final String FML_PLUGIN_WRAPPER_CLASS = "FMLPluginWrapper";
    private static final String FML_CORE_MOD_INSTANCE_FIELD = "coreModInstance";
    private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
    private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
    private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
    private static final String FML_TWEAKER_DEOBF = "FMLDeobfTweaker";
    private static final String FML_TWEAKER_INJECTION = "FMLInjectionAndSortingTweaker";
    private static final String FML_TWEAKER_TERMINAL = "TerminalTweaker";
    private static final Set<String> loadedCoreMods;
    private final ITweaker coreModWrapper;
    private final String fileName;
    private Class<?> clCoreModManager;
    private boolean initInjectionState;
    
    public MixinPlatformAgentFML(final MixinPlatformManager a1, final URI a2) {
        super(a1, a2);
        this.fileName = this.container.getName();
        this.coreModWrapper = this.initFMLCoreMod();
    }
    
    private ITweaker initFMLCoreMod() {
        try {
            try {
                this.clCoreModManager = getCoreModManagerClass();
            }
            catch (ClassNotFoundException v1) {
                MixinPlatformAgentAbstract.logger.info("FML platform manager could not load class {}. Proceeding without FML support.", new Object[] { v1.getMessage() });
                return null;
            }
            if ("true".equalsIgnoreCase(this.attributes.get("ForceLoadAsMod"))) {
                MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[] { this.fileName });
                this.loadAsMod();
            }
            return this.injectCorePlugin();
        }
        catch (Exception v2) {
            MixinPlatformAgentAbstract.logger.catching((Throwable)v2);
            return null;
        }
    }
    
    private void loadAsMod() {
        try {
            getIgnoredMods(this.clCoreModManager).remove(this.fileName);
        }
        catch (Exception v1) {
            MixinPlatformAgentAbstract.logger.catching((Throwable)v1);
        }
        if (this.attributes.get("FMLCorePluginContainsFMLMod") != null) {
            if (this.isIgnoredReparseable()) {
                MixinPlatformAgentAbstract.logger.debug("Ignoring request to add {} to reparseable coremod collection - it is a deobfuscated dependency", new Object[] { this.fileName });
                return;
            }
            this.addReparseableJar();
        }
    }
    
    private boolean isIgnoredReparseable() {
        return this.container.toString().contains("deobfedDeps");
    }
    
    private void addReparseableJar() {
        try {
            final Method v1 = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.reparseablecoremodsmethod", "getReparseableCoremods"), (Class<?>[])new Class[0]);
            final List<String> v2 = (List<String>)v1.invoke(null, new Object[0]);
            if (!v2.contains(this.fileName)) {
                MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[] { this.fileName });
                v2.add(this.fileName);
            }
        }
        catch (Exception v3) {
            MixinPlatformAgentAbstract.logger.catching((Throwable)v3);
        }
    }
    
    private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final String v1 = this.attributes.get("FMLCorePlugin");
        if (v1 == null) {
            return null;
        }
        if (this.isAlreadyInjected(v1)) {
            MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Skipping because it was already injected.", new Object[] { this.fileName, v1 });
            return null;
        }
        MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[] { this.fileName, v1 });
        final Method v2 = this.clCoreModManager.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.loadcoremodmethod", "loadCoreMod"), LaunchClassLoader.class, String.class, File.class);
        v2.setAccessible(true);
        final ITweaker v3 = (ITweaker)v2.invoke(null, Launch.classLoader, v1, this.container);
        if (v3 == null) {
            MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[] { v1 });
            return null;
        }
        this.initInjectionState = isTweakerQueued("FMLInjectionAndSortingTweaker");
        MixinPlatformAgentFML.loadedCoreMods.add(v1);
        return v3;
    }
    
    private boolean isAlreadyInjected(final String v-5) {
        if (MixinPlatformAgentFML.loadedCoreMods.contains(v-5)) {
            return true;
        }
        try {
            final List<ITweaker> list = GlobalProperties.get("Tweaks");
            if (list == null) {
                return false;
            }
            for (final ITweaker tweaker : list) {
                final Class<? extends ITweaker> class1 = tweaker.getClass();
                if ("FMLPluginWrapper".equals(class1.getSimpleName())) {
                    final Field a1 = class1.getField("coreModInstance");
                    a1.setAccessible(true);
                    final Object v1 = a1.get(tweaker);
                    if (v-5.equals(v1.getClass().getName())) {
                        return true;
                    }
                    continue;
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    @Override
    public String getPhaseProvider() {
        return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
    }
    
    @Override
    public void prepare() {
        this.initInjectionState |= isTweakerQueued("FMLInjectionAndSortingTweaker");
    }
    
    @Override
    public void initPrimaryContainer() {
        if (this.clCoreModManager != null) {
            this.injectRemapper();
        }
    }
    
    private void injectRemapper() {
        try {
            MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[] { "org.spongepowered.asm.bridge.RemapperAdapterFML" });
            final Class<?> v1 = Class.forName("org.spongepowered.asm.bridge.RemapperAdapterFML", true, (ClassLoader)Launch.classLoader);
            final Method v2 = v1.getDeclaredMethod("create", (Class<?>[])new Class[0]);
            final IRemapper v3 = (IRemapper)v2.invoke(null, new Object[0]);
            MixinEnvironment.getDefaultEnvironment().getRemappers().add(v3);
        }
        catch (Exception v4) {
            MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
        }
    }
    
    @Override
    public void inject() {
        if (this.coreModWrapper != null && this.checkForCoInitialisation()) {
            MixinPlatformAgentAbstract.logger.debug("FML agent is co-initiralising coremod instance {} for {}", new Object[] { this.coreModWrapper, this.uri });
            this.coreModWrapper.injectIntoClassLoader(Launch.classLoader);
        }
    }
    
    @Override
    public String getLaunchTarget() {
        return null;
    }
    
    protected final boolean checkForCoInitialisation() {
        final boolean v1 = isTweakerQueued("FMLInjectionAndSortingTweaker");
        final boolean v2 = isTweakerQueued("TerminalTweaker");
        if ((this.initInjectionState && v2) || v1) {
            MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML will inject it normally", new Object[] { this.coreModWrapper });
            return false;
        }
        return !isTweakerQueued("FMLDeobfTweaker");
    }
    
    private static boolean isTweakerQueued(final String v1) {
        for (final String a1 : GlobalProperties.get("TweakClasses")) {
            if (a1.endsWith(v1)) {
                return true;
            }
        }
        return false;
    }
    
    private static Class<?> getCoreModManagerClass() throws ClassNotFoundException {
        try {
            return Class.forName(GlobalProperties.getString("mixin.launch.fml.coremodmanagerclass", "net.minecraftforge.fml.relauncher.CoreModManager"));
        }
        catch (ClassNotFoundException v1) {
            return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
        }
    }
    
    private static List<String> getIgnoredMods(final Class<?> v-1) throws IllegalAccessException, InvocationTargetException {
        Method v0 = null;
        try {
            v0 = v-1.getDeclaredMethod(GlobalProperties.getString("mixin.launch.fml.ignoredmodsmethod", "getIgnoredMods"), (Class<?>[])new Class[0]);
        }
        catch (NoSuchMethodException v2) {
            try {
                v0 = v-1.getDeclaredMethod("getLoadedCoremods", (Class<?>[])new Class[0]);
            }
            catch (NoSuchMethodException a1) {
                MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, (Throwable)a1);
                return Collections.emptyList();
            }
        }
        return (List<String>)v0.invoke(null, new Object[0]);
    }
    
    static {
        loadedCoreMods = new HashSet<String>();
        for (final String v1 : System.getProperty("fml.coreMods.load", "").split(",")) {
            if (!v1.isEmpty()) {
                MixinPlatformAgentAbstract.logger.debug("FML platform agent will ignore coremod {} specified on the command line", new Object[] { v1 });
                MixinPlatformAgentFML.loadedCoreMods.add(v1);
            }
        }
    }
}
