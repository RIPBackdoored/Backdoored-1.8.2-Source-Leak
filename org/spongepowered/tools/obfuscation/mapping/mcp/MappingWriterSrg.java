package org.spongepowered.tools.obfuscation.mapping.mcp;

import org.spongepowered.tools.obfuscation.mapping.common.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.io.*;
import java.util.*;

public class MappingWriterSrg extends MappingWriter
{
    public MappingWriterSrg(final Messager a1, final Filer a2) {
        super(a1, a2);
    }
    
    @Override
    public void write(final String a3, final ObfuscationType a4, final IMappingConsumer.MappingSet<MappingField> v1, final IMappingConsumer.MappingSet<MappingMethod> v2) {
        if (a3 == null) {
            return;
        }
        PrintWriter v3 = null;
        try {
            v3 = this.openFileWriter(a3, a4 + " output SRGs");
            this.writeFieldMappings(v3, v1);
            this.writeMethodMappings(v3, v2);
        }
        catch (IOException a5) {
            a5.printStackTrace();
        }
        finally {
            if (v3 != null) {
                try {
                    v3.close();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    protected void writeFieldMappings(final PrintWriter v1, final IMappingConsumer.MappingSet<MappingField> v2) {
        for (final IMappingConsumer.MappingSet.Pair<MappingField> a1 : v2) {
            v1.println(this.formatFieldMapping(a1));
        }
    }
    
    protected void writeMethodMappings(final PrintWriter v1, final IMappingConsumer.MappingSet<MappingMethod> v2) {
        for (final IMappingConsumer.MappingSet.Pair<MappingMethod> a1 : v2) {
            v1.println(this.formatMethodMapping(a1));
        }
    }
    
    protected String formatFieldMapping(final IMappingConsumer.MappingSet.Pair<MappingField> a1) {
        return String.format("FD: %s/%s %s/%s", ((MappingField)a1.from).getOwner(), ((MappingField)a1.from).getName(), ((MappingField)a1.to).getOwner(), ((MappingField)a1.to).getName());
    }
    
    protected String formatMethodMapping(final IMappingConsumer.MappingSet.Pair<MappingMethod> a1) {
        return String.format("MD: %s %s %s %s", ((MappingMethod)a1.from).getName(), ((MappingMethod)a1.from).getDesc(), ((MappingMethod)a1.to).getName(), ((MappingMethod)a1.to).getDesc());
    }
}
