package com.google.api.services.sheets.v4.model;

import com.google.api.client.json.*;
import com.google.api.client.util.*;

public final class Request extends GenericJson
{
    @Key
    private AddBandingRequest addBanding;
    @Key
    private AddChartRequest addChart;
    @Key
    private AddConditionalFormatRuleRequest addConditionalFormatRule;
    @Key
    private AddDimensionGroupRequest addDimensionGroup;
    @Key
    private AddFilterViewRequest addFilterView;
    @Key
    private AddNamedRangeRequest addNamedRange;
    @Key
    private AddProtectedRangeRequest addProtectedRange;
    @Key
    private AddSheetRequest addSheet;
    @Key
    private AppendCellsRequest appendCells;
    @Key
    private AppendDimensionRequest appendDimension;
    @Key
    private AutoFillRequest autoFill;
    @Key
    private AutoResizeDimensionsRequest autoResizeDimensions;
    @Key
    private ClearBasicFilterRequest clearBasicFilter;
    @Key
    private CopyPasteRequest copyPaste;
    @Key
    private CreateDeveloperMetadataRequest createDeveloperMetadata;
    @Key
    private CutPasteRequest cutPaste;
    @Key
    private DeleteBandingRequest deleteBanding;
    @Key
    private DeleteConditionalFormatRuleRequest deleteConditionalFormatRule;
    @Key
    private DeleteDeveloperMetadataRequest deleteDeveloperMetadata;
    @Key
    private DeleteDimensionRequest deleteDimension;
    @Key
    private DeleteDimensionGroupRequest deleteDimensionGroup;
    @Key
    private DeleteEmbeddedObjectRequest deleteEmbeddedObject;
    @Key
    private DeleteFilterViewRequest deleteFilterView;
    @Key
    private DeleteNamedRangeRequest deleteNamedRange;
    @Key
    private DeleteProtectedRangeRequest deleteProtectedRange;
    @Key
    private DeleteRangeRequest deleteRange;
    @Key
    private DeleteSheetRequest deleteSheet;
    @Key
    private DuplicateFilterViewRequest duplicateFilterView;
    @Key
    private DuplicateSheetRequest duplicateSheet;
    @Key
    private FindReplaceRequest findReplace;
    @Key
    private InsertDimensionRequest insertDimension;
    @Key
    private InsertRangeRequest insertRange;
    @Key
    private MergeCellsRequest mergeCells;
    @Key
    private MoveDimensionRequest moveDimension;
    @Key
    private PasteDataRequest pasteData;
    @Key
    private RandomizeRangeRequest randomizeRange;
    @Key
    private RepeatCellRequest repeatCell;
    @Key
    private SetBasicFilterRequest setBasicFilter;
    @Key
    private SetDataValidationRequest setDataValidation;
    @Key
    private SortRangeRequest sortRange;
    @Key
    private TextToColumnsRequest textToColumns;
    @Key
    private UnmergeCellsRequest unmergeCells;
    @Key
    private UpdateBandingRequest updateBanding;
    @Key
    private UpdateBordersRequest updateBorders;
    @Key
    private UpdateCellsRequest updateCells;
    @Key
    private UpdateChartSpecRequest updateChartSpec;
    @Key
    private UpdateConditionalFormatRuleRequest updateConditionalFormatRule;
    @Key
    private UpdateDeveloperMetadataRequest updateDeveloperMetadata;
    @Key
    private UpdateDimensionGroupRequest updateDimensionGroup;
    @Key
    private UpdateDimensionPropertiesRequest updateDimensionProperties;
    @Key
    private UpdateEmbeddedObjectPositionRequest updateEmbeddedObjectPosition;
    @Key
    private UpdateFilterViewRequest updateFilterView;
    @Key
    private UpdateNamedRangeRequest updateNamedRange;
    @Key
    private UpdateProtectedRangeRequest updateProtectedRange;
    @Key
    private UpdateSheetPropertiesRequest updateSheetProperties;
    @Key
    private UpdateSpreadsheetPropertiesRequest updateSpreadsheetProperties;
    
