package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.util.perf.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import org.spongepowered.asm.mixin.transformer.ext.extensions.*;
import org.apache.logging.log4j.*;
import java.text.*;
import org.spongepowered.asm.util.*;
import java.lang.reflect.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

public class MixinTransformer extends TreeTransformer
{
    private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
    private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
    static final Logger logger;
    private final IMixinService service;
    private final List<MixinConfig> configs;
    private final List<MixinConfig> pendingConfigs;
    private final ReEntranceLock lock;
    private final String sessionId;
    private final Extensions extensions;
    private final IHotSwap hotSwapper;
    private final MixinPostProcessor postProcessor;
    private final Profiler profiler;
    private MixinEnvironment currentEnvironment;
    private Level verboseLoggingLevel;
    private boolean errorState;
    private int transformedCount;
    
    MixinTransformer() {
        super();
        this.service = MixinService.getService();
        this.configs = new ArrayList<MixinConfig>();
        this.pendingConfigs = new ArrayList<MixinConfig>();
        this.sessionId = UUID.randomUUID().toString();
        this.verboseLoggingLevel = Level.DEBUG;
        this.errorState = false;
        this.transformedCount = 0;
        final MixinEnvironment v1 = MixinEnvironment.getCurrentEnvironment();
        final Object v2 = v1.getActiveTransformer();
        if (v2 instanceof ITransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
        }
        v1.setActiveTransformer(this);
        this.lock = this.service.getReEntranceLock();
        this.extensions = new Extensions(this);
        this.hotSwapper = this.initHotSwapper(v1);
        this.postProcessor = new MixinPostProcessor();
        this.extensions.add(new ArgsClassGenerator());
        this.extensions.add(new InnerClassGenerator());
        this.extensions.add(new ExtensionClassExporter(v1));
        this.extensions.add(new ExtensionCheckClass());
        this.extensions.add(new ExtensionCheckInterfaces());
        this.profiler = MixinEnvironment.getProfiler();
    }
    
