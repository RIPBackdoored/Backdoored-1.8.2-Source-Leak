package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.*;

public class ObfuscationTypeDescriptor
{
    private final String key;
    private final String inputFileArgName;
    private final String extraInputFilesArgName;
    private final String outFileArgName;
    private final Class<? extends ObfuscationEnvironment> environmentType;
    
    public ObfuscationTypeDescriptor(final String a1, final String a2, final String a3, final Class<? extends ObfuscationEnvironment> a4) {
        this(a1, a2, null, a3, a4);
    }
    
    public ObfuscationTypeDescriptor(final String a1, final String a2, final String a3, final String a4, final Class<? extends ObfuscationEnvironment> a5) {
        super();
        this.key = a1;
        this.inputFileArgName = a2;
        this.extraInputFilesArgName = a3;
        this.outFileArgName = a4;
        this.environmentType = a5;
    }
    
    public final String getKey() {
        return this.key;
    }
    
    public String getInputFileOption() {
        return this.inputFileArgName;
    }
    
    public String getExtraInputFilesOption() {
        return this.extraInputFilesArgName;
    }
    
    public String getOutputFileOption() {
        return this.outFileArgName;
    }
    
    public Class<? extends ObfuscationEnvironment> getEnvironmentType() {
        return this.environmentType;
    }
}
