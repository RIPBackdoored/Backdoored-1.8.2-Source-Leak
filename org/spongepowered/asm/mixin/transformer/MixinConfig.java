package org.spongepowered.asm.mixin.transformer;

import com.google.gson.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.launch.*;
import java.util.regex.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.service.*;
import com.google.gson.*;
import java.io.*;

final class MixinConfig implements Comparable<MixinConfig>, IMixinConfig
{
    private static int configOrder;
    private static final Set<String> globalMixinList;
    private final Logger logger;
    private final transient Map<String, List<MixinInfo>> mixinMapping;
    private final transient Set<String> unhandledTargets;
    private final transient List<MixinInfo> mixins;
    private transient Config handle;
    @SerializedName("target")
    private String selector;
    @SerializedName("minVersion")
    private String version;
    @SerializedName("compatibilityLevel")
    private String compatibility;
    @SerializedName("required")
    private boolean required;
    @SerializedName("priority")
    private int priority;
    @SerializedName("mixinPriority")
    private int mixinPriority;
    @SerializedName("package")
    private String mixinPackage;
    @SerializedName("mixins")
    private List<String> mixinClasses;
    @SerializedName("client")
    private List<String> mixinClassesClient;
    @SerializedName("server")
    private List<String> mixinClassesServer;
    @SerializedName("setSourceFile")
    private boolean setSourceFile;
    @SerializedName("refmap")
    private String refMapperConfig;
    @SerializedName("verbose")
    private boolean verboseLogging;
    private final transient int order;
    private final transient List<IListener> listeners;
    private transient IMixinService service;
    private transient MixinEnvironment env;
    private transient String name;
    @SerializedName("plugin")
    private String pluginClassName;
    @SerializedName("injectors")
    private InjectorOptions injectorOptions;
    @SerializedName("overwrites")
    private OverwriteOptions overwriteOptions;
    private transient IMixinConfigPlugin plugin;
    private transient IReferenceMapper refMapper;
    private transient boolean prepared;
    private transient boolean visited;
    
    private MixinConfig() {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.mixinMapping = new HashMap<String, List<MixinInfo>>();
        this.unhandledTargets = new HashSet<String>();
        this.mixins = new ArrayList<MixinInfo>();
        this.priority = 1000;
        this.mixinPriority = 1000;
        this.setSourceFile = false;
        this.order = MixinConfig.configOrder++;
        this.listeners = new ArrayList<IListener>();
        this.injectorOptions = new InjectorOptions();
        this.overwriteOptions = new OverwriteOptions();
        this.prepared = false;
        this.visited = false;
    }
    
