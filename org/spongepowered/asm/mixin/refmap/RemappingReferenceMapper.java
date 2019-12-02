package org.spongepowered.asm.mixin.refmap;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;
import com.google.common.io.*;
import org.apache.logging.log4j.*;

public final class RemappingReferenceMapper implements IReferenceMapper
{
    private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
    private static final String DEFAULT_MAPPING_ENV = "searge";
    private static final Logger logger;
    private static final Map<String, Map<String, String>> srgs;
    private final IReferenceMapper refMap;
    private final Map<String, String> mappings;
    private final Map<String, Map<String, String>> cache;
    
    private RemappingReferenceMapper(final MixinEnvironment a1, final IReferenceMapper a2) {
        super();
        this.cache = new HashMap<String, Map<String, String>>();
        (this.refMap = a2).setContext(getMappingEnv(a1));
        final String v1 = getResource(a1);
        this.mappings = loadSrgs(v1);
        RemappingReferenceMapper.logger.info("Remapping refMap {} using {}", new Object[] { a2.getResourceName(), v1 });
    }
    
    @Override
    public boolean isDefault() {
        return this.refMap.isDefault();
    }
    
    @Override
    public String getResourceName() {
        return this.refMap.getResourceName();
    }
    
    @Override
    public String getStatus() {
        return this.refMap.getStatus();
    }
    
    @Override
    public String getContext() {
        return this.refMap.getContext();
    }
    
    @Override
    public void setContext(final String a1) {
    }
    
    @Override
    public String remap(final String v1, final String v2) {
        final Map<String, String> v3 = this.getCache(v1);
        String v4 = v3.get(v2);
        if (v4 == null) {
            v4 = this.refMap.remap(v1, v2);
            for (final Map.Entry<String, String> a1 : this.mappings.entrySet()) {
                v4 = v4.replace(a1.getKey(), a1.getValue());
            }
            v3.put(v2, v4);
        }
        return v4;
    }
    
    private Map<String, String> getCache(final String a1) {
        Map<String, String> v1 = this.cache.get(a1);
        if (v1 == null) {
            v1 = new HashMap<String, String>();
            this.cache.put(a1, v1);
        }
        return v1;
    }
    
    @Override
    public String remapWithContext(final String a1, final String a2, final String a3) {
        return this.refMap.remapWithContext(a1, a2, a3);
    }
    
    private static Map<String, String> loadSrgs(final String v1) {
        if (RemappingReferenceMapper.srgs.containsKey(v1)) {
            return RemappingReferenceMapper.srgs.get(v1);
        }
        final Map<String, String> v2 = new HashMap<String, String>();
        RemappingReferenceMapper.srgs.put(v1, v2);
        final File v3 = new File(v1);
        if (!v3.isFile()) {
            return v2;
        }
        try {
            Files.readLines(v3, Charsets.UTF_8, (LineProcessor)new LineProcessor<Object>() {
                final /* synthetic */ Map val$map;
                
                RemappingReferenceMapper$1() {
                    super();
                }
                
                public Object getResult() {
                    return null;
                }
                
                public boolean processLine(final String v2) throws IOException {
                    if (Strings.isNullOrEmpty(v2) || v2.startsWith("#")) {
                        return true;
                    }
                    final int v3 = 0;
                    int v4 = 0;
                    int n2;
                    final int n = v2.startsWith("MD: ") ? (n2 = 2) : (v2.startsWith("FD: ") ? (n2 = 1) : (n2 = 0));
                    v4 = n2;
                    if (n > 0) {
                        final String[] a1 = v2.substring(4).split(" ", 4);
                        v2.put(a1[v3].substring(a1[v3].lastIndexOf(47) + 1), a1[v4].substring(a1[v4].lastIndexOf(47) + 1));
                    }
                    return true;
                }
            });
        }
        catch (IOException a1) {
            RemappingReferenceMapper.logger.warn("Could not read input SRG file: {}", new Object[] { v1 });
            RemappingReferenceMapper.logger.catching((Throwable)a1);
        }
        return v2;
    }
    
    public static IReferenceMapper of(final MixinEnvironment a1, final IReferenceMapper a2) {
        if (!a2.isDefault() && hasData(a1)) {
            return new RemappingReferenceMapper(a1, a2);
        }
        return a2;
    }
    
    private static boolean hasData(final MixinEnvironment a1) {
        final String v1 = getResource(a1);
        return v1 != null && new File(v1).exists();
    }
    
    private static String getResource(final MixinEnvironment a1) {
        final String v1 = a1.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
        return Strings.isNullOrEmpty(v1) ? System.getProperty("net.minecraftforge.gradle.GradleStart.srg.srg-mcp") : v1;
    }
    
    private static String getMappingEnv(final MixinEnvironment a1) {
        final String v1 = a1.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
        return Strings.isNullOrEmpty(v1) ? "searge" : v1;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        srgs = new HashMap<String, Map<String, String>>();
    }
}
