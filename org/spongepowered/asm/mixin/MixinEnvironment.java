package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.perf.*;
import org.spongepowered.asm.obfuscation.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.mixin.transformer.*;
import com.google.common.collect.*;
import org.spongepowered.asm.util.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;

public final class MixinEnvironment implements ITokenProvider
{
    private static final Set<String> excludeTransformers;
    private static MixinEnvironment currentEnvironment;
    private static Phase currentPhase;
    private static CompatibilityLevel compatibility;
    private static boolean showHeader;
    private static final Logger logger;
    private static final Profiler profiler;
    private final IMixinService service;
    private final Phase phase;
    private final String configsKey;
    private final boolean[] options;
    private final Set<String> tokenProviderClasses;
    private final List<TokenProviderWrapper> tokenProviders;
    private final Map<String, Integer> internalTokens;
    private final RemapperChain remappers;
    private Side side;
    private List<ILegacyClassTransformer> transformers;
    private String obfuscationContext;
    
    MixinEnvironment(final Phase v2) {
        super();
        this.tokenProviderClasses = new HashSet<String>();
        this.tokenProviders = new ArrayList<TokenProviderWrapper>();
        this.internalTokens = new HashMap<String, Integer>();
        this.remappers = new RemapperChain();
        this.obfuscationContext = null;
        this.service = MixinService.getService();
        this.phase = v2;
        this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
        final Object v3 = this.getVersion();
        if (v3 == null || !"0.7.11".equals(v3)) {
            throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
        }
        this.service.checkEnv(this);
        this.options = new boolean[Option.values().length];
        for (final Option a1 : Option.values()) {
            this.options[a1.ordinal()] = a1.getBooleanValue();
        }
        if (MixinEnvironment.showHeader) {
            MixinEnvironment.showHeader = false;
            this.printHeader(v3);
        }
    }
    
