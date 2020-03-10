var scrollToPageTop = function () {
    $('html, body').animate({scrollTop: 0}, 800);
}

// on load
var latestURL = null;
$(window).on('load', function(){
    latestURL = $(window).attr('location').href;
});


var startPageRedirectionBlockUI = function () {
    $('.ui-blockui').hide();
    $('.ui-blockui-content').hide();
    try {
        PF('pageRedirectBlock').show();
        $('.pageRedirectBlock').css({height:$(document).height()});
        $(PF('pageRedirectBlock').jqId).css({top: ($(window).height()/2 - $(PF('pageRedirectBlock').jqId).height()/2 )});
        scrollToPageTop();
    }
    catch(e){}
};

// onbeforeunload
$(window).on('beforeunload', function(){

    setTimeout(function () {
        startPageRedirectionBlockUI();
        console.log("blocklaniyor")
    }, 100);

});

var removePageRedirectBlock = function(){
    console.log("girdi");
    setTimeout(function () {
        try {PF('pageRedirectBlock').hide();
        }
        catch(e){}

    },3000);
}