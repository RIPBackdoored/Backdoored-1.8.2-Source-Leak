package org.spongepowered.asm.mixin;

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
