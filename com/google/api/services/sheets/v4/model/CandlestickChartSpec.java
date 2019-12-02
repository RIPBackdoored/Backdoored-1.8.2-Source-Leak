package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import java.util.*;
import com.google.api.client.util.*;

public final class CandlestickChartSpec extends GenericJson
{
    @Key
    private List<CandlestickData> data;
    @Key
    private CandlestickDomain domain;
    
    public CandlestickChartSpec() {
        super();
    }
    
    public List<CandlestickData> getData() {
        return this.data;
    }
    
    public CandlestickChartSpec setData(final List<CandlestickData> data) {
        this.data = data;
        return this;
    }
    
    public CandlestickDomain getDomain() {
        return this.domain;
    }
    
    public CandlestickChartSpec setDomain(final CandlestickDomain domain) {
        this.domain = domain;
        return this;
    }
    
    @Override
    public CandlestickChartSpec set(final String a1, final Object a2) {
        return (CandlestickChartSpec)super.set(a1, a2);
    }
    
    @Override
    public CandlestickChartSpec clone() {
        return (CandlestickChartSpec)super.clone();
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
