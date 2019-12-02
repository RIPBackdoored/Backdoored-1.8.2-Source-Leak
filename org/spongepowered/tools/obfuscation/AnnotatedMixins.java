package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.util.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.validation.*;
import com.google.common.collect.*;
import java.util.regex.*;
import javax.tools.*;
import java.io.*;
import javax.lang.model.type.*;
import java.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.tools.obfuscation.struct.*;
import javax.annotation.processing.*;
import javax.lang.model.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider
{
    private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
    private static Map<ProcessingEnvironment, AnnotatedMixins> instances;
    private final CompilerEnvironment env;
    private final ProcessingEnvironment processingEnv;
    private final Map<String, AnnotatedMixin> mixins;
    private final List<AnnotatedMixin> mixinsForPass;
    private final IObfuscationManager obf;
    private final List<IMixinValidator> validators;
    private final Map<String, Integer> tokenCache;
    private final TargetMap targets;
    private Properties properties;
    
    private AnnotatedMixins(final ProcessingEnvironment a1) {
        super();
        this.mixins = new HashMap<String, AnnotatedMixin>();
        this.mixinsForPass = new ArrayList<AnnotatedMixin>();
        this.tokenCache = new HashMap<String, Integer>();
        this.env = this.detectEnvironment(a1);
        this.processingEnv = a1;
        this.printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.7.11");
        this.targets = this.initTargetMap();
        (this.obf = new ObfuscationManager(this)).init();
        this.validators = (List<IMixinValidator>)ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
        this.initTokenCache(this.getOption("tokens"));
    }
    
    protected TargetMap initTargetMap() {
        final TargetMap create = TargetMap.create(System.getProperty("mixin.target.mapid"));
        System.setProperty("mixin.target.mapid", create.getSessionId());
        final String v0 = this.getOption("dependencyTargetsFile");
        if (v0 != null) {
            try {
                create.readImports(new File(v0));
            }
            catch (IOException v2) {
                this.printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + v0);
            }
        }
        return create;
    }
    
    private void initTokenCache(final String v-5) {
        if (v-5 != null) {
            final Pattern compile = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
            final String[] split;
            final String[] array = split = v-5.replaceAll("\\s", "").toUpperCase().split("[;,]");
            for (final String v1 : split) {
                final Matcher a1 = compile.matcher(v1);
                if (a1.matches()) {
                    this.tokenCache.put(a1.group(1), Integer.parseInt(a1.group(2)));
                }
            }
        }
    }
    
    @Override
    public ITypeHandleProvider getTypeProvider() {
        return this;
    }
    
    @Override
    public ITokenProvider getTokenProvider() {
        return this;
    }
    
    @Override
    public IObfuscationManager getObfuscationManager() {
        return this.obf;
    }
    
    @Override
    public IJavadocProvider getJavadocProvider() {
        return this;
    }
    
    @Override
    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }
    
    @Override
    public CompilerEnvironment getCompilerEnvironment() {
        return this.env;
    }
    
    @Override
    public Integer getToken(final String a1) {
        if (this.tokenCache.containsKey(a1)) {
            return this.tokenCache.get(a1);
        }
        final String v1 = this.getOption(a1);
        Integer v2 = null;
        try {
            v2 = Integer.parseInt(v1);
        }
        catch (Exception ex) {}
        this.tokenCache.put(a1, v2);
        return v2;
    }
    
    @Override
    public String getOption(final String a1) {
        if (a1 == null) {
            return null;
        }
        final String v1 = this.processingEnv.getOptions().get(a1);
        if (v1 != null) {
            return v1;
        }
        return this.getProperties().getProperty(a1);
    }
    
    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
            try {
                final Filer filer = this.processingEnv.getFiler();
                final FileObject v0 = filer.getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
                if (v0 != null) {
                    final InputStream v2 = v0.openInputStream();
                    this.properties.load(v2);
                    v2.close();
                }
            }
            catch (Exception ex) {}
        }
        return this.properties;
    }
    
    private CompilerEnvironment detectEnvironment(final ProcessingEnvironment a1) {
        if (a1.getClass().getName().contains("jdt")) {
            return CompilerEnvironment.JDT;
        }
        return CompilerEnvironment.JAVAC;
    }
    
    public void writeMappings() {
        this.obf.writeMappings();
    }
    
    public void writeReferences() {
        this.obf.writeReferences();
    }
    
    public void clear() {
        this.mixins.clear();
    }
    
    public void registerMixin(final TypeElement v2) {
        final String v3 = v2.getQualifiedName().toString();
        if (!this.mixins.containsKey(v3)) {
            final AnnotatedMixin a1 = new AnnotatedMixin(this, v2);
            this.targets.registerTargets(a1);
            a1.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
            this.mixins.put(v3, a1);
            this.mixinsForPass.add(a1);
        }
    }
    
    public AnnotatedMixin getMixin(final TypeElement a1) {
        return this.getMixin(a1.getQualifiedName().toString());
    }
    
    public AnnotatedMixin getMixin(final String a1) {
        return this.mixins.get(a1);
    }
    
    public Collection<TypeMirror> getMixinsTargeting(final TypeMirror a1) {
        return this.getMixinsTargeting((TypeElement)((DeclaredType)a1).asElement());
    }
    
    public Collection<TypeMirror> getMixinsTargeting(final TypeElement v-2) {
        final List<TypeMirror> list = new ArrayList<TypeMirror>();
        for (final TypeReference v1 : this.targets.getMixinsTargeting(v-2)) {
            final TypeHandle a1 = v1.getHandle(this.processingEnv);
            if (a1 != null) {
                list.add(a1.getType());
            }
        }
        return list;
    }
    
    public void registerAccessor(final TypeElement a1, final ExecutableElement a2) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", a2);
            return;
        }
        final AnnotationHandle v2 = AnnotationHandle.of(a2, Accessor.class);
        v1.registerAccessor(a2, v2, this.shouldRemap(v1, v2));
    }
    
    public void registerInvoker(final TypeElement a1, final ExecutableElement a2) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", a2);
            return;
        }
        final AnnotationHandle v2 = AnnotationHandle.of(a2, Invoker.class);
        v1.registerInvoker(a2, v2, this.shouldRemap(v1, v2));
    }
    
    public void registerOverwrite(final TypeElement a1, final ExecutableElement a2) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", a2);
            return;
        }
        final AnnotationHandle v2 = AnnotationHandle.of(a2, Overwrite.class);
        v1.registerOverwrite(a2, v2, this.shouldRemap(v1, v2));
    }
    
    public void registerShadow(final TypeElement a1, final VariableElement a2, final AnnotationHandle a3) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", a2);
            return;
        }
        v1.registerShadow(a2, a3, this.shouldRemap(v1, a3));
    }
    
    public void registerShadow(final TypeElement a1, final ExecutableElement a2, final AnnotationHandle a3) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", a2);
            return;
        }
        v1.registerShadow(a2, a3, this.shouldRemap(v1, a3));
    }
    
    public void registerInjector(final TypeElement a1, final ExecutableElement a2, final AnnotationHandle a3) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found " + a3 + " annotation on a non-mixin method", a2);
            return;
        }
        final InjectorRemap v2 = new InjectorRemap(this.shouldRemap(v1, a3));
        v1.registerInjector(a2, a3, v2);
        v2.dispatchPendingMessages(this);
    }
    
    public void registerSoftImplements(final TypeElement a1, final AnnotationHandle a2) {
        final AnnotatedMixin v1 = this.getMixin(a1);
        if (v1 == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
            return;
        }
        v1.registerSoftImplements(a2);
    }
    
    public void onPassStarted() {
        this.mixinsForPass.clear();
    }
    
    public void onPassCompleted(final RoundEnvironment v2) {
        if (!"true".equalsIgnoreCase(this.getOption("disableTargetExport"))) {
            this.targets.write(true);
        }
        for (final AnnotatedMixin a1 : v2.processingOver() ? this.mixins.values() : this.mixinsForPass) {
            a1.runValidators(v2.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
        }
    }
    
    private boolean shouldRemap(final AnnotatedMixin a1, final AnnotationHandle a2) {
        return a2.getBoolean("remap", a1.remap());
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind a1, final CharSequence a2) {
        if (this.env == CompilerEnvironment.JAVAC || a1 != Diagnostic.Kind.NOTE) {
            this.processingEnv.getMessager().printMessage(a1, a2);
        }
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3) {
        this.processingEnv.getMessager().printMessage(a1, a2, a3);
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationMirror a4) {
        this.processingEnv.getMessager().printMessage(a1, a2, a3, a4);
    }
    
    @Override
    public void printMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationMirror a4, final AnnotationValue a5) {
        this.processingEnv.getMessager().printMessage(a1, a2, a3, a4, a5);
    }
    
    @Override
    public TypeHandle getTypeHandle(String v-4) {
        v-4 = v-4.replace('/', '.');
        final Elements elementUtils = this.processingEnv.getElementUtils();
        final TypeElement typeElement = elementUtils.getTypeElement(v-4);
        if (typeElement != null) {
            try {
                return new TypeHandle(typeElement);
            }
            catch (NullPointerException ex) {}
        }
        final int lastIndex = v-4.lastIndexOf(46);
        if (lastIndex > -1) {
            final String a1 = v-4.substring(0, lastIndex);
            final PackageElement v1 = elementUtils.getPackageElement(a1);
            if (v1 != null) {
                return new TypeHandle(v1, v-4);
            }
        }
        return null;
    }
    
    @Override
    public TypeHandle getSimulatedHandle(String v2, final TypeMirror v3) {
        v2 = v2.replace('/', '.');
        final int v4 = v2.lastIndexOf(46);
        if (v4 > -1) {
            final String a1 = v2.substring(0, v4);
            final PackageElement a2 = this.processingEnv.getElementUtils().getPackageElement(a1);
            if (a2 != null) {
                return new TypeHandleSimulated(a2, v2, v3);
            }
        }
        return new TypeHandleSimulated(v2, v3);
    }
    
    @Override
    public String getJavadoc(final Element a1) {
        final Elements v1 = this.processingEnv.getElementUtils();
        return v1.getDocComment(a1);
    }
    
    public static AnnotatedMixins getMixinsForEnvironment(final ProcessingEnvironment a1) {
        AnnotatedMixins v1 = AnnotatedMixins.instances.get(a1);
        if (v1 == null) {
            v1 = new AnnotatedMixins(a1);
            AnnotatedMixins.instances.put(a1, v1);
        }
        return v1;
    }
    
    static {
        AnnotatedMixins.instances = new HashMap<ProcessingEnvironment, AnnotatedMixins>();
    }
}