    private boolean onLoad(final IMixinService a1, final String a2, final MixinEnvironment a3) {
        this.service = a1;
        this.name = a2;
        this.env = this.parseSelector(this.selector, a3);
        this.required &= !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED);
        this.initCompatibilityLevel();
        this.initInjectionPoints();
        return this.checkVersion();
    }
    
    private void initCompatibilityLevel() {
        if (this.compatibility == null) {
            return;
        }
        final MixinEnvironment.CompatibilityLevel v1 = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase());
        final MixinEnvironment.CompatibilityLevel v2 = MixinEnvironment.getCompatibilityLevel();
        if (v1 == v2) {
            return;
        }
        if (v2.isAtLeast(v1) && !v2.canSupport(v1)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + v1 + " which is too old");
        }
        if (!v2.canElevateTo(v1)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + v1 + " which is prohibited by " + v2);
        }
        MixinEnvironment.setCompatibilityLevel(v1);
    }
    
    private MixinEnvironment parseSelector(final String v-5, final MixinEnvironment v-4) {
        if (v-5 != null) {
            final String[] split;
            final String[] array = split = v-5.split("[&\\| ]");
            for (String v1 : split) {
                v1 = v1.trim();
                final Pattern a1 = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
                final Matcher a2 = a1.matcher(v1);
                if (a2.matches()) {
                    return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(a2.group(1)));
                }
            }
            final MixinEnvironment.Phase forName = MixinEnvironment.Phase.forName(v-5);
            if (forName != null) {
                return MixinEnvironment.getEnvironment(forName);
            }
        }
        return v-4;
    }
    
    private void initInjectionPoints() {
        if (this.injectorOptions.injectionPoints == null) {
            return;
        }
        for (final String v0 : this.injectorOptions.injectionPoints) {
            try {
                final Class<?> v2 = this.service.getClassProvider().findClass(v0, true);
                if (InjectionPoint.class.isAssignableFrom(v2)) {
                    InjectionPoint.register((Class<? extends InjectionPoint>)v2);
                }
                else {
                    this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[] { v2, this });
                }
            }
            catch (Throwable v3) {
                this.logger.catching(v3);
            }
        }
    }
    
    private boolean checkVersion() throws MixinInitialisationError {
        if (this.version == null) {
            this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[] { this.name });
        }
        final VersionNumber v1 = VersionNumber.parse(this.version);
        final VersionNumber v2 = VersionNumber.parse(this.env.getVersion());
        if (v1.compareTo(v2) <= 0) {
            return true;
        }
        this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[] { this.name, v1, v2 });
        if (this.required) {
            throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + v1);
        }
        return false;
    }
    
    void addListener(final IListener a1) {
        this.listeners.add(a1);
    }
    
    void onSelect() {
        if (this.pluginClassName != null) {
            try {
                final Class<?> v1 = this.service.getClassProvider().findClass(this.pluginClassName, true);
                this.plugin = (IMixinConfigPlugin)v1.newInstance();
                if (this.plugin != null) {
                    this.plugin.onLoad(this.mixinPackage);
                }
            }
            catch (Throwable v2) {
                v2.printStackTrace();
                this.plugin = null;
            }
        }
        if (!this.mixinPackage.endsWith(".")) {
            this.mixinPackage += ".";
        }
        boolean v3 = false;
        if (this.refMapperConfig == null) {
            if (this.plugin != null) {
                this.refMapperConfig = this.plugin.getRefMapperConfig();
            }
            if (this.refMapperConfig == null) {
                v3 = true;
                this.refMapperConfig = "mixin.refmap.json";
            }
        }
        this.refMapper = ReferenceMapper.read(this.refMapperConfig);
        this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        if (!v3 && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
            this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[] { this.refMapperConfig, this });
        }
        if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
            this.refMapper = RemappingReferenceMapper.of(this.env, this.refMapper);
        }
    }
    
    void prepare() {
        if (this.prepared) {
            return;
        }
        this.prepared = true;
        this.prepareMixins(this.mixinClasses, false);
        switch (this.env.getSide()) {
            case CLIENT: {
                this.prepareMixins(this.mixinClassesClient, false);
                break;
            }
            case SERVER: {
                this.prepareMixins(this.mixinClassesServer, false);
                break;
            }
            default: {
                this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
                break;
            }
        }
    }
    
    void postInitialise() {
        if (this.plugin != null) {
            final List<String> v1 = this.plugin.getMixins();
            this.prepareMixins(v1, true);
        }
        final Iterator<MixinInfo> v2 = this.mixins.iterator();
        while (v2.hasNext()) {
            final MixinInfo v3 = v2.next();
            try {
                v3.validate();
                for (final IListener v4 : this.listeners) {
                    v4.onInit(v3);
                }
            }
            catch (InvalidMixinException v5) {
                this.logger.error(v5.getMixin() + ": " + v5.getMessage(), (Throwable)v5);
                this.removeMixin(v3);
                v2.remove();
            }
            catch (Exception v6) {
                this.logger.error(v6.getMessage(), (Throwable)v6);
                this.removeMixin(v3);
                v2.remove();
            }
        }
    }
    
    private void removeMixin(final MixinInfo v-1) {
        for (final List<MixinInfo> v1 : this.mixinMapping.values()) {
            final Iterator<MixinInfo> a1 = v1.iterator();
            while (a1.hasNext()) {
                if (v-1 == a1.next()) {
                    a1.remove();
                }
            }
        }
    }
    
    private void prepareMixins(final List<String> v-6, final boolean v-5) {
        if (v-6 == null) {
            return;
        }
        for (final String v4 : v-6) {
            final String string = this.mixinPackage + v4;
            if (v4 != null) {
                if (MixinConfig.globalMixinList.contains(string)) {
                    continue;
                }
                MixinInfo a4 = null;
                try {
                    a4 = new MixinInfo(this.service, this, v4, true, this.plugin, v-5);
                    if (a4.getTargetClasses().size() <= 0) {
                        continue;
                    }
                    MixinConfig.globalMixinList.add(string);
                    for (final String a2 : a4.getTargetClasses()) {
                        final String a3 = a2.replace('/', '.');
                        this.mixinsFor(a3).add(a4);
                        this.unhandledTargets.add(a3);
                    }
                    for (final IListener v1 : this.listeners) {
                        v1.onPrepare(a4);
                    }
                    this.mixins.add(a4);
                }
                catch (InvalidMixinException v2) {
                    if (this.required) {
                        throw v2;
                    }
                    this.logger.error(v2.getMessage(), (Throwable)v2);
                }
                catch (Exception v3) {
                    if (this.required) {
                        throw new InvalidMixinException(a4, "Error initialising mixin " + a4 + " - " + v3.getClass() + ": " + v3.getMessage(), v3);
                    }
                    this.logger.error(v3.getMessage(), (Throwable)v3);
                }
            }
        }
    }
    
    void postApply(final String a1, final ClassNode a2) {
        this.unhandledTargets.remove(a1);
    }
    
    public Config getHandle() {
        if (this.handle == null) {
            this.handle = new Config(this);
        }
        return this.handle;
    }
    
    @Override
    public boolean isRequired() {
        return this.required;
    }
    
    @Override
    public MixinEnvironment getEnvironment() {
        return this.env;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getMixinPackage() {
        return this.mixinPackage;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    public int getDefaultMixinPriority() {
        return this.mixinPriority;
    }
    
    public int getDefaultRequiredInjections() {
        return this.injectorOptions.defaultRequireValue;
    }
    
    public String getDefaultInjectorGroup() {
        final String v1 = this.injectorOptions.defaultGroup;
        return (v1 != null && !v1.isEmpty()) ? v1 : "default";
    }
    
    public boolean conformOverwriteVisibility() {
        return this.overwriteOptions.conformAccessModifiers;
    }
    
    public boolean requireOverwriteAnnotations() {
        return this.overwriteOptions.requireOverwriteAnnotations;
    }
    
    public int getMaxShiftByValue() {
        return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
    }
    
    public boolean select(final MixinEnvironment a1) {
        this.visited = true;
        return this.env == a1;
    }
    
    boolean isVisited() {
        return this.visited;
    }
    
    int getDeclaredMixinCount() {
        return getCollectionSize(this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer);
    }
    
    int getMixinCount() {
        return this.mixins.size();
    }
    
    public List<String> getClasses() {
        return Collections.unmodifiableList((List<? extends String>)this.mixinClasses);
    }
    
    public boolean shouldSetSourceFile() {
        return this.setSourceFile;
    }
    
    public IReferenceMapper getReferenceMapper() {
        if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
            return ReferenceMapper.DEFAULT_MAPPER;
        }
        this.refMapper.setContext(this.env.getRefmapObfuscationContext());
        return this.refMapper;
    }
    
    String remapClassName(final String a1, final String a2) {
        return this.getReferenceMapper().remap(a1, a2);
    }
    
    @Override
    public IMixinConfigPlugin getPlugin() {
        return this.plugin;
    }
    
    @Override
    public Set<String> getTargets() {
        return Collections.unmodifiableSet((Set<? extends String>)this.mixinMapping.keySet());
    }
    
    public Set<String> getUnhandledTargets() {
        return Collections.unmodifiableSet((Set<? extends String>)this.unhandledTargets);
    }
    
    public Level getLoggingLevel() {
        return this.verboseLogging ? Level.INFO : Level.DEBUG;
    }
    
    public boolean packageMatch(final String a1) {
        return a1.startsWith(this.mixinPackage);
    }
    
    public boolean hasMixinsFor(final String a1) {
        return this.mixinMapping.containsKey(a1);
    }
    
    public List<MixinInfo> getMixinsFor(final String a1) {
        return this.mixinsFor(a1);
    }
    
    private List<MixinInfo> mixinsFor(final String a1) {
        List<MixinInfo> v1 = this.mixinMapping.get(a1);
        if (v1 == null) {
            v1 = new ArrayList<MixinInfo>();
            this.mixinMapping.put(a1, v1);
        }
        return v1;
    }
    
    public List<String> reloadMixin(final String v2, final byte[] v3) {
        for (final MixinInfo a3 : this.mixins) {
            if (a3.getClassName().equals(v2)) {
                a3.reloadMixin(v3);
                return a3.getTargetClasses();
            }
        }
        return Collections.emptyList();
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public int compareTo(final MixinConfig a1) {
        if (a1 == null) {
            return 0;
        }
        if (a1.priority == this.priority) {
            return this.order - a1.order;
        }
        return this.priority - a1.priority;
    }
    
    static Config create(final String v-1, final MixinEnvironment v0) {
        try {
            final IMixinService a1 = MixinService.getService();
            final MixinConfig a2 = new Gson().fromJson(new InputStreamReader(a1.getResourceAsStream(v-1)), MixinConfig.class);
            if (a2.onLoad(a1, v-1, v0)) {
                return a2.getHandle();
            }
            return null;
        }
        catch (Exception v) {
            v.printStackTrace();
            throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", v-1), v);
        }
    }
    
    private static int getCollectionSize(final Collection<?>... v1) {
        int v2 = 0;
        for (final Collection<?> a1 : v1) {
            if (a1 != null) {
                v2 += a1.size();
            }
        }
        return v2;
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((MixinConfig)o);
    }
    
    static {
        MixinConfig.configOrder = 0;
        globalMixinList = new HashSet<String>();
    }
    
    static class InjectorOptions
    {
        @SerializedName("defaultRequire")
        int defaultRequireValue;
        @SerializedName("defaultGroup")
        String defaultGroup;
        @SerializedName("injectionPoints")
        List<String> injectionPoints;
        @SerializedName("maxShiftBy")
        int maxShiftBy;
        
        InjectorOptions() {
            super();
            this.defaultRequireValue = 0;
            this.defaultGroup = "default";
            this.maxShiftBy = 0;
        }
    }
    
    static class OverwriteOptions
    {
        @SerializedName("conformVisibility")
        boolean conformAccessModifiers;
        @SerializedName("requireAnnotations")
        boolean requireOverwriteAnnotations;
        
        OverwriteOptions() {
            super();
        }
    }
    
    interface IListener
    {
        void onPrepare(final MixinInfo p0);
        
        void onInit(final MixinInfo p0);
    }
}
