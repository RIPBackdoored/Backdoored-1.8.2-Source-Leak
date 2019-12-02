package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class OrgChartSpec extends GenericJson
{
    @Key
    private ChartData labels;
    @Key
    private Color nodeColor;
    @Key
    private String nodeSize;
    @Key
    private ChartData parentLabels;
    @Key
    private Color selectedNodeColor;
    @Key
    private ChartData tooltips;
    
    public OrgChartSpec() {
        super();
    }
    
    public ChartData getLabels() {
        return this.labels;
    }
    
    public OrgChartSpec setLabels(final ChartData labels) {
        this.labels = labels;
        return this;
    }
    
    public Color getNodeColor() {
        return this.nodeColor;
    }
    
    public OrgChartSpec setNodeColor(final Color nodeColor) {
        this.nodeColor = nodeColor;
        return this;
    }
    
    public String getNodeSize() {
        return this.nodeSize;
    }
    
    public OrgChartSpec setNodeSize(final String nodeSize) {
        this.nodeSize = nodeSize;
        return this;
    }
    
    public ChartData getParentLabels() {
        return this.parentLabels;
    }
    
    public OrgChartSpec setParentLabels(final ChartData parentLabels) {
        this.parentLabels = parentLabels;
        return this;
    }
    
    public Color getSelectedNodeColor() {
        return this.selectedNodeColor;
    }
    
    public OrgChartSpec setSelectedNodeColor(final Color selectedNodeColor) {
        this.selectedNodeColor = selectedNodeColor;
        return this;
    }
    
    public ChartData getTooltips() {
        return this.tooltips;
    }
    
    public OrgChartSpec setTooltips(final ChartData tooltips) {
        this.tooltips = tooltips;
        return this;
    }
    
    @Override
    public OrgChartSpec set(final String a1, final Object a2) {
        return (OrgChartSpec)super.set(a1, a2);
    }
    
    @Override
    public OrgChartSpec clone() {
        return (OrgChartSpec)super.clone();
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
