import { Hotel } from "./entities/Hotel"

export class HotelMarker
{
    marker : google.maps.marker.AdvancedMarkerElement
    hotel : Hotel

    constructor(marker : google.maps.marker.AdvancedMarkerElement, hotel : Hotel)
    {
        this.marker = marker;
        this.hotel = hotel;
    }
}