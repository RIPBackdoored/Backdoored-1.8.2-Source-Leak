package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.*;
import java.util.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.service.*;
import java.io.*;
import com.google.gson.*;

public final class ReferenceMapper implements IReferenceMapper, Serializable
{
    private static final long serialVersionUID = 2L;
    public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
    public static final ReferenceMapper DEFAULT_MAPPER;
    private final Map<String, Map<String, String>> mappings;
    private final Map<String, Map<String, Map<String, String>>> data;
    private final transient boolean readOnly;
    private transient String context;
    private transient String resource;
    
    public ReferenceMapper() {
        this(false, "mixin.refmap.json");
    }
    
    private ReferenceMapper(final boolean a1, final String a2) {
        super();
        this.mappings = (Map<String, Map<String, String>>)Maps.newHashMap();
        this.data = (Map<String, Map<String, Map<String, String>>>)Maps.newHashMap();
        this.context = null;
        this.readOnly = a1;
        this.resource = a2;
    }
    
    @Override
    public boolean isDefault() {
        return this.readOnly;
    }
    
    private void setResourceName(final String a1) {
        if (!this.readOnly) {
            this.resource = ((a1 != null) ? a1 : "<unknown resource>");
        }
    }
    
    @Override
    public String getResourceName() {
        return this.resource;
    }
    
    @Override
    public String getStatus() {
        return this.isDefault() ? "No refMap loaded." : ("Using refmap " + this.getResourceName());
    }
    
    @Override
    public String getContext() {
        return this.context;
    }
    
    @Override
    public void setContext(final String a1) {
        this.context = a1;
    }
    
    @Override
    public String remap(final String a1, final String a2) {
        return this.remapWithContext(this.context, a1, a2);
    }
    
    @Override
    public String remapWithContext(final String a1, final String a2, final String a3) {
        Map<String, Map<String, String>> v1 = this.mappings;
        if (a1 != null) {
            v1 = this.data.get(a1);
            if (v1 == null) {
                v1 = this.mappings;
            }
        }
        return this.remap(v1, a2, a3);
    }
    
    private String remap(final Map<String, Map<String, String>> a3, final String v1, final String v2) {
        if (v1 == null) {
            for (final Map<String, String> a4 : a3.values()) {
                if (a4.containsKey(v2)) {
                    return a4.get(v2);
                }
            }
        }
        final Map<String, String> v3 = a3.get(v1);
        if (v3 == null) {
            return v2;
        }
        final String v4 = v3.get(v2);
        return (v4 != null) ? v4 : v2;
    }
    
    public String addMapping(final String a1, final String a2, final String a3, final String a4) {
        if (this.readOnly || a3 == null || a4 == null || a3.equals(a4)) {
            return null;
        }
        Map<String, Map<String, String>> v1 = this.mappings;
        if (a1 != null) {
            v1 = this.data.get(a1);
            if (v1 == null) {
                v1 = (Map<String, Map<String, String>>)Maps.newHashMap();
                this.data.put(a1, v1);
            }
        }
        Map<String, String> v2 = v1.get(a2);
        if (v2 == null) {
            v2 = new HashMap<String, String>();
            v1.put(a2, v2);
        }
        return v2.put(a3, a4);
    }
    
    public void write(final Appendable a1) {
        new GsonBuilder().setPrettyPrinting().create().toJson(this, a1);
    }
    
    public static ReferenceMapper read(final String v-2) {
        final Logger logger = LogManager.getLogger("mixin");
        Reader v0 = null;
        try {
            final IMixinService v2 = MixinService.getService();
            final InputStream v3 = v2.getResourceAsStream(v-2);
            if (v3 != null) {
                v0 = new InputStreamReader(v3);
                final ReferenceMapper a1 = readJson(v0);
                a1.setResourceName(v-2);
                return a1;
            }
            return ReferenceMapper.DEFAULT_MAPPER;
        }
        catch (JsonParseException v4) {
            logger.error("Invalid REFMAP JSON in " + v-2 + ": " + v4.getClass().getName() + " " + v4.getMessage());
        }
        catch (Exception v5) {
            logger.error("Failed reading REFMAP JSON from " + v-2 + ": " + v5.getClass().getName() + " " + v5.getMessage());
        }
        finally {
            IOUtils.closeQuietly(v0);
        }
        return ReferenceMapper.DEFAULT_MAPPER;
    }
    
    public static ReferenceMapper read(final Reader v1, final String v2) {
        try {
            final ReferenceMapper a1 = readJson(v1);
            a1.setResourceName(v2);
            return a1;
        }
        catch (Exception a2) {
            return ReferenceMapper.DEFAULT_MAPPER;
        }
    }
    
    private static ReferenceMapper readJson(final Reader a1) {
        return new Gson().fromJson(a1, ReferenceMapper.class);
    }
    
    static {
        DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
    }
}
