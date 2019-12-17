function clearTableFilter(tableId) {
    try {
        var tableWidgetVar = getTableWidgetVarById(tableId);
        if (tableWidgetVar != undefined && tableWidgetVar != null) {
            tableWidgetVar.clearFilters();
            var rangePickerIds = getTableDateRangePickerButtonsId(tableId);
            for (var rangePicker in rangePickerIds) {
                $('#' + rangePickerIds[rangePicker].replace(/:/g, '\\:')).click();
            }
        }
    } catch (e) {
        console.log(e);
    }
}

function getTableWidgetVarById(tableId) {
    try {
        for (var propertyName in PrimeFaces.widgets) {
            if (PrimeFaces.widgets[propertyName] != undefined && PrimeFaces.widgets[propertyName] != null && PrimeFaces.widgets[propertyName].hasOwnProperty('id') && PrimeFaces.widgets[propertyName].id.endsWith(tableId)) {
                return PrimeFaces.widgets[propertyName];
            }
        }
    } catch (e) {
        console.log(e);
    }
}

function getTableDateRangePickerButtonsId(tableId) {
    var rangePicker = [];
    try {
        for (var propertyName in PrimeFaces.widgets) {
            if (PrimeFaces.widgets[propertyName] != undefined && PrimeFaces.widgets[propertyName] != null && PrimeFaces.widgets[propertyName].hasOwnProperty('id') && PrimeFaces.widgets[propertyName].id.includes(':' + tableId + ':') && PrimeFaces.widgets[propertyName].id.endsWith(':clearFilterHandler')) {
                rangePicker.push(PrimeFaces.widgets[propertyName].id);
            }
        }
    } catch (e) {
        console.log(e);
    }
    return rangePicker;
}