package org.reflections.scanners;

import com.google.common.base.*;
import java.util.*;

public class TypeElementsScanner extends AbstractScanner
{
    private boolean includeFields;
    private boolean includeMethods;
    private boolean includeAnnotations;
    private boolean publicOnly;
    
    public TypeElementsScanner() {
        super();
        this.includeFields = true;
        this.includeMethods = true;
        this.includeAnnotations = true;
        this.publicOnly = true;
    }
    
    @Override
    public void scan(final Object v-2) {
        final String className = this.getMetadataAdapter().getClassName(v-2);
        if (!this.acceptResult(className)) {
            return;
        }
        this.getStore().put((Object)className, (Object)"");
        if (this.includeFields) {
            for (final Object v1 : this.getMetadataAdapter().getFields(v-2)) {
                final String a1 = this.getMetadataAdapter().getFieldName(v1);
                this.getStore().put((Object)className, (Object)a1);
            }
        }
        if (this.includeMethods) {
            for (final Object v1 : this.getMetadataAdapter().getMethods(v-2)) {
                if (!this.publicOnly || this.getMetadataAdapter().isPublic(v1)) {
                    final String v2 = this.getMetadataAdapter().getMethodName(v1) + "(" + Joiner.on(", ").join(this.getMetadataAdapter().getParameterNames(v1)) + ")";
                    this.getStore().put((Object)className, (Object)v2);
                }
            }
        }
        if (this.includeAnnotations) {
            for (final Object v1 : this.getMetadataAdapter().getClassAnnotationNames(v-2)) {
                this.getStore().put((Object)className, (Object)("@" + v1));
            }
        }
    }
    
    public TypeElementsScanner includeFields() {
        return this.includeFields(true);
    }
    
    public TypeElementsScanner includeFields(final boolean a1) {
        this.includeFields = a1;
        return this;
    }
    
    public TypeElementsScanner includeMethods() {
        return this.includeMethods(true);
    }
    
    public TypeElementsScanner includeMethods(final boolean a1) {
        this.includeMethods = a1;
        return this;
    }
    
    public TypeElementsScanner includeAnnotations() {
        return this.includeAnnotations(true);
    }
    
    public TypeElementsScanner includeAnnotations(final boolean a1) {
        this.includeAnnotations = a1;
        return this;
    }
    
    public TypeElementsScanner publicOnly(final boolean a1) {
        this.publicOnly = a1;
        return this;
    }
    
    public TypeElementsScanner publicOnly() {
        return this.publicOnly(true);
    }
}
