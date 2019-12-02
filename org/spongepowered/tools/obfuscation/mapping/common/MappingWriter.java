package org.spongepowered.tools.obfuscation.mapping.common;

import org.spongepowered.tools.obfuscation.mapping.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.io.*;

public abstract class MappingWriter implements IMappingWriter
{
    private final Messager messager;
    private final Filer filer;
    
    public MappingWriter(final Messager a1, final Filer a2) {
        super();
        this.messager = a1;
        this.filer = a2;
    }
    
    protected PrintWriter openFileWriter(final String v1, final String v2) throws IOException {
        if (v1.matches("^.*[\\\\/:].*$")) {
            final File a1 = new File(v1);
            a1.getParentFile().mkdirs();
            this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + v2 + " to " + a1.getAbsolutePath());
            return new PrintWriter(a1);
        }
        final FileObject v3 = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", v1, new Element[0]);
        this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + v2 + " to " + new File(v3.toUri()).getAbsolutePath());
        return new PrintWriter(v3.openWriter());
    }
}
