function tamEkranStyleDegistir(xhr, status, args) {

    $('.layout-main').removeClass('beyanTamEkranLayoutMain');
    $('.layout-topbar').removeClass('beyanTamEkranLayoutTopBar');
    $('.layout-sidebar').removeClass('beyanTamEkranSideBar');

    if(args.isFullScreen){

        $('.layout-main').addClass('beyanTamEkranLayoutMain');
        $('.layout-topbar').addClass('beyanTamEkranLayoutTopBar');
        $('.layout-sidebar').addClass('beyanTamEkranSideBar');

    }
    else {

        $('.layout-main').addClass('beyanNormalEkranLayoutMain');
        $('.layout-topbar').addClass('beyanNormalEkranLayoutTopBar');
        $('.layout-sidebar').addClass('beyanNormalEkranSideBar');
    }
}