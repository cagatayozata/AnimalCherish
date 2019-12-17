var markersDefinition = [];
var markers = [];
var currentInfoWindow;
var map;

function zoomToLocation(targetMarker) {
    google.maps.event.trigger(targetMarker, 'click');
}

function smoothZoom(map, max, cnt) {
    var z;
    if (cnt >= max) {
        return;
    }
    else {
        z = google.maps.event.addListener(map, 'zoom_changed', function (event) {
            google.maps.event.removeListener(z);
            smoothZoom(map, max, cnt + 1);
        });
        setTimeout(function () {
            map.setZoom(cnt);
        }, 80);
    }
}


function initMap() {

    markersDefinition.push({
        position: {lat: 35.2113814, lng: 33.3480373},
        title: 'Gümrük ve Rüsümat Dairesi',
        address: 'Maliye Bakanlığı Hasane Ilgaz Sokak, A Blok Kat: 2 Köşklüçiftlik 99010 Lefkoşa',
        zoom: 12
    });
    markersDefinition.push({position: {lat: 35.23478, lng: 33.15892}, title: 'Lefkoşa Gümrük Şube Amirlikleri', address: 'Organize Sanayi Bölgesi 99010 Lefkoşa', zoom: 10});
    markersDefinition.push({position: {lat: 35.2262004, lng: 33.67399}, title: 'Gazimağusa Gümrük Şube Amirlikleri', address: 'Mağusa Serbest Liman Bölgesi 99450 Gazimağusa', zoom: 12});
    markersDefinition.push({position: {lat: 35.2262743, lng: 33.3491173}, title: 'Girne Gümrük Şube Amirliği', address: 'Girne Turizm Limanı (Yeni Liman) 99300 Girne', zoom: 11});
    markersDefinition.push({position: {lat: 35.1580872, lng: 33.4989015}, title: 'Ercan Gümrük Şube Amirliği', address: 'Ercan Havalimanı 99100 Lefkoşa', zoom: 10});
    markersDefinition.push({position: {lat: 35.1445595, lng: 32.7996698}, title: 'Gemikonağı Gümrük Şube Amirliği', address: 'Soli Mahallesi Ecevit cad. No:2 Gemikonağı 99770 Lefke', zoom: 12});
    markersDefinition.push({position: {lat: 35.0431537, lng: 33.1105858}, title: 'Akyar Gümrük Kapısı', address: 'Gazimağusa', zoom: 9});
    markersDefinition.push({position: {lat: 35.0427592, lng: 32.8587685}, title: 'Beyarmudu Gümrük Kapısı', address: 'Gazimağusa', zoom: 12});
    markersDefinition.push({position: {lat: 35.1999673, lng: 33.1296481}, title: 'Kermiya / (Metehan) Gümrük Kapısı', address: 'Lefkoşa', zoom: 12});
    markersDefinition.push({position: {lat: 35.1750364, lng: 33.3576438}, title: 'Lokmacı Gümrük Kapısı', address: 'Lefkoşa', zoom: 11});
    markersDefinition.push({position: {lat: 35.1808707, lng: 33.3309358}, title: 'Ledra Palace Gümrük Kapısı', address: 'Lefkoşa', zoom: 11});
    markersDefinition.push({position: {lat: 35.1450739, lng: 32.6698777}, title: 'Yukarı Bostancı Gümrük Kapısı', address: 'Güzelyurt', zoom: 13});
    markersDefinition.push({position: {lat: 35.1872921, lng: 32.5593486}, title: 'Yeşilırmak Gümrük Kapısı', address: 'Lefke', zoom: 12});

    map = new google.maps.Map(document.getElementById('map'), {
        center: markersDefinition[0].position,
        zoom: 8
    });


    var _tmpMarker;
    for (var t = 0; t < markersDefinition.length; t++) {

        markers[t] = new google.maps.Marker({
            map: map,
            position: markersDefinition[t].position,
            zoom: markersDefinition[t].zoom,
            title: '<b>' + markersDefinition[t].title + '</b>',
            address: markersDefinition[t].address
        });


        markers[t].addListener('click', function () {
            smoothZoom(map, this.zoom, map.getZoom());
            map.panTo(this.getPosition());

            var _infowindow = new google.maps.InfoWindow({
                content: this.title + '<br>' + this.address
            });

            if (currentInfoWindow != undefined && currentInfoWindow != null) {
                currentInfoWindow.close();
            }
            _infowindow.open(map, this);
            currentInfoWindow = _infowindow;
        });


    }


}