    private IHotSwap initHotSwapper(final MixinEnvironment v-1) {
        if (!v-1.getOption(MixinEnvironment.Option.HOT_SWAP)) {
            return null;
        }
        try {
            MixinTransformer.logger.info("Attempting to load Hot-Swap agent");
            final Class<? extends IHotSwap> a1 = (Class<? extends IHotSwap>)Class.forName("org.spongepowered.tools.agent.MixinAgent");
            final Constructor<? extends IHotSwap> v1 = a1.getDeclaredConstructor(MixinTransformer.class);
            return (IHotSwap)v1.newInstance(this);
        }
        catch (Throwable v2) {
            MixinTransformer.logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[] { v2.getClass().getSimpleName(), v2.getMessage() });
            return null;
        }
    }
    
    public void audit(final MixinEnvironment v-4) {
        final Set<String> set = new HashSet<String>();
        for (final MixinConfig a1 : this.configs) {
            set.addAll(a1.getUnhandledTargets());
        }
        final Logger logger = LogManager.getLogger("mixin/audit");
        for (final String v0 : set) {
            try {
                logger.info("Force-loading class {}", new Object[] { v0 });
                this.service.getClassProvider().findClass(v0, true);
            }
            catch (ClassNotFoundException v2) {
                logger.error("Could not force-load " + v0, (Throwable)v2);
            }
        }
        for (final MixinConfig v3 : this.configs) {
            for (final String v4 : v3.getUnhandledTargets()) {
                final ClassAlreadyLoadedException v5 = new ClassAlreadyLoadedException(v4 + " was already classloaded");
                logger.error("Could not force-load " + v4, (Throwable)v5);
            }
        }
        if (v-4.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
            this.printProfilerSummary();
        }
    }
    
    private void printProfilerSummary() {
        final DecimalFormat decimalFormat = new DecimalFormat("(###0.000");
        final DecimalFormat decimalFormat2 = new DecimalFormat("(###0.0");
        final PrettyPrinter printer = this.profiler.printer(false, false);
        final long totalTime = this.profiler.get("mixin.prepare").getTotalTime();
        final long totalTime2 = this.profiler.get("mixin.read").getTotalTime();
        final long totalTime3 = this.profiler.get("mixin.apply").getTotalTime();
        final long totalTime4 = this.profiler.get("mixin.write").getTotalTime();
        final long totalTime5 = this.profiler.get("mixin").getTotalTime();
        final long totalTime6 = this.profiler.get("class.load").getTotalTime();
        final long totalTime7 = this.profiler.get("class.transform").getTotalTime();
        final long totalTime8 = this.profiler.get("mixin.debug.export").getTotalTime();
        final long n = totalTime5 - totalTime6 - totalTime7 - totalTime8;
        final double n2 = n / (double)totalTime5 * 100.0;
        final double n3 = totalTime6 / (double)totalTime5 * 100.0;
        final double n4 = totalTime7 / (double)totalTime5 * 100.0;
        final double n5 = totalTime8 / (double)totalTime5 * 100.0;
        long n6 = 0L;
        Profiler.Section section = null;
        for (final Profiler.Section v0 : this.profiler.getSections()) {
            final long v2 = v0.getName().startsWith("class.transform.") ? v0.getTotalTime() : 0L;
            if (v2 > n6) {
                n6 = v2;
                section = v0;
            }
        }
        printer.hr().add("Summary").hr().add();
        final String a2 = "%9d ms %12s seconds)";
        printer.kv("Total mixin time", a2, totalTime5, decimalFormat.format(totalTime5 * 0.001)).add();
        printer.kv("Preparing mixins", a2, totalTime, decimalFormat.format(totalTime * 0.001));
        printer.kv("Reading input", a2, totalTime2, decimalFormat.format(totalTime2 * 0.001));
        printer.kv("Applying mixins", a2, totalTime3, decimalFormat.format(totalTime3 * 0.001));
        printer.kv("Writing output", a2, totalTime4, decimalFormat.format(totalTime4 * 0.001)).add();
        printer.kv("of which", (Object)"");
        printer.kv("Time spent loading from disk", a2, totalTime6, decimalFormat.format(totalTime6 * 0.001));
        printer.kv("Time spent transforming classes", a2, totalTime7, decimalFormat.format(totalTime7 * 0.001)).add();
        if (section != null) {
            printer.kv("Worst transformer", (Object)section.getName());
            printer.kv("Class", (Object)section.getInfo());
            printer.kv("Time spent", "%s seconds", section.getTotalSeconds());
            printer.kv("called", "%d times", section.getTotalCount()).add();
        }
        printer.kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", n, decimalFormat2.format(n2));
        printer.kv("Loading classes", "%9d ms %10s%% of total)", totalTime6, decimalFormat2.format(n3));
        printer.kv("Running transformers", "%9d ms %10s%% of total)", totalTime7, decimalFormat2.format(n4));
        if (totalTime8 > 0L) {
            printer.kv("Exporting classes (debug)", "%9d ms %10s%% of total)", totalTime8, decimalFormat2.format(n5));
        }
        printer.add();
        try {
            final Class<?> v3 = this.service.getClassProvider().findAgentClass("org.spongepowered.metronome.Agent", false);
            final Method v4 = v3.getDeclaredMethod("getTimes", (Class<?>[])new Class[0]);
            final Map<String, Long> v5 = (Map<String, Long>)v4.invoke(null, new Object[0]);
            printer.hr().add("Transformer Times").hr().add();
            int v6 = 10;
            for (final Map.Entry<String, Long> v7 : v5.entrySet()) {
                v6 = Math.max(v6, v7.getKey().length());
            }
            for (final Map.Entry<String, Long> v7 : v5.entrySet()) {
                final String v8 = v7.getKey();
                long v9 = 0L;
                for (final Profiler.Section v10 : this.profiler.getSections()) {
                    if (v8.equals(v10.getInfo())) {
                        v9 = v10.getTotalTime();
                        break;
                    }
                }
                if (v9 > 0L) {
                    printer.add("%-" + v6 + "s %8s ms %8s ms in mixin)", v8, v7.getValue() + v9, "(" + v9);
                }
                else {
                    printer.add("%-" + v6 + "s %8s ms", v8, v7.getValue());
                }
            }
            printer.add();
        }
        catch (Throwable t) {}
        printer.print();
    }
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public boolean isDelegationExcluded() {
        return true;
    }
    
    @Override
    public synchronized byte[] transformClassBytes(final String v-5, final String v-4, byte[] v-3) {
        if (v-4 == null || this.errorState) {
            return v-3;
        }
        final MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (v-3 == null) {
            for (final IClassGenerator a2 : this.extensions.getGenerators()) {
                final Profiler.Section a3 = this.profiler.begin("generator", a2.getClass().getSimpleName().toLowerCase());
                v-3 = a2.generate(v-4);
                a3.end();
                if (v-3 != null) {
                    this.extensions.export(currentEnvironment, v-4.replace('.', '/'), false, v-3);
                    return v-3;
                }
            }
            return v-3;
        }
        final boolean check = this.lock.push().check();
        final Profiler.Section v0 = this.profiler.begin("mixin");
        if (!check) {
            try {
                this.checkSelect(currentEnvironment);
            }
            catch (Exception a4) {
                this.lock.pop();
                v0.end();
                throw new MixinException(a4);
            }
        }
        try {
            if (this.postProcessor.canTransform(v-4)) {
                final Profiler.Section v2 = this.profiler.begin("postprocessor");
                final byte[] v3 = this.postProcessor.transformClassBytes(v-5, v-4, v-3);
                v2.end();
                this.extensions.export(currentEnvironment, v-4, false, v3);
                return v3;
            }
            SortedSet<MixinInfo> v4 = null;
            boolean v5 = false;
            for (final MixinConfig v6 : this.configs) {
                if (v6.packageMatch(v-4)) {
                    v5 = true;
                }
                else {
                    if (!v6.hasMixinsFor(v-4)) {
                        continue;
                    }
                    if (v4 == null) {
                        v4 = new TreeSet<MixinInfo>();
                    }
                    v4.addAll((Collection<?>)v6.getMixinsFor(v-4));
                }
            }
            if (v5) {
                throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", v-4));
            }
            if (v4 != null) {
                if (check) {
                    MixinTransformer.logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)new MixinException());
                    throw new MixinApplyError("Re-entrance error.");
                }
                if (this.hotSwapper != null) {
                    this.hotSwapper.registerTargetClass(v-4, v-3);
                }
                try {
                    final Profiler.Section v7 = this.profiler.begin("read");
                    final ClassNode v8 = this.readClass(v-3, true);
                    final TargetClassContext v9 = new TargetClassContext(currentEnvironment, this.extensions, this.sessionId, v-4, v8, v4);
                    v7.end();
                    v-3 = this.applyMixins(currentEnvironment, v9);
                    ++this.transformedCount;
                }
                catch (InvalidMixinException v10) {
                    this.dumpClassOnFailure(v-4, v-3, currentEnvironment);
                    this.handleMixinApplyError(v-4, v10, currentEnvironment);
                }
            }
            return v-3;
        }
        catch (Throwable v11) {
            v11.printStackTrace();
            this.dumpClassOnFailure(v-4, v-3, currentEnvironment);
            throw new MixinTransformerError("An unexpected critical error was encountered", v11);
        }
        finally {
            this.lock.pop();
            v0.end();
        }
    }
    
    public List<String> reload(final String v1, final byte[] v2) {
        if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
        }
        final List<String> v3 = new ArrayList<String>();
        for (final MixinConfig a1 : this.configs) {
            v3.addAll(a1.reloadMixin(v1, v2));
        }
        return v3;
    }
    
    private void checkSelect(final MixinEnvironment a1) {
        if (this.currentEnvironment != a1) {
            this.select(a1);
            return;
        }
        final int v1 = Mixins.getUnvisitedCount();
        if (v1 > 0 && this.transformedCount == 0) {
            this.select(a1);
        }
    }
    
    private void select(final MixinEnvironment v-9) {
        this.verboseLoggingLevel = (v-9.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG);
        if (this.transformedCount > 0) {
            MixinTransformer.logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[] { this.currentEnvironment, this.transformedCount });
        }
        final String s = (this.currentEnvironment == v-9) ? "Checking for additional" : "Preparing";
        MixinTransformer.logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[] { s, v-9 });
        this.profiler.setActive(true);
        this.profiler.mark(v-9.getPhase().toString() + ":prepare");
        final Profiler.Section begin = this.profiler.begin("prepare");
        this.selectConfigs(v-9);
        this.extensions.select(v-9);
        final int prepareConfigs = this.prepareConfigs(v-9);
        this.currentEnvironment = v-9;
        this.transformedCount = 0;
        begin.end();
        final long time = begin.getTime();
        final double seconds = begin.getSeconds();
        if (seconds > 0.25) {
            final long a1 = this.profiler.get("class.load").getTime();
            final long v1 = this.profiler.get("class.transform").getTime();
            final long v2 = this.profiler.get("mixin.plugin").getTime();
            final String v3 = new DecimalFormat("###0.000").format(seconds);
            final String v4 = new DecimalFormat("###0.0").format(time / (double)prepareConfigs);
            MixinTransformer.logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[] { prepareConfigs, v3, v4, a1, v1, v2 });
        }
        this.profiler.mark(v-9.getPhase().toString() + ":apply");
        this.profiler.setActive(v-9.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
    }
    
    private void selectConfigs(final MixinEnvironment v-2) {
        final Iterator<Config> iterator = Mixins.getConfigs().iterator();
        while (iterator.hasNext()) {
            final Config v0 = iterator.next();
            try {
                final MixinConfig a1 = v0.get();
                if (!a1.select(v-2)) {
                    continue;
                }
                iterator.remove();
                MixinTransformer.logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[] { a1 });
                a1.onSelect();
                this.pendingConfigs.add(a1);
            }
            catch (Exception v2) {
                MixinTransformer.logger.warn(String.format("Failed to select mixin config: %s", v0), (Throwable)v2);
            }
        }
        Collections.sort(this.pendingConfigs);
    }
    
    private int prepareConfigs(final MixinEnvironment v-4) {
        int n = 0;
        final IHotSwap hotSwapper = this.hotSwapper;
        for (final MixinConfig a1 : this.pendingConfigs) {
            a1.addListener(this.postProcessor);
            if (hotSwapper != null) {
                a1.addListener(new MixinConfig.IListener() {
                    final /* synthetic */ IHotSwap val$hotSwapper;
                    final /* synthetic */ MixinTransformer this$0;
                    
                    MixinTransformer$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public void onPrepare(final MixinInfo a1) {
                        hotSwapper.registerMixinClass(a1.getClassName());
                    }
                    
                    @Override
                    public void onInit(final MixinInfo a1) {
                    }
                });
            }
        }
        for (final MixinConfig v0 : this.pendingConfigs) {
            try {
                MixinTransformer.logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[] { v0, v0.getDeclaredMixinCount() });
                v0.prepare();
                n += v0.getMixinCount();
            }
            catch (InvalidMixinException v2) {
                this.handleMixinPrepareError(v0, v2, v-4);
            }
            catch (Exception v4) {
                final String v3 = v4.getMessage();
                MixinTransformer.logger.error("Error encountered whilst initialising mixin config '" + v0.getName() + "': " + v3, (Throwable)v4);
            }
        }
        for (final MixinConfig v0 : this.pendingConfigs) {
            final IMixinConfigPlugin v5 = v0.getPlugin();
            if (v5 == null) {
                continue;
            }
            final Set<String> v6 = new HashSet<String>();
            for (final MixinConfig v7 : this.pendingConfigs) {
                if (!v7.equals(v0)) {
                    v6.addAll(v7.getTargets());
                }
            }
            v5.acceptTargets(v0.getTargets(), Collections.unmodifiableSet((Set<? extends String>)v6));
        }
        for (final MixinConfig v0 : this.pendingConfigs) {
            try {
                v0.postInitialise();
            }
            catch (InvalidMixinException v2) {
                this.handleMixinPrepareError(v0, v2, v-4);
            }
            catch (Exception v4) {
                final String v3 = v4.getMessage();
                MixinTransformer.logger.error("Error encountered during mixin config postInit step'" + v0.getName() + "': " + v3, (Throwable)v4);
            }
        }
        this.configs.addAll(this.pendingConfigs);
        Collections.sort(this.configs);
        this.pendingConfigs.clear();
        return n;
    }
    
    private byte[] applyMixins(final MixinEnvironment v1, final TargetClassContext v2) {
        Profiler.Section v3 = this.profiler.begin("preapply");
        this.extensions.preApply(v2);
        v3 = v3.next("apply");
        this.apply(v2);
        v3 = v3.next("postapply");
        try {
            this.extensions.postApply(v2);
        }
        catch (ExtensionCheckClass.ValidationFailedException a1) {
            MixinTransformer.logger.info(a1.getMessage());
            if (v2.isExportForced() || v1.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
                this.writeClass(v2);
            }
        }
        v3.end();
        return this.writeClass(v2);
    }
    
    private void apply(final TargetClassContext a1) {
        a1.applyMixins();
    }
    
    private void handleMixinPrepareError(final MixinConfig a1, final InvalidMixinException a2, final MixinEnvironment a3) throws MixinPrepareError {
        this.handleMixinError(a1.getName(), a2, a3, ErrorPhase.PREPARE);
    }
    
    private void handleMixinApplyError(final String a1, final InvalidMixinException a2, final MixinEnvironment a3) throws MixinApplyError {
        this.handleMixinError(a1, a2, a3, ErrorPhase.APPLY);
    }
    
    private void handleMixinError(final String a4, final InvalidMixinException v1, final MixinEnvironment v2, final ErrorPhase v3) throws Error {
        this.errorState = true;
        final IMixinInfo v4 = v1.getMixin();
        if (v4 == null) {
            MixinTransformer.logger.error("InvalidMixinException has no mixin!", (Throwable)v1);
            throw v1;
        }
        final IMixinConfig v5 = v4.getConfig();
        final MixinEnvironment.Phase v6 = v4.getPhase();
        IMixinErrorHandler.ErrorAction v7 = v5.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
        if (v2.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            new PrettyPrinter().add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", (Object)v3.name()).kv("Mixin", (Object)v4.getClassName()).kv("Config", (Object)v5.getName()).kv("Phase", v6).hr('-').add("    %s", v1.getClass().getName()).hr('-').addWrapped("    %s", v1.getMessage()).hr('-').add(v1, 8).trace(v7.logLevel);
        }
        for (final IMixinErrorHandler a5 : this.getErrorHandlers(v4.getPhase())) {
            final IMixinErrorHandler.ErrorAction a6 = v3.onError(a5, a4, v1, v4, v7);
            if (a6 != null) {
                v7 = a6;
            }
        }
        MixinTransformer.logger.log(v7.logLevel, v3.getLogMessage(a4, v1, v4), (Throwable)v1);
        this.errorState = false;
        if (v7 == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(v3.getErrorMessage(v4, v5, v6), v1);
        }
    }
    
    private List<IMixinErrorHandler> getErrorHandlers(final MixinEnvironment.Phase v-4) {
        final List<IMixinErrorHandler> list = new ArrayList<IMixinErrorHandler>();
        for (final String s : Mixins.getErrorHandlerClasses()) {
            try {
                MixinTransformer.logger.info("Instancing error handler class {}", new Object[] { s });
                final Class<?> a1 = this.service.getClassProvider().findClass(s, true);
                final IMixinErrorHandler v1 = (IMixinErrorHandler)a1.newInstance();
                if (v1 == null) {
                    continue;
                }
                list.add(v1);
            }
            catch (Throwable t) {}
        }
        return list;
    }
    
    private byte[] writeClass(final TargetClassContext a1) {
        return this.writeClass(a1.getClassName(), a1.getClassNode(), a1.isExportForced());
    }
    
    private byte[] writeClass(final String a1, final ClassNode a2, final boolean a3) {
        final Profiler.Section v1 = this.profiler.begin("write");
        final byte[] v2 = this.writeClass(a2);
        v1.end();
        this.extensions.export(this.currentEnvironment, a1, a3, v2);
        return v2;
    }
    
    private void dumpClassOnFailure(final String a3, final byte[] v1, final MixinEnvironment v2) {
        if (v2.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
            final ExtensionClassExporter a4 = (ExtensionClassExporter)this.extensions.getExtension((Class)ExtensionClassExporter.class);
            a4.dumpClass(a3.replace('.', '/') + ".target", v1);
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    enum ErrorPhase
    {
        PREPARE {
            MixinTransformer$ErrorPhase$1(final String a1, final int a2) {
            }
            
            @Override
            IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler a3, final String a4, final InvalidMixinException a5, final IMixinInfo v1, final IMixinErrorHandler.ErrorAction v2) {
                try {
                    return a3.onPrepareError(v1.getConfig(), a5, v1, v2);
                }
                catch (AbstractMethodError a6) {
                    return v2;
                }
            }
            
            @Override
            protected String getContext(final IMixinInfo a1, final String a2) {
                return String.format("preparing %s in %s", a1.getName(), a2);
            }
        }, 
        APPLY {
            MixinTransformer$ErrorPhase$2(final String a1, final int a2) {
            }
            
            @Override
            IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler a3, final String a4, final InvalidMixinException a5, final IMixinInfo v1, final IMixinErrorHandler.ErrorAction v2) {
                try {
                    return a3.onApplyError(a4, a5, v1, v2);
                }
                catch (AbstractMethodError a6) {
                    return v2;
                }
            }
            
            @Override
            protected String getContext(final IMixinInfo a1, final String a2) {
                return String.format("%s -> %s", a1, a2);
            }
        };
        
        private final String text;
        private static final /* synthetic */ ErrorPhase[] $VALUES;
        
        public static ErrorPhase[] values() {
            return ErrorPhase.$VALUES.clone();
        }
        
        public static ErrorPhase valueOf(final String a1) {
            return Enum.valueOf(ErrorPhase.class, a1);
        }
        
        private ErrorPhase() {
            this.text = this.name().toLowerCase();
        }
        
        abstract IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler p0, final String p1, final InvalidMixinException p2, final IMixinInfo p3, final IMixinErrorHandler.ErrorAction p4);
        
        protected abstract String getContext(final IMixinInfo p0, final String p1);
        
        public String getLogMessage(final String a1, final InvalidMixinException a2, final IMixinInfo a3) {
            return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(a3, a1), a2.getClass().getName(), a2.getMessage());
        }
        
        public String getErrorMessage(final IMixinInfo a1, final IMixinConfig a2, final MixinEnvironment.Phase a3) {
            return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", a1, a3, a2, this.name());
        }
        
        ErrorPhase(final String a1, final int a2, final MixinTransformer$1 a3) {
            this();
        }
        
        static {
            $VALUES = new ErrorPhase[] { ErrorPhase.PREPARE, ErrorPhase.APPLY };
        }
    }
}
