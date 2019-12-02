package com.sun.jna;

import java.awt.*;

private static class AWT
{
    private AWT() {
        super();
    }
    
    static long getWindowID(final Window a1) throws HeadlessException {
        return getComponentID(a1);
    }
    
    static long getComponentID(final Object a1) throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("No native windows when headless");
        }
        final Component v1 = (Component)a1;
        if (v1.isLightweight()) {
            throw new IllegalArgumentException("Component must be heavyweight");
        }
        if (!v1.isDisplayable()) {
            throw new IllegalStateException("Component must be displayable");
        }
        if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !v1.isVisible()) {
            throw new IllegalStateException("Component must be visible");
        }
        return Native.getWindowHandle0(v1);
    }
}