    private void printHeader(final Object v-9) {
        final String codeSource = this.getCodeSource();
        final String name = this.service.getName();
        final Side side = this.getSide();
        MixinEnvironment.logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[] { v-9, codeSource, name, side });
        final boolean option = this.getOption(Option.DEBUG_VERBOSE);
        if (option || this.getOption(Option.DEBUG_EXPORT) || this.getOption(Option.DEBUG_PROFILER)) {
            final PrettyPrinter prettyPrinter = new PrettyPrinter(32);
            prettyPrinter.add("SpongePowered MIXIN%s", option ? " (Verbose debugging enabled)" : "").centre().hr();
            prettyPrinter.kv("Code source", (Object)codeSource);
            prettyPrinter.kv("Internal Version", v-9);
            prettyPrinter.kv("Java 8 Supported", CompatibilityLevel.JAVA_8.isSupported()).hr();
            prettyPrinter.kv("Service Name", (Object)name);
            prettyPrinter.kv("Service Class", (Object)this.service.getClass().getName()).hr();
            for (final Option v0 : Option.values()) {
                final StringBuilder v2 = new StringBuilder();
                for (int a1 = 0; a1 < v0.depth; ++a1) {
                    v2.append("- ");
                }
                prettyPrinter.kv(v0.property, "%s<%s>", v2, v0);
            }
            prettyPrinter.hr().kv("Detected Side", side);
            prettyPrinter.print(System.err);
        }
    }
    
    private String getCodeSource() {
        try {
            return this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        }
        catch (Throwable v1) {
            return "Unknown";
        }
    }
    
    public Phase getPhase() {
        return this.phase;
    }
    
    @Deprecated
    public List<String> getMixinConfigs() {
        List<String> v1 = GlobalProperties.get(this.configsKey);
        if (v1 == null) {
            v1 = new ArrayList<String>();
            GlobalProperties.put(this.configsKey, v1);
        }
        return v1;
    }
    
    @Deprecated
    public MixinEnvironment addConfiguration(final String a1) {
        MixinEnvironment.logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
        Mixins.addConfiguration(a1, this);
        return this;
    }
    
    void registerConfig(final String a1) {
        final List<String> v1 = this.getMixinConfigs();
        if (!v1.contains(a1)) {
            v1.add(a1);
        }
    }
    
    @Deprecated
    public MixinEnvironment registerErrorHandlerClass(final String a1) {
        Mixins.registerErrorHandlerClass(a1);
        return this;
    }
    
    public MixinEnvironment registerTokenProviderClass(final String v-1) {
        if (!this.tokenProviderClasses.contains(v-1)) {
            try {
                final Class<? extends IEnvironmentTokenProvider> a1 = (Class<? extends IEnvironmentTokenProvider>)this.service.getClassProvider().findClass(v-1, true);
                final IEnvironmentTokenProvider v1 = (IEnvironmentTokenProvider)a1.newInstance();
                this.registerTokenProvider(v1);
            }
            catch (Throwable v2) {
                MixinEnvironment.logger.error("Error instantiating " + v-1, v2);
            }
        }
        return this;
    }
    
    public MixinEnvironment registerTokenProvider(final IEnvironmentTokenProvider v-1) {
        if (v-1 != null && !this.tokenProviderClasses.contains(v-1.getClass().getName())) {
            final String a1 = v-1.getClass().getName();
            final TokenProviderWrapper v1 = new TokenProviderWrapper(v-1, this);
            MixinEnvironment.logger.info("Adding new token provider {} to {}", new Object[] { a1, this });
            this.tokenProviders.add(v1);
            this.tokenProviderClasses.add(a1);
            Collections.sort(this.tokenProviders);
        }
        return this;
    }
    
    @Override
    public Integer getToken(String v-1) {
        v-1 = v-1.toUpperCase();
        for (final TokenProviderWrapper v1 : this.tokenProviders) {
            final Integer a1 = v1.getToken(v-1);
            if (a1 != null) {
                return a1;
            }
        }
        return this.internalTokens.get(v-1);
    }
    
    @Deprecated
    public Set<String> getErrorHandlerClasses() {
        return Mixins.getErrorHandlerClasses();
    }
    
    public Object getActiveTransformer() {
        return GlobalProperties.get("mixin.transformer");
    }
    
    public void setActiveTransformer(final ITransformer a1) {
        if (a1 != null) {
            GlobalProperties.put("mixin.transformer", a1);
        }
    }
    
    public MixinEnvironment setSide(final Side a1) {
        if (a1 != null && this.getSide() == Side.UNKNOWN && a1 != Side.UNKNOWN) {
            this.side = a1;
        }
        return this;
    }
    
    public Side getSide() {
        if (this.side == null) {
            for (final Side v1 : Side.values()) {
                if (v1.detect()) {
                    this.side = v1;
                    break;
                }
            }
        }
        return (this.side != null) ? this.side : Side.UNKNOWN;
    }
    
    public String getVersion() {
        return GlobalProperties.get("mixin.initialised");
    }
    
    public boolean getOption(final Option a1) {
        return this.options[a1.ordinal()];
    }
    
    public void setOption(final Option a1, final boolean a2) {
        this.options[a1.ordinal()] = a2;
    }
    
    public String getOptionValue(final Option a1) {
        return a1.getStringValue();
    }
    
    public <E extends Enum<E>> E getOption(final Option a1, final E a2) {
        return a1.getEnumValue(a2);
    }
    
    public void setObfuscationContext(final String a1) {
        this.obfuscationContext = a1;
    }
    
    public String getObfuscationContext() {
        return this.obfuscationContext;
    }
    
    public String getRefmapObfuscationContext() {
        final String v1 = Option.OBFUSCATION_TYPE.getStringValue();
        if (v1 != null) {
            return v1;
        }
        return this.obfuscationContext;
    }
    
    public RemapperChain getRemappers() {
        return this.remappers;
    }
    
    public void audit() {
        final Object v0 = this.getActiveTransformer();
        if (v0 instanceof MixinTransformer) {
            final MixinTransformer v2 = (MixinTransformer)v0;
            v2.audit(this);
        }
    }
    
    public List<ILegacyClassTransformer> getTransformers() {
        if (this.transformers == null) {
            this.buildTransformerDelegationList();
        }
        return Collections.unmodifiableList((List<? extends ILegacyClassTransformer>)this.transformers);
    }
    
    public void addTransformerExclusion(final String a1) {
        MixinEnvironment.excludeTransformers.add(a1);
        this.transformers = null;
    }
    
    private void buildTransformerDelegationList() {
        MixinEnvironment.logger.debug("Rebuilding transformer delegation list:");
        this.transformers = new ArrayList<ILegacyClassTransformer>();
        for (final ITransformer transformer : this.service.getTransformers()) {
            if (!(transformer instanceof ILegacyClassTransformer)) {
                continue;
            }
            final ILegacyClassTransformer legacyClassTransformer = (ILegacyClassTransformer)transformer;
            final String name = legacyClassTransformer.getName();
            boolean b = true;
            for (final String v1 : MixinEnvironment.excludeTransformers) {
                if (name.contains(v1)) {
                    b = false;
                    break;
                }
            }
            if (b && !legacyClassTransformer.isDelegationExcluded()) {
                MixinEnvironment.logger.debug("  Adding:    {}", new Object[] { name });
                this.transformers.add(legacyClassTransformer);
            }
            else {
                MixinEnvironment.logger.debug("  Excluding: {}", new Object[] { name });
            }
        }
        MixinEnvironment.logger.debug("Transformer delegation list created with {} entries", new Object[] { this.transformers.size() });
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]", this.getClass().getSimpleName(), this.phase);
    }
    
    private static Phase getCurrentPhase() {
        if (MixinEnvironment.currentPhase == Phase.NOT_INITIALISED) {
            init(Phase.PREINIT);
        }
        return MixinEnvironment.currentPhase;
    }
    
    public static void init(final Phase v1) {
        if (MixinEnvironment.currentPhase == Phase.NOT_INITIALISED) {
            MixinEnvironment.currentPhase = v1;
            final MixinEnvironment a1 = getEnvironment(v1);
            getProfiler().setActive(a1.getOption(Option.DEBUG_PROFILER));
            MixinLogWatcher.begin();
        }
    }
    
    public static MixinEnvironment getEnvironment(final Phase a1) {
        if (a1 == null) {
            return Phase.DEFAULT.getEnvironment();
        }
        return a1.getEnvironment();
    }
    
    public static MixinEnvironment getDefaultEnvironment() {
        return getEnvironment(Phase.DEFAULT);
    }
    
    public static MixinEnvironment getCurrentEnvironment() {
        if (MixinEnvironment.currentEnvironment == null) {
            MixinEnvironment.currentEnvironment = getEnvironment(getCurrentPhase());
        }
        return MixinEnvironment.currentEnvironment;
    }
    
    public static CompatibilityLevel getCompatibilityLevel() {
        return MixinEnvironment.compatibility;
    }
    
    @Deprecated
    public static void setCompatibilityLevel(final CompatibilityLevel a1) throws IllegalArgumentException {
        final StackTraceElement[] v1 = Thread.currentThread().getStackTrace();
        if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(v1[2].getClassName())) {
            MixinEnvironment.logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
        }
        if (a1 != MixinEnvironment.compatibility && a1.isAtLeast(MixinEnvironment.compatibility)) {
            if (!a1.isSupported()) {
                throw new IllegalArgumentException("The requested compatibility level " + a1 + " could not be set. Level is not supported");
            }
            MixinEnvironment.compatibility = a1;
            MixinEnvironment.logger.info("Compatibility level set to {}", new Object[] { a1 });
        }
    }
    
    public static Profiler getProfiler() {
        return MixinEnvironment.profiler;
    }
    
    static void gotoPhase(final Phase a1) {
        if (a1 == null || a1.ordinal < 0) {
            throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
        }
        if (a1.ordinal > getCurrentPhase().ordinal) {
            MixinService.getService().beginPhase();
        }
        if (a1 == Phase.DEFAULT) {
            MixinLogWatcher.end();
        }
        MixinEnvironment.currentPhase = a1;
        MixinEnvironment.currentEnvironment = getEnvironment(getCurrentPhase());
    }
    
    static {
        excludeTransformers = Sets.newHashSet((Object[])new String[] { "net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer" });
        MixinEnvironment.currentPhase = Phase.NOT_INITIALISED;
        MixinEnvironment.compatibility = Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(CompatibilityLevel.JAVA_6);
        MixinEnvironment.showHeader = true;
        logger = LogManager.getLogger("mixin");
        profiler = new Profiler();
    }
    
    public static final class Phase
    {
        static final Phase NOT_INITIALISED;
        public static final Phase PREINIT;
        public static final Phase INIT;
        public static final Phase DEFAULT;
        static final List<Phase> phases;
        final int ordinal;
        final String name;
        private MixinEnvironment environment;
        
        private Phase(final int a1, final String a2) {
            super();
            this.ordinal = a1;
            this.name = a2;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static Phase forName(final String v1) {
            for (final Phase a1 : Phase.phases) {
                if (a1.name.equals(v1)) {
                    return a1;
                }
            }
            return null;
        }
        
        MixinEnvironment getEnvironment() {
            if (this.ordinal < 0) {
                throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
            }
            if (this.environment == null) {
                this.environment = new MixinEnvironment(this);
            }
            return this.environment;
        }
        
        static {
            NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
            PREINIT = new Phase(0, "PREINIT");
            INIT = new Phase(1, "INIT");
            DEFAULT = new Phase(2, "DEFAULT");
            phases = ImmutableList.of((Object)Phase.PREINIT, (Object)Phase.INIT, (Object)Phase.DEFAULT);
        }
    }
    
    public enum Side
    {
        UNKNOWN {
            MixinEnvironment$Side$1(final String a1, final int a2) {
            }
            
            @Override
            protected boolean detect() {
                return false;
            }
        }, 
        CLIENT {
            MixinEnvironment$Side$2(final String a1, final int a2) {
            }
            
            @Override
            protected boolean detect() {
                final String v1 = MixinService.getService().getSideName();
                return "CLIENT".equals(v1);
            }
        }, 
        SERVER {
            MixinEnvironment$Side$3(final String a1, final int a2) {
            }
            
            @Override
            protected boolean detect() {
                final String v1 = MixinService.getService().getSideName();
                return "SERVER".equals(v1) || "DEDICATEDSERVER".equals(v1);
            }
        };
        
        private static final /* synthetic */ Side[] $VALUES;
        
        public static Side[] values() {
            return Side.$VALUES.clone();
        }
        
        public static Side valueOf(final String a1) {
            return Enum.valueOf(Side.class, a1);
        }
        
        protected abstract boolean detect();
        
        Side(final String a1, final int a2, final MixinEnvironment$1 a3) {
            this();
        }
        
        static {
            $VALUES = new Side[] { Side.UNKNOWN, Side.CLIENT, Side.SERVER };
        }
    }
    
    public enum Option
    {
        DEBUG_ALL("debug"), 
        DEBUG_EXPORT(Option.DEBUG_ALL, "export"), 
        DEBUG_EXPORT_FILTER(Option.DEBUG_EXPORT, "filter", false), 
        DEBUG_EXPORT_DECOMPILE(Option.DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, "decompile"), 
        DEBUG_EXPORT_DECOMPILE_THREADED(Option.DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "async"), 
        DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(Option.DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"), 
        DEBUG_VERIFY(Option.DEBUG_ALL, "verify"), 
        DEBUG_VERBOSE(Option.DEBUG_ALL, "verbose"), 
        DEBUG_INJECTORS(Option.DEBUG_ALL, "countInjections"), 
        DEBUG_STRICT(Option.DEBUG_ALL, Inherit.INDEPENDENT, "strict"), 
        DEBUG_UNIQUE(Option.DEBUG_STRICT, "unique"), 
        DEBUG_TARGETS(Option.DEBUG_STRICT, "targets"), 
        DEBUG_PROFILER(Option.DEBUG_ALL, Inherit.ALLOW_OVERRIDE, "profiler"), 
        DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"), 
        CHECK_ALL("checks"), 
        CHECK_IMPLEMENTS(Option.CHECK_ALL, "interfaces"), 
        CHECK_IMPLEMENTS_STRICT(Option.CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, "strict"), 
        IGNORE_CONSTRAINTS("ignoreConstraints"), 
        HOT_SWAP("hotSwap"), 
        ENVIRONMENT(Inherit.ALWAYS_FALSE, "env"), 
        OBFUSCATION_TYPE(Option.ENVIRONMENT, Inherit.ALWAYS_FALSE, "obf"), 
        DISABLE_REFMAP(Option.ENVIRONMENT, Inherit.INDEPENDENT, "disableRefMap"), 
        REFMAP_REMAP(Option.ENVIRONMENT, Inherit.INDEPENDENT, "remapRefMap"), 
        REFMAP_REMAP_RESOURCE(Option.ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingFile", ""), 
        REFMAP_REMAP_SOURCE_ENV(Option.ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"), 
        REFMAP_REMAP_ALLOW_PERMISSIVE(Option.ENVIRONMENT, Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"), 
        IGNORE_REQUIRED(Option.ENVIRONMENT, Inherit.INDEPENDENT, "ignoreRequired"), 
        DEFAULT_COMPATIBILITY_LEVEL(Option.ENVIRONMENT, Inherit.INDEPENDENT, "compatLevel"), 
        SHIFT_BY_VIOLATION_BEHAVIOUR(Option.ENVIRONMENT, Inherit.INDEPENDENT, "shiftByViolation", "warn"), 
        INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
        
        private static final String PREFIX = "mixin";
        final Option parent;
        final Inherit inheritance;
        final String property;
        final String defaultValue;
        final boolean isFlag;
        final int depth;
        private static final /* synthetic */ Option[] $VALUES;
        
        public static Option[] values() {
            return Option.$VALUES.clone();
        }
        
        public static Option valueOf(final String a1) {
            return Enum.valueOf(Option.class, a1);
        }
        
        private Option(final String a1) {
            this(null, a1, true);
        }
        
        private Option(final Inherit a1, final String a2) {
            this(null, a1, a2, true);
        }
        
        private Option(final String a1, final boolean a2) {
            this(null, a1, a2);
        }
        
        private Option(final String a1, final String a2) {
            this(null, Inherit.INDEPENDENT, a1, false, a2);
        }
        
        private Option(final Option a1, final String a2) {
            this(a1, Inherit.INHERIT, a2, true);
        }
        
        private Option(final Option a1, final Inherit a2, final String a3) {
            this(a1, a2, a3, true);
        }
        
        private Option(final Option a1, final String a2, final boolean a3) {
            this(a1, Inherit.INHERIT, a2, a3, null);
        }
        
        private Option(final Option a1, final Inherit a2, final String a3, final boolean a4) {
            this(a1, a2, a3, a4, null);
        }
        
        private Option(final Option a1, final String a2, final String a3) {
            this(a1, Inherit.INHERIT, a2, false, a3);
        }
        
        private Option(final Option a1, final Inherit a2, final String a3, final String a4) {
            this(a1, a2, a3, false, a4);
        }
        
        private Option(Option a1, final Inherit a2, final String a3, final boolean a4, final String a5) {
            this.parent = a1;
            this.inheritance = a2;
            this.property = ((a1 != null) ? a1.property : "mixin") + "." + a3;
            this.defaultValue = a5;
            this.isFlag = a4;
            int a6;
            for (a6 = 0; a1 != null; a1 = a1.parent, ++a6) {}
            this.depth = a6;
        }
        
        Option getParent() {
            return this.parent;
        }
        
        String getProperty() {
            return this.property;
        }
        
        @Override
        public String toString() {
            return this.isFlag ? String.valueOf(this.getBooleanValue()) : this.getStringValue();
        }
        
        private boolean getLocalBooleanValue(final boolean a1) {
            return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(a1)));
        }
        
        private boolean getInheritedBooleanValue() {
            return this.parent != null && this.parent.getBooleanValue();
        }
        
        final boolean getBooleanValue() {
            if (this.inheritance == Inherit.ALWAYS_FALSE) {
                return false;
            }
            final boolean v1 = this.getLocalBooleanValue(false);
            if (this.inheritance == Inherit.INDEPENDENT) {
                return v1;
            }
            final boolean v2 = v1 || this.getInheritedBooleanValue();
            return (this.inheritance == Inherit.INHERIT) ? v2 : this.getLocalBooleanValue(v2);
        }
        
        final String getStringValue() {
            return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
        }
        
         <E extends Enum<E>> E getEnumValue(final E v2) {
            final String v3 = System.getProperty(this.property, v2.name());
            try {
                return Enum.valueOf(v2.getClass(), v3.toUpperCase());
            }
            catch (IllegalArgumentException a1) {
                return v2;
            }
        }
        
        static {
            $VALUES = new Option[] { Option.DEBUG_ALL, Option.DEBUG_EXPORT, Option.DEBUG_EXPORT_FILTER, Option.DEBUG_EXPORT_DECOMPILE, Option.DEBUG_EXPORT_DECOMPILE_THREADED, Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES, Option.DEBUG_VERIFY, Option.DEBUG_VERBOSE, Option.DEBUG_INJECTORS, Option.DEBUG_STRICT, Option.DEBUG_UNIQUE, Option.DEBUG_TARGETS, Option.DEBUG_PROFILER, Option.DUMP_TARGET_ON_FAILURE, Option.CHECK_ALL, Option.CHECK_IMPLEMENTS, Option.CHECK_IMPLEMENTS_STRICT, Option.IGNORE_CONSTRAINTS, Option.HOT_SWAP, Option.ENVIRONMENT, Option.OBFUSCATION_TYPE, Option.DISABLE_REFMAP, Option.REFMAP_REMAP, Option.REFMAP_REMAP_RESOURCE, Option.REFMAP_REMAP_SOURCE_ENV, Option.REFMAP_REMAP_ALLOW_PERMISSIVE, Option.IGNORE_REQUIRED, Option.DEFAULT_COMPATIBILITY_LEVEL, Option.SHIFT_BY_VIOLATION_BEHAVIOUR, Option.INITIALISER_INJECTION_MODE };
        }
        
        private enum Inherit
        {
            INHERIT, 
            ALLOW_OVERRIDE, 
            INDEPENDENT, 
            ALWAYS_FALSE;
            
            private static final /* synthetic */ Inherit[] $VALUES;
            
            public static Inherit[] values() {
                return Inherit.$VALUES.clone();
            }
            
            public static Inherit valueOf(final String a1) {
                return Enum.valueOf(Inherit.class, a1);
            }
            
            static {
                $VALUES = new Inherit[] { Inherit.INHERIT, Inherit.ALLOW_OVERRIDE, Inherit.INDEPENDENT, Inherit.ALWAYS_FALSE };
            }
        }
    }
    
    public enum CompatibilityLevel
    {
        JAVA_6(6, 50, false), 
        JAVA_7(7, 51, false) {
            MixinEnvironment$CompatibilityLevel$1(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
            }
            
            @Override
            boolean isSupported() {
                return JavaVersion.current() >= 1.7;
            }
        }, 
        JAVA_8(8, 52, true) {
            MixinEnvironment$CompatibilityLevel$2(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
            }
            
            @Override
            boolean isSupported() {
                return JavaVersion.current() >= 1.8;
            }
        }, 
        JAVA_9(9, 53, true) {
            MixinEnvironment$CompatibilityLevel$3(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
            }
            
            @Override
            boolean isSupported() {
                return false;
            }
        };
        
        private static final int CLASS_V1_9 = 53;
        private final int ver;
        private final int classVersion;
        private final boolean supportsMethodsInInterfaces;
        private CompatibilityLevel maxCompatibleLevel;
        private static final /* synthetic */ CompatibilityLevel[] $VALUES;
        
        public static CompatibilityLevel[] values() {
            return CompatibilityLevel.$VALUES.clone();
        }
        
        public static CompatibilityLevel valueOf(final String a1) {
            return Enum.valueOf(CompatibilityLevel.class, a1);
        }
        
        private CompatibilityLevel(final int a1, final int a2, final boolean a3) {
            this.ver = a1;
            this.classVersion = a2;
            this.supportsMethodsInInterfaces = a3;
        }
        
        private void setMaxCompatibleLevel(final CompatibilityLevel a1) {
            this.maxCompatibleLevel = a1;
        }
        
        boolean isSupported() {
            return true;
        }
        
        public int classVersion() {
            return this.classVersion;
        }
        
        public boolean supportsMethodsInInterfaces() {
            return this.supportsMethodsInInterfaces;
        }
        
        public boolean isAtLeast(final CompatibilityLevel a1) {
            return a1 == null || this.ver >= a1.ver;
        }
        
        public boolean canElevateTo(final CompatibilityLevel a1) {
            return a1 == null || this.maxCompatibleLevel == null || a1.ver <= this.maxCompatibleLevel.ver;
        }
        
        public boolean canSupport(final CompatibilityLevel a1) {
            return a1 == null || a1.canElevateTo(this);
        }
        
        CompatibilityLevel(final String a1, final int a2, final int a3, final int a4, final boolean a5, final MixinEnvironment$1 a6) {
            this(a3, a4, a5);
        }
        
        static {
            $VALUES = new CompatibilityLevel[] { CompatibilityLevel.JAVA_6, CompatibilityLevel.JAVA_7, CompatibilityLevel.JAVA_8, CompatibilityLevel.JAVA_9 };
        }
    }
    
    static class TokenProviderWrapper implements Comparable<TokenProviderWrapper>
    {
        private static int nextOrder;
        private final int priority;
        private final int order;
        private final IEnvironmentTokenProvider provider;
        private final MixinEnvironment environment;
        
        public TokenProviderWrapper(final IEnvironmentTokenProvider a1, final MixinEnvironment a2) {
            super();
            this.provider = a1;
            this.environment = a2;
            this.order = TokenProviderWrapper.nextOrder++;
            this.priority = a1.getPriority();
        }
        
        @Override
        public int compareTo(final TokenProviderWrapper a1) {
            if (a1 == null) {
                return 0;
            }
            if (a1.priority == this.priority) {
                return a1.order - this.order;
            }
            return a1.priority - this.priority;
        }
        
        public IEnvironmentTokenProvider getProvider() {
            return this.provider;
        }
        
        Integer getToken(final String a1) {
            return this.provider.getToken(a1, this.environment);
        }
        
        @Override
        public /* bridge */ int compareTo(final Object o) {
            return this.compareTo((TokenProviderWrapper)o);
        }
        
        static {
            TokenProviderWrapper.nextOrder = 0;
        }
    }
    
    static class MixinLogWatcher
    {
        static MixinAppender appender;
        static org.apache.logging.log4j.core.Logger log;
        static Level oldLevel;
        
        MixinLogWatcher() {
            super();
        }
        
        static void begin() {
            final Logger v1 = LogManager.getLogger("FML");
            if (!(v1 instanceof org.apache.logging.log4j.core.Logger)) {
                return;
            }
            MixinLogWatcher.log = (org.apache.logging.log4j.core.Logger)v1;
            MixinLogWatcher.oldLevel = MixinLogWatcher.log.getLevel();
            MixinLogWatcher.appender.start();
            MixinLogWatcher.log.addAppender((Appender)MixinLogWatcher.appender);
            MixinLogWatcher.log.setLevel(Level.ALL);
        }
        
        static void end() {
            if (MixinLogWatcher.log != null) {
                MixinLogWatcher.log.removeAppender((Appender)MixinLogWatcher.appender);
            }
        }
        
        static {
            MixinLogWatcher.appender = new MixinAppender();
            MixinLogWatcher.oldLevel = null;
        }
        
        static class MixinAppender extends AbstractAppender
        {
            MixinAppender() {
                super("MixinLogWatcherAppender", (Filter)null, (Layout)null);
            }
            
            public void append(final LogEvent a1) {
                if (a1.getLevel() != Level.DEBUG || !"Validating minecraft".equals(a1.getMessage().getFormattedMessage())) {
                    return;
                }
                MixinEnvironment.gotoPhase(Phase.INIT);
                if (MixinLogWatcher.log.getLevel() == Level.ALL) {
                    MixinLogWatcher.log.setLevel(MixinLogWatcher.oldLevel);
                }
            }
        }
    }
}
