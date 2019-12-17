String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

var filterTitle;
var resizeSelectOneMenu;
var initWidthValues = [];
$(document).ready(function () {
    try {

        filterTitle = function () {
            $("table tr td button").parent().css("overflow", "inherit");
            //Datatable Columns
            $('td[role="gridcell"],li[class*="ui-selectonemenu-item"], label[class*="ui-selectonemenu-label"], li[class*="ui-selectcheckboxmenu-token"], input[type="text"]:disabled ').each(function (filterCount, filterValue) {

                if ($(filterValue).hasClass('ui-inputfield')) {
                    $(filterValue).attr('title', $(filterValue).val().trim().replaceAll('\n','').replaceAll('  ',''));
                    $(filterValue).css('pointer-events', 'all');
                }

                if ($(this).text() !== "" && $(this).text() !== " ") {
                    if($(filterValue).attr('title') == null && $(filterValue).hasClass('ui-selectonemenu-item')){
                        $(filterValue).attr('title', $(filterValue).text().trim().replaceAll('\n','').replaceAll('  ',''));
                    }
                }

                if ($(this).text() !== "" && $(this).has("span").length) {
                    var $textOfGridcell = $(filterValue).text().replaceAll('\n','').replaceAll('  ','');
                    var $columnOfGridcell = $(filterValue).children().text().trim().replaceAll('\n','').replaceAll('  ','');
                    var _foundTitle = $textOfGridcell.substr($columnOfGridcell.length, $textOfGridcell.length);
                    $(filterValue).attr('title', _foundTitle.trim().replaceAll('\n','').replaceAll('  ',''));

                    if ($(filterValue).hasClass('ui-selectcheckboxmenu-token')) {
                        $(filterValue).attr('title', $(this).find('.ui-selectcheckboxmenu-token-label').text().trim().replaceAll('\n','').replaceAll('  ',''));
                    }
                }

            });

        }


        resizeSelectOneMenu = function () {

            $('div[class*=ui-selectonemenu-panel], div[class*=ui-selectcheckboxmenu-panel]').each(function () {
                try {
                    var containerId = $(this).attr('id');
                    if (containerId != undefined && containerId != null) {
                        containerId = containerId.replaceAll(':', '\\:');
                        containerId = containerId.replaceAll('_panel', '');
                    }

                    $('#' + containerId).css('min-width', 'unset');

                    $(this).css('min-width', 'unset');
                    $(this).removeClass('customSelectOneMenu');
                    $(this).addClass('customSelectOneMenu');

                    if ($('#' + containerId).parent().hasClass('ui-column-customfilter')) {
                        // in datatable
                        // $('#' + containerId).parent().addClass('customSelectOneMenuChanged');
                        // $(this).width($('#' + containerId).parent().width());
                    }
                    else {
                        // in form row

                        $('#' + containerId).on('change', function () {
                            filterTitle();
                        });

                        var _maxWidth = $('#' + containerId).width();
                        if(_maxWidth > 0){
                            // additional check if new width is much bigger than the inital value
                            if(initWidthValues[containerId] != undefined && initWidthValues[containerId] < _maxWidth) {
                                var breaker = 0;
                                while(initWidthValues[containerId] < _maxWidth) {
                                    _maxWidth -= _maxWidth /4;
                                    breaker++;
                                    if(breaker > 100){
                                        break;
                                    }
                                }

                            }

                            //selectOneMenu icin otomatik maxWidth eklediginden kaldirdik
                            $('#' + containerId).find('.ui-selectonemenu-label').css('max-width', _maxWidth);

                            if(initWidthValues[containerId] == undefined) {
                                initWidthValues[containerId] = _maxWidth;
                            }
                        }

                    }

                }
                catch (e) {
                }

            });

        }


        filterTitle();
        resizeSelectOneMenu();

    }
    catch (e) {
    }

    $(window).resize(function () {
        resizeSelectOneMenu();
    });


    $("body").on('DOMSubtreeModified', 'ul[class*="ui-selectcheckboxmenu-multiple-container"]', function () {
        filterTitle();
        resizeSelectOneMenu();
    });


});

