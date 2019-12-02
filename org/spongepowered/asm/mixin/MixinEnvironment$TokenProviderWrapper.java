package org.spongepowered.asm.mixin;

import org.spongepowered.asm.mixin.extensibility.*;

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
