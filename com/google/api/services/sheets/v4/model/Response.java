package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Response extends GenericJson
{
    @Key
    private AddBandingResponse addBanding;
    @Key
    private AddChartResponse addChart;
    @Key
    private AddDimensionGroupResponse addDimensionGroup;
    @Key
    private AddFilterViewResponse addFilterView;
    @Key
    private AddNamedRangeResponse addNamedRange;
    @Key
    private AddProtectedRangeResponse addProtectedRange;
    @Key
    private AddSheetResponse addSheet;
    @Key
    private CreateDeveloperMetadataResponse createDeveloperMetadata;
    @Key
    private DeleteConditionalFormatRuleResponse deleteConditionalFormatRule;
    @Key
    private DeleteDeveloperMetadataResponse deleteDeveloperMetadata;
    @Key
    private DeleteDimensionGroupResponse deleteDimensionGroup;
    @Key
    private DuplicateFilterViewResponse duplicateFilterView;
    @Key
    private DuplicateSheetResponse duplicateSheet;
    @Key
    private FindReplaceResponse findReplace;
    @Key
    private UpdateConditionalFormatRuleResponse updateConditionalFormatRule;
    @Key
    private UpdateDeveloperMetadataResponse updateDeveloperMetadata;
    @Key
    private UpdateEmbeddedObjectPositionResponse updateEmbeddedObjectPosition;
    
    public Response() {
        super();
    }
    
    public AddBandingResponse getAddBanding() {
        return this.addBanding;
    }
    
    public Response setAddBanding(final AddBandingResponse addBanding) {
        this.addBanding = addBanding;
        return this;
    }
    
    public AddChartResponse getAddChart() {
        return this.addChart;
    }
    
    public Response setAddChart(final AddChartResponse addChart) {
        this.addChart = addChart;
        return this;
    }
    
    public AddDimensionGroupResponse getAddDimensionGroup() {
        return this.addDimensionGroup;
    }
    
    public Response setAddDimensionGroup(final AddDimensionGroupResponse addDimensionGroup) {
        this.addDimensionGroup = addDimensionGroup;
        return this;
    }
    
    public AddFilterViewResponse getAddFilterView() {
        return this.addFilterView;
    }
    
    public Response setAddFilterView(final AddFilterViewResponse addFilterView) {
        this.addFilterView = addFilterView;
        return this;
    }
    
    public AddNamedRangeResponse getAddNamedRange() {
        return this.addNamedRange;
    }
    
    public Response setAddNamedRange(final AddNamedRangeResponse addNamedRange) {
        this.addNamedRange = addNamedRange;
        return this;
    }
    
    public AddProtectedRangeResponse getAddProtectedRange() {
        return this.addProtectedRange;
    }
    
    public Response setAddProtectedRange(final AddProtectedRangeResponse addProtectedRange) {
        this.addProtectedRange = addProtectedRange;
        return this;
    }
    
    public AddSheetResponse getAddSheet() {
        return this.addSheet;
    }
    
    public Response setAddSheet(final AddSheetResponse addSheet) {
        this.addSheet = addSheet;
        return this;
    }
    
    public CreateDeveloperMetadataResponse getCreateDeveloperMetadata() {
        return this.createDeveloperMetadata;
    }
    
    public Response setCreateDeveloperMetadata(final CreateDeveloperMetadataResponse createDeveloperMetadata) {
        this.createDeveloperMetadata = createDeveloperMetadata;
        return this;
    }
    
    public DeleteConditionalFormatRuleResponse getDeleteConditionalFormatRule() {
        return this.deleteConditionalFormatRule;
    }
    
    public Response setDeleteConditionalFormatRule(final DeleteConditionalFormatRuleResponse deleteConditionalFormatRule) {
        this.deleteConditionalFormatRule = deleteConditionalFormatRule;
        return this;
    }
    
    public DeleteDeveloperMetadataResponse getDeleteDeveloperMetadata() {
        return this.deleteDeveloperMetadata;
    }
    
    public Response setDeleteDeveloperMetadata(final DeleteDeveloperMetadataResponse deleteDeveloperMetadata) {
        this.deleteDeveloperMetadata = deleteDeveloperMetadata;
        return this;
    }
    
    public DeleteDimensionGroupResponse getDeleteDimensionGroup() {
        return this.deleteDimensionGroup;
    }
    
    public Response setDeleteDimensionGroup(final DeleteDimensionGroupResponse deleteDimensionGroup) {
        this.deleteDimensionGroup = deleteDimensionGroup;
        return this;
    }
    
    public DuplicateFilterViewResponse getDuplicateFilterView() {
        return this.duplicateFilterView;
    }
    
    public Response setDuplicateFilterView(final DuplicateFilterViewResponse duplicateFilterView) {
        this.duplicateFilterView = duplicateFilterView;
        return this;
    }
    
    public DuplicateSheetResponse getDuplicateSheet() {
        return this.duplicateSheet;
    }
    
    public Response setDuplicateSheet(final DuplicateSheetResponse duplicateSheet) {
        this.duplicateSheet = duplicateSheet;
        return this;
    }
    
    public FindReplaceResponse getFindReplace() {
        return this.findReplace;
    }
    
    public Response setFindReplace(final FindReplaceResponse findReplace) {
        this.findReplace = findReplace;
        return this;
    }
    
    public UpdateConditionalFormatRuleResponse getUpdateConditionalFormatRule() {
        return this.updateConditionalFormatRule;
    }
    
    public Response setUpdateConditionalFormatRule(final UpdateConditionalFormatRuleResponse updateConditionalFormatRule) {
        this.updateConditionalFormatRule = updateConditionalFormatRule;
        return this;
    }
    
    public UpdateDeveloperMetadataResponse getUpdateDeveloperMetadata() {
        return this.updateDeveloperMetadata;
    }
    
    public Response setUpdateDeveloperMetadata(final UpdateDeveloperMetadataResponse updateDeveloperMetadata) {
        this.updateDeveloperMetadata = updateDeveloperMetadata;
        return this;
    }
    
    public UpdateEmbeddedObjectPositionResponse getUpdateEmbeddedObjectPosition() {
        return this.updateEmbeddedObjectPosition;
    }
    
    public Response setUpdateEmbeddedObjectPosition(final UpdateEmbeddedObjectPositionResponse updateEmbeddedObjectPosition) {
        this.updateEmbeddedObjectPosition = updateEmbeddedObjectPosition;
        return this;
    }
    
    @Override
    public Response set(final String a1, final Object a2) {
        return (Response)super.set(a1, a2);
    }
    
    @Override
    public Response clone() {
        return (Response)super.clone();
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
