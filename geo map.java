<apex:component >

<apex:attribute name="addressList" type="String[]" description="array of addresses"/>

<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<script type="text/javascript"> 

$(document).ready(function() {

    var myOptions = {
        center: new google.maps.LatLng(54, -2),
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
        
    };

  var map = new google.maps.Map(document.getElementById("map"), myOptions);
  
   

    var addressArray = new Array();
    <apex:repeat value="{!addressList}" var="address">
        addressArray.push("{!address}");
    </apex:repeat>

    var geocoder = new google.maps.Geocoder();

    var markerBounds = new google.maps.LatLngBounds();

    for (var i = 0; i < addressArray.length; i++) {
        geocoder.geocode( { 'address': addressArray[i]}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                var marker = new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location
                });
                markerBounds.extend(results[0].geometry.location);
                map.fitBounds(markerBounds);
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
    }
    
   
});
</script>
<style>
    #map {
      font-family: Arial;
      font-size:12px;
      line-height:normal !important;
      height:500px;
      width: 800px;
      background:transparent;
    }
</style>

<div id="map"></div> 

</apex:component>

///////////////////////////////////////


<apex:page standardController="contact" extensions="MapCtrller2">
   <c:mapcomponent addressList="{!listOfAddresses}"/>
 </apex:page>
 
 //////////////////////////
 public class MapCtrller2 {

    public List<String> listOfAddresses {get; set;}
    public MapCtrller2(ApexPages.StandardController controller) {
    
     listOfAddresses = new List<String>();
    list<contact> conlist=[select id , Additonal_Practice_11__c ,Additional_Practice_22__c, Additional_Practice_33__c,Serv_add__c from contact where id=:((contact)controller.getRecord()).id];
    
    for(contact c : conlist)
    {
       listOfAddresses.add( c.Serv_add__c );
       listOfAddresses.add( c.Additonal_Practice_11__c );
       listOfAddresses.add( c.Additional_Practice_22__c);
       listOfAddresses.add( c.Additional_Practice_33__c);
    }
     
    
    }


}