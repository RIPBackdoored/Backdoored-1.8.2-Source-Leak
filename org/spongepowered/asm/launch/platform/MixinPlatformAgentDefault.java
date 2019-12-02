package org.spongepowered.asm.launch.platform;

import java.net.*;

public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract
{
    public MixinPlatformAgentDefault(final MixinPlatformManager a1, final URI a2) {
        super(a1, a2);
    }
    
    @Override
    public void prepare() {
        final String value = this.attributes.get("MixinCompatibilityLevel");
        if (value != null) {
            this.manager.setCompatibilityLevel(value);
        }
        final String value2 = this.attributes.get("MixinConfigs");
        if (value2 != null) {
            for (final String v1 : value2.split(",")) {
                this.manager.addConfig(v1.trim());
            }
        }
        final String value3 = this.attributes.get("MixinTokenProviders");
        if (value3 != null) {
            for (final String v2 : value3.split(",")) {
                this.manager.addTokenProvider(v2.trim());
            }
        }
    }
    
    @Override
    public void initPrimaryContainer() {
    }
    
    @Override
    public void inject() {
    }
    
    @Override
    public String getLaunchTarget() {
        return this.attributes.get("Main-Class");
    }
}