    public Request() {
        super();
    }
    
    public AddBandingRequest getAddBanding() {
        return this.addBanding;
    }
    
    public Request setAddBanding(final AddBandingRequest addBanding) {
        this.addBanding = addBanding;
        return this;
    }
    
    public AddChartRequest getAddChart() {
        return this.addChart;
    }
    
    public Request setAddChart(final AddChartRequest addChart) {
        this.addChart = addChart;
        return this;
    }
    
    public AddConditionalFormatRuleRequest getAddConditionalFormatRule() {
        return this.addConditionalFormatRule;
    }
    
    public Request setAddConditionalFormatRule(final AddConditionalFormatRuleRequest addConditionalFormatRule) {
        this.addConditionalFormatRule = addConditionalFormatRule;
        return this;
    }
    
    public AddDimensionGroupRequest getAddDimensionGroup() {
        return this.addDimensionGroup;
    }
    
    public Request setAddDimensionGroup(final AddDimensionGroupRequest addDimensionGroup) {
        this.addDimensionGroup = addDimensionGroup;
        return this;
    }
    
    public AddFilterViewRequest getAddFilterView() {
        return this.addFilterView;
    }
    
    public Request setAddFilterView(final AddFilterViewRequest addFilterView) {
        this.addFilterView = addFilterView;
        return this;
    }
    
    public AddNamedRangeRequest getAddNamedRange() {
        return this.addNamedRange;
    }
    
    public Request setAddNamedRange(final AddNamedRangeRequest addNamedRange) {
        this.addNamedRange = addNamedRange;
        return this;
    }
    
    public AddProtectedRangeRequest getAddProtectedRange() {
        return this.addProtectedRange;
    }
    
    public Request setAddProtectedRange(final AddProtectedRangeRequest addProtectedRange) {
        this.addProtectedRange = addProtectedRange;
        return this;
    }
    
    public AddSheetRequest getAddSheet() {
        return this.addSheet;
    }
    
    public Request setAddSheet(final AddSheetRequest addSheet) {
        this.addSheet = addSheet;
        return this;
    }
    
    public AppendCellsRequest getAppendCells() {
        return this.appendCells;
    }
    
    public Request setAppendCells(final AppendCellsRequest appendCells) {
        this.appendCells = appendCells;
        return this;
    }
    
    public AppendDimensionRequest getAppendDimension() {
        return this.appendDimension;
    }
    
    public Request setAppendDimension(final AppendDimensionRequest appendDimension) {
        this.appendDimension = appendDimension;
        return this;
    }
    
    public AutoFillRequest getAutoFill() {
        return this.autoFill;
    }
    
    public Request setAutoFill(final AutoFillRequest autoFill) {
        this.autoFill = autoFill;
        return this;
    }
    
    public AutoResizeDimensionsRequest getAutoResizeDimensions() {
        return this.autoResizeDimensions;
    }
    
    public Request setAutoResizeDimensions(final AutoResizeDimensionsRequest autoResizeDimensions) {
        this.autoResizeDimensions = autoResizeDimensions;
        return this;
    }
    
    public ClearBasicFilterRequest getClearBasicFilter() {
        return this.clearBasicFilter;
    }
    
    public Request setClearBasicFilter(final ClearBasicFilterRequest clearBasicFilter) {
        this.clearBasicFilter = clearBasicFilter;
        return this;
    }
    
    public CopyPasteRequest getCopyPaste() {
        return this.copyPaste;
    }
    
    public Request setCopyPaste(final CopyPasteRequest copyPaste) {
        this.copyPaste = copyPaste;
        return this;
    }
    
    public CreateDeveloperMetadataRequest getCreateDeveloperMetadata() {
        return this.createDeveloperMetadata;
    }
    
    public Request setCreateDeveloperMetadata(final CreateDeveloperMetadataRequest createDeveloperMetadata) {
        this.createDeveloperMetadata = createDeveloperMetadata;
        return this;
    }
    
