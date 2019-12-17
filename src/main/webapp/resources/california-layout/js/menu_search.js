var clickExternally;
$(document).ready(function () {
    try {
        var listItemsSpan = $('.menuContainer:not(ui-menuitem-submenu) li span');
        var listItems = $('.menuContainer:not(.ui-menuitem-submenu) li');
        var array = [];
        var arrayAsStr = [];
        for (var i = 0; i < listItems.length; i++) {
            var searchList = {
                label: listItemsSpan[i].innerText,
                value: listItemsSpan[i].innerText,
                url: listItems[i].firstChild.href,
                firstChild: listItems[i].firstChild,
                parentElement: listItems[i].parentElement,
                parentNode: listItems[i].parentNode,
                node: listItems[i],
            };
            array.push(searchList);
            arrayAsStr.push(listItemsSpan[i].innerText);
        }

        $('input#menuSearch').puiautocomplete({
            completeSource: array,
            content: function(data) {
                return '<span style="font-size:14px">' + data.value.label + '</span>';
            },
            select: function(event, ui) {

                    var _value = ui.data('value');
                    $('#menuSearch').val(_value.label.label);

                    if (_value != null && _value.node != null && _value.node.className != null) {
                        if (_value.node.className.trim() != "") {
                            if (_value.node.className.includes("active")) {
                            }
                            else {
                                if (_value.parentNode.parentElement.className == "menuContainer") {
                                    _value.firstChild.click();
                                }
                                else {
                                    if (_value.parentNode.parentElement.className.includes("active")) {
                                        _value.firstChild.click();
                                    }
                                    else {
                                        _value.parentNode.parentElement.firstChild.click();
                                        _value.firstChild.click();
                                    }
                                }
                            }
                        }
                        else {
                            $(window).attr('location', _value.url);
                        }
                    }

            }

        });

        clickExternally = function (targetTitle) {
            $('li[role="menuitem"]').find('span').each(function () {
                if($(this).text() == targetTitle){

                    findParent($(this));

                    return;
                }
            });
        }

        var findParent = function(target){
            if($(target).parent().attr("href")){
                if(!$(target).parent().parent().hasClass('active-menuitem')) {
                    $(target).parent().click();
                }
                findParent($(target).parent());
            }
            else if($(target).parent().parent().attr("role") == 'menu'){
                    if(!$(target).parent().parent().prev().parent().hasClass('active-menuitem')) {
                        $(target).parent().parent().prev().click();
                    }
                    findParent($(target).parent().parent().prev());
            }

        }

    }
    catch (e) {

    }
});

