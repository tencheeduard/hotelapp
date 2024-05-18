import { Component, AfterViewInit, ViewChild, ElementRef, OnInit } from '@angular/core';
import { GoogleMap, GoogleMapsModule, MapAdvancedMarker, MapMarker } from '@angular/google-maps';
import { HotelMarker } from '../../classes/HotelMarker';
import { Hotel } from '../../classes/entities/Hotel';
import { AppComponent } from '../../app.component';
import { HotelData } from '../offcanvas/offcanvas.component';
import { Distance } from '../../classes/Distance';

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [GoogleMapsModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.scss'
})
export class MapComponent {

  @ViewChild('mapContainer', { static: false }) mapContainer!: ElementRef;

  

  map: google.maps.Map

  // marker at own position
  positionMarker : google.maps.marker.AdvancedMarkerElement

  // list of all the created markers
  markers : HotelMarker[]
  

  constructor() { }

  async initMap(): Promise<void> {
    const { Map } = await google.maps.importLibrary("maps") as google.maps.MapsLibrary;
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker") as google.maps.MarkerLibrary;
    this.markers = [];


    this.map = new Map(document.getElementById("map") as HTMLElement, 
      {
        center: { lat: AppComponent.lat, lng: AppComponent.long },
        zoom: 14,
        streetViewControl: false,
        disableDefaultUI: true,
        mapId: "0",
        styles: [
          {
            featureType: "poi",
            stylers: [ { visibility: "off" } ]
          }
        ]
      }
    );

    this.positionMarker = new AdvancedMarkerElement(
      {
        position: { lat: AppComponent.lat, lng: AppComponent.long },
        map: this.map,
        content: new google.maps.marker.PinElement(
          {
            glyph:"You",
            glyphColor:'white',
            background:'light-blue'
          }
        ).element
      }
    );
  }

  clearHotelMarkers()
  {
    for(let i = 0; i < this.markers.length; i++)
      this.markers[i].marker.remove();


    this.markers = [];
  }

  createHotelMarker(hotel : Hotel, app : AppComponent): void
  {
    const marker = new HotelMarker(
      new google.maps.marker.AdvancedMarkerElement(
        {
          position: { lat: hotel.coordinates.latitude, lng: hotel.coordinates.longitude},
          map: this.map,
          gmpClickable: true,
          content: new google.maps.marker.PinElement(
            {
              scale:1.3
            }
          ).element
        }
      ),
      hotel
    );

    marker.marker.addListener('click', ()=>{app.openModal(new HotelData(marker.hotel))});

    this.markers.push(marker);
  }


}