    public CutPasteRequest getCutPaste() {
        return this.cutPaste;
    }
    
    public Request setCutPaste(final CutPasteRequest cutPaste) {
        this.cutPaste = cutPaste;
        return this;
    }
    
    public DeleteBandingRequest getDeleteBanding() {
        return this.deleteBanding;
    }
    
    public Request setDeleteBanding(final DeleteBandingRequest deleteBanding) {
        this.deleteBanding = deleteBanding;
        return this;
    }
    
    public DeleteConditionalFormatRuleRequest getDeleteConditionalFormatRule() {
        return this.deleteConditionalFormatRule;
    }
    
    public Request setDeleteConditionalFormatRule(final DeleteConditionalFormatRuleRequest deleteConditionalFormatRule) {
        this.deleteConditionalFormatRule = deleteConditionalFormatRule;
        return this;
    }
    
    public DeleteDeveloperMetadataRequest getDeleteDeveloperMetadata() {
        return this.deleteDeveloperMetadata;
    }
    
    public Request setDeleteDeveloperMetadata(final DeleteDeveloperMetadataRequest deleteDeveloperMetadata) {
        this.deleteDeveloperMetadata = deleteDeveloperMetadata;
        return this;
    }
    
    public DeleteDimensionRequest getDeleteDimension() {
        return this.deleteDimension;
    }
    
    public Request setDeleteDimension(final DeleteDimensionRequest deleteDimension) {
        this.deleteDimension = deleteDimension;
        return this;
    }
    
    public DeleteDimensionGroupRequest getDeleteDimensionGroup() {
        return this.deleteDimensionGroup;
    }
    
    public Request setDeleteDimensionGroup(final DeleteDimensionGroupRequest deleteDimensionGroup) {
        this.deleteDimensionGroup = deleteDimensionGroup;
        return this;
    }
    
    public DeleteEmbeddedObjectRequest getDeleteEmbeddedObject() {
        return this.deleteEmbeddedObject;
    }
    
    public Request setDeleteEmbeddedObject(final DeleteEmbeddedObjectRequest deleteEmbeddedObject) {
        this.deleteEmbeddedObject = deleteEmbeddedObject;
        return this;
    }
    
    public DeleteFilterViewRequest getDeleteFilterView() {
        return this.deleteFilterView;
    }
    
    public Request setDeleteFilterView(final DeleteFilterViewRequest deleteFilterView) {
        this.deleteFilterView = deleteFilterView;
        return this;
    }
    
    public DeleteNamedRangeRequest getDeleteNamedRange() {
        return this.deleteNamedRange;
    }
    
    public Request setDeleteNamedRange(final DeleteNamedRangeRequest deleteNamedRange) {
        this.deleteNamedRange = deleteNamedRange;
        return this;
    }
    
    public DeleteProtectedRangeRequest getDeleteProtectedRange() {
        return this.deleteProtectedRange;
    }
    
    public Request setDeleteProtectedRange(final DeleteProtectedRangeRequest deleteProtectedRange) {
        this.deleteProtectedRange = deleteProtectedRange;
        return this;
    }
    
    public DeleteRangeRequest getDeleteRange() {
        return this.deleteRange;
    }
    
    public Request setDeleteRange(final DeleteRangeRequest deleteRange) {
        this.deleteRange = deleteRange;
        return this;
    }
    
    public DeleteSheetRequest getDeleteSheet() {
        return this.deleteSheet;
    }
    
    public Request setDeleteSheet(final DeleteSheetRequest deleteSheet) {
        this.deleteSheet = deleteSheet;
        return this;
    }
    
    public DuplicateFilterViewRequest getDuplicateFilterView() {
        return this.duplicateFilterView;
    }
    
    public Request setDuplicateFilterView(final DuplicateFilterViewRequest duplicateFilterView) {
        this.duplicateFilterView = duplicateFilterView;
        return this;
    }
    
