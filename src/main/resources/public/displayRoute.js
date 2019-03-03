var map;
var cascadiaFault;
var cityCircles;
var markers;
function initMap() {
    var mapOptions = {
        zoom: 6,
        center: new google.maps.LatLng(41.85, -87.65)
    };
    cascadiaFault=null;
    cityCircles=[];
    markers = [];
    map = new google.maps.Map(document.getElementById('map'),
        mapOptions);
    document.getElementById('submit').addEventListener('click', function() {
        calculateAndDisplayRoute();
    });
}
function makeBubbleHandler(circle, contentString,latlng){
    var iwOptions = {
        content: contentString,
        position: latlng
    }
    google.maps.event.addListener(circle, 'click', function(){
        infoBubble.setOptions(iwOptions);
        infoBubble.open(map);
    });
}

function clearMap() {
    for(var i in markers){
        markers[i].setMap(null);
    }
    markers=[];
    for(var i in cityCircles) {
        cityCircles[i].setMap(null);
    }
    cityCircles = [];
    if(cascadiaFault!=undefined){
        cascadiaFault.setMap(null);
    }
}
function calculateAndDisplayRoute() {
    var source = document.getElementById("start").value;
    var destination = document.getElementById("end").value;
    $.getJSON("/getdirections?sname="+source+"&dname="+destination, function(response, status){
        if (status === 'success') {

            clearMap();

            var LatLngArray =[];
            var cityCircle;
            var marker1,marker2;
            var infoBubble = new google.maps.InfoWindow();

            for(var i=0; i<response.length; i++){
                var latlng = new google.maps.LatLng(response[i].latitude, response[i].longitude);
                LatLngArray.push(latlng);

                var contentString = response[i].name + " - "+"Temperature: " + response[i].minTemp+"/"+response[i].maxTemp+" 'K ("+response[i].weatherDescription+")";
                var circleOptions = {
                    strokeColor: '#DD4B3E',
                    strokeOpacity: 0.1,
                    strokeWeight: 2,
                    fillColor: '#DD4B3E',
                    fillOpacity: 0.8,
                    map: map,
                    center: latlng,
                    radius: 7000,
                    draggable:false
                };
                cityCircle = new google.maps.Circle(circleOptions);
                cityCircles.push(cityCircle);
                makeBubbleHandler(cityCircle, contentString,latlng);
            }

            cascadiaFault = new google.maps.Polyline({
                path: LatLngArray,
                strokeColor: '#4A89F3',
                strokeOpacity: 1.0,
                strokeWeight: 7
            });
            cascadiaFault.setMap(map);

            marker1 = new google.maps.Marker({
                position: LatLngArray[0],
                map: map,
                title: source
            });
            marker1.setMap(map);
            markers.push(marker1);

            marker2 = new google.maps.Marker({
                position: LatLngArray[response.length-1],
                map: map,
                title: destination
            });
            marker2.setMap(map);
            markers.push(marker2);
        }
        else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}
