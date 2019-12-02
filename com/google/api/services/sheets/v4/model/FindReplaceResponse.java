package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class FindReplaceResponse extends GenericJson
{
    @Key
    private Integer formulasChanged;
    @Key
    private Integer occurrencesChanged;
    @Key
    private Integer rowsChanged;
    @Key
    private Integer sheetsChanged;
    @Key
    private Integer valuesChanged;
    
    public FindReplaceResponse() {
        super();
    }
    
    public Integer getFormulasChanged() {
        return this.formulasChanged;
    }
    
    public FindReplaceResponse setFormulasChanged(final Integer formulasChanged) {
        this.formulasChanged = formulasChanged;
        return this;
    }
    
    public Integer getOccurrencesChanged() {
        return this.occurrencesChanged;
    }
    
    public FindReplaceResponse setOccurrencesChanged(final Integer occurrencesChanged) {
        this.occurrencesChanged = occurrencesChanged;
        return this;
    }
    
    public Integer getRowsChanged() {
        return this.rowsChanged;
    }
    
    public FindReplaceResponse setRowsChanged(final Integer rowsChanged) {
        this.rowsChanged = rowsChanged;
        return this;
    }
    
    public Integer getSheetsChanged() {
        return this.sheetsChanged;
    }
    
    public FindReplaceResponse setSheetsChanged(final Integer sheetsChanged) {
        this.sheetsChanged = sheetsChanged;
        return this;
    }
    
    public Integer getValuesChanged() {
        return this.valuesChanged;
    }
    
    public FindReplaceResponse setValuesChanged(final Integer valuesChanged) {
        this.valuesChanged = valuesChanged;
        return this;
    }
    
    @Override
    public FindReplaceResponse set(final String a1, final Object a2) {
        return (FindReplaceResponse)super.set(a1, a2);
    }
    
    @Override
    public FindReplaceResponse clone() {
        return (FindReplaceResponse)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