    public DuplicateSheetRequest getDuplicateSheet() {
        return this.duplicateSheet;
    }
    
    public Request setDuplicateSheet(final DuplicateSheetRequest duplicateSheet) {
        this.duplicateSheet = duplicateSheet;
        return this;
    }
    
    public FindReplaceRequest getFindReplace() {
        return this.findReplace;
    }
    
    public Request setFindReplace(final FindReplaceRequest findReplace) {
        this.findReplace = findReplace;
        return this;
    }
    
    public InsertDimensionRequest getInsertDimension() {
        return this.insertDimension;
    }
    
    public Request setInsertDimension(final InsertDimensionRequest insertDimension) {
        this.insertDimension = insertDimension;
        return this;
    }
    
    public InsertRangeRequest getInsertRange() {
        return this.insertRange;
    }
    
    public Request setInsertRange(final InsertRangeRequest insertRange) {
        this.insertRange = insertRange;
        return this;
    }
    
    public MergeCellsRequest getMergeCells() {
        return this.mergeCells;
    }
    
    public Request setMergeCells(final MergeCellsRequest mergeCells) {
        this.mergeCells = mergeCells;
        return this;
    }
    
    public MoveDimensionRequest getMoveDimension() {
        return this.moveDimension;
    }
    
    public Request setMoveDimension(final MoveDimensionRequest moveDimension) {
        this.moveDimension = moveDimension;
        return this;
    }
    
    public PasteDataRequest getPasteData() {
        return this.pasteData;
    }
    
    public Request setPasteData(final PasteDataRequest pasteData) {
        this.pasteData = pasteData;
        return this;
    }
    
    public RandomizeRangeRequest getRandomizeRange() {
        return this.randomizeRange;
    }
    
    public Request setRandomizeRange(final RandomizeRangeRequest randomizeRange) {
        this.randomizeRange = randomizeRange;
        return this;
    }
    
    public RepeatCellRequest getRepeatCell() {
        return this.repeatCell;
    }
    
    public Request setRepeatCell(final RepeatCellRequest repeatCell) {
        this.repeatCell = repeatCell;
        return this;
    }
    
    public SetBasicFilterRequest getSetBasicFilter() {
        return this.setBasicFilter;
    }
    
    public Request setSetBasicFilter(final SetBasicFilterRequest setBasicFilter) {
        this.setBasicFilter = setBasicFilter;
        return this;
    }
    
    public SetDataValidationRequest getSetDataValidation() {
        return this.setDataValidation;
    }
    
    public Request setSetDataValidation(final SetDataValidationRequest setDataValidation) {
        this.setDataValidation = setDataValidation;
        return this;
    }
    
    public SortRangeRequest getSortRange() {
        return this.sortRange;
    }
    
    public Request setSortRange(final SortRangeRequest sortRange) {
        this.sortRange = sortRange;
        return this;
    }
    
    public TextToColumnsRequest getTextToColumns() {
        return this.textToColumns;
    }
    
    public Request setTextToColumns(final TextToColumnsRequest textToColumns) {
        this.textToColumns = textToColumns;
        return this;
    }
    
    public UnmergeCellsRequest getUnmergeCells() {
        return this.unmergeCells;
    }
    
    public Request setUnmergeCells(final UnmergeCellsRequest unmergeCells) {
        this.unmergeCells = unmergeCells;
        return this;
    }
    
    public UpdateBandingRequest getUpdateBanding() {
        return this.updateBanding;
    }
    
    public Request setUpdateBanding(final UpdateBandingRequest updateBanding) {
        this.updateBanding = updateBanding;
        return this;
    }
    
    public UpdateBordersRequest getUpdateBorders() {
        return this.updateBorders;
    }
    
    public Request setUpdateBorders(final UpdateBordersRequest updateBorders) {
        this.updateBorders = updateBorders;
        return this;
    }
    
    public UpdateCellsRequest getUpdateCells() {
        return this.updateCells;
    }
    
