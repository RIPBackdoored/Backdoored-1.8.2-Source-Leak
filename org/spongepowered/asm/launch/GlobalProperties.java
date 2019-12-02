package org.spongepowered.asm.launch;

import org.spongepowered.asm.service.*;
import java.util.*;

public final class GlobalProperties
{
    private static IGlobalPropertyService service;
    
    private GlobalProperties() {
        super();
    }
    
    private static IGlobalPropertyService getService() {
        if (GlobalProperties.service == null) {
            final ServiceLoader<IGlobalPropertyService> v1 = ServiceLoader.load(IGlobalPropertyService.class, GlobalProperties.class.getClassLoader());
            GlobalProperties.service = v1.iterator().next();
        }
        return GlobalProperties.service;
    }
    
    public static <T> T get(final String a1) {
        return getService().getProperty(a1);
    }
    
    public static void put(final String a1, final Object a2) {
        getService().setProperty(a1, a2);
    }
    
    public static <T> T get(final String a1, final T a2) {
        return getService().getProperty(a1, a2);
    }
    
    public static String getString(final String a1, final String a2) {
        return getService().getPropertyString(a1, a2);
    }
    
    public static final class Keys
    {
        public static final String INIT = "mixin.initialised";
        public static final String AGENTS = "mixin.agents";
        public static final String CONFIGS = "mixin.configs";
        public static final String TRANSFORMER = "mixin.transformer";
        public static final String PLATFORM_MANAGER = "mixin.platform";
        public static final String FML_LOAD_CORE_MOD = "mixin.launch.fml.loadcoremodmethod";
        public static final String FML_GET_REPARSEABLE_COREMODS = "mixin.launch.fml.reparseablecoremodsmethod";
        public static final String FML_CORE_MOD_MANAGER = "mixin.launch.fml.coremodmanagerclass";
        public static final String FML_GET_IGNORED_MODS = "mixin.launch.fml.ignoredmodsmethod";
        
        private Keys() {
            super();
        }
    }
}