    public Request setUpdateCells(final UpdateCellsRequest updateCells) {
        this.updateCells = updateCells;
        return this;
    }
    
    public UpdateChartSpecRequest getUpdateChartSpec() {
        return this.updateChartSpec;
    }
    
    public Request setUpdateChartSpec(final UpdateChartSpecRequest updateChartSpec) {
        this.updateChartSpec = updateChartSpec;
        return this;
    }
    
    public UpdateConditionalFormatRuleRequest getUpdateConditionalFormatRule() {
        return this.updateConditionalFormatRule;
    }
    
    public Request setUpdateConditionalFormatRule(final UpdateConditionalFormatRuleRequest updateConditionalFormatRule) {
        this.updateConditionalFormatRule = updateConditionalFormatRule;
        return this;
    }
    
    public UpdateDeveloperMetadataRequest getUpdateDeveloperMetadata() {
        return this.updateDeveloperMetadata;
    }
    
    public Request setUpdateDeveloperMetadata(final UpdateDeveloperMetadataRequest updateDeveloperMetadata) {
        this.updateDeveloperMetadata = updateDeveloperMetadata;
        return this;
    }
    
    public UpdateDimensionGroupRequest getUpdateDimensionGroup() {
        return this.updateDimensionGroup;
    }
    
    public Request setUpdateDimensionGroup(final UpdateDimensionGroupRequest updateDimensionGroup) {
        this.updateDimensionGroup = updateDimensionGroup;
        return this;
    }
    
    public UpdateDimensionPropertiesRequest getUpdateDimensionProperties() {
        return this.updateDimensionProperties;
    }
    
    public Request setUpdateDimensionProperties(final UpdateDimensionPropertiesRequest updateDimensionProperties) {
        this.updateDimensionProperties = updateDimensionProperties;
        return this;
    }
    
    public UpdateEmbeddedObjectPositionRequest getUpdateEmbeddedObjectPosition() {
        return this.updateEmbeddedObjectPosition;
    }
    
    public Request setUpdateEmbeddedObjectPosition(final UpdateEmbeddedObjectPositionRequest updateEmbeddedObjectPosition) {
        this.updateEmbeddedObjectPosition = updateEmbeddedObjectPosition;
        return this;
    }
    
    public UpdateFilterViewRequest getUpdateFilterView() {
        return this.updateFilterView;
    }
    
    public Request setUpdateFilterView(final UpdateFilterViewRequest updateFilterView) {
        this.updateFilterView = updateFilterView;
        return this;
    }
    
    public UpdateNamedRangeRequest getUpdateNamedRange() {
        return this.updateNamedRange;
    }
    
    public Request setUpdateNamedRange(final UpdateNamedRangeRequest updateNamedRange) {
        this.updateNamedRange = updateNamedRange;
        return this;
    }
    
    public UpdateProtectedRangeRequest getUpdateProtectedRange() {
        return this.updateProtectedRange;
    }
    
    public Request setUpdateProtectedRange(final UpdateProtectedRangeRequest updateProtectedRange) {
        this.updateProtectedRange = updateProtectedRange;
        return this;
    }
    
    public UpdateSheetPropertiesRequest getUpdateSheetProperties() {
        return this.updateSheetProperties;
    }
    
    public Request setUpdateSheetProperties(final UpdateSheetPropertiesRequest updateSheetProperties) {
        this.updateSheetProperties = updateSheetProperties;
        return this;
    }
    
    public UpdateSpreadsheetPropertiesRequest getUpdateSpreadsheetProperties() {
        return this.updateSpreadsheetProperties;
    }
    
    public Request setUpdateSpreadsheetProperties(final UpdateSpreadsheetPropertiesRequest updateSpreadsheetProperties) {
        this.updateSpreadsheetProperties = updateSpreadsheetProperties;
        return this;
    }
    
    @Override
    public Request set(final String a1, final Object a2) {
        return (Request)super.set(a1, a2);
    }
    
    @Override
    public Request clone() {
        return (Request)super.clone();
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
