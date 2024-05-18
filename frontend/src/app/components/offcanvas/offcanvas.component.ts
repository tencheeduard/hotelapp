import { Component, inject, TemplateRef, ViewChild } from '@angular/core';

import { NgbDatepickerModule, NgbNav, NgbNavContent, NgbNavItem, NgbNavLink, NgbNavModule, NgbNavOutlet, NgbOffcanvas, NgbRatingConfig, NgbRatingModule, OffcanvasDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Hotel } from '../../classes/entities/Hotel';
import { Room } from '../../classes/entities/Room';
import { Distance } from '../../classes/Distance';
import { Unit } from '../../enums/Unit';
import { BackendApiService } from '../../services/backend-api.service';
import { AppComponent } from '../../app.component';
import { CommonModule } from '@angular/common';

export class HotelData
{
  hotel : Hotel
  distance : string
  roomCount : number
  rating : number

  constructor(hotel : Hotel, distance? : Distance, roomCount? : number, rating? : number)
  {
    this.hotel = hotel;
    if(roomCount)
      this.roomCount = roomCount;
    if(rating)
      this.rating = rating;
    if(distance)
    {
      let result = distance.value.toFixed(2);
      switch(distance.unit.toString())
      {
        case "METERS":
          result=result+"m";
          break;
        case "KILOMETERS":
          result=result+"km";
          break;
        case "FEET":
          result=result+"ft";
          break;
        case "MILES":
          result= result+"mi";
          break;
      }

      this.distance = result;
    }
  }
}

@Component({
  selector: 'app-offcanvas',
  standalone: true,
  imports: [NgbRatingModule, CommonModule, NgbNav, NgbNavItem, NgbNavLink, NgbNavContent, NgbNavOutlet, NgbNavModule ],
  templateUrl: './offcanvas.component.html',
  styleUrl: './offcanvas.component.scss',
  providers: [NgbRatingConfig]
})
export class OffCanvasComponent {
	private offcanvasService = inject(NgbOffcanvas);

  constructor(private api : BackendApiService, config: NgbRatingConfig)
  {
    this.hotels = []
    config.max = 5;
		config.readonly = true;
  }

  @ViewChild('content') content: HTMLElement;
  @ViewChild('body') body: HTMLElement;

  title : string

  hotels : HotelData[]

  app = AppComponent.app;

	open(hotels : Hotel[]) : void
  {
    this.populateWithHotels(hotels);
		this.offcanvasService.open(this.content, { ariaLabelledBy: 'offcanvas-basic-title' }).result.then();
	}

  populateWithHotels(hotels: Hotel[]) : void
  {
    this.hotels = [];
    let distances : Distance[]
    let roomCounts : number[]
    let ratings : number[]
    distances = []
    roomCounts = []
    ratings = []
    hotels.forEach((hotel)=>this.api.getRating(hotel.id).subscribe(
      (response)=>
        {
          ratings.push(response);
        } 
      )
    )
    hotels.forEach((hotel)=>this.api.getAllRooms(hotel.id).subscribe(
      (response)=>
        {
          roomCounts.push(response.length);
        } 
      )
    )
    hotels.forEach((hotel)=>this.api.getDistanceToHotel(hotel.id, AppComponent.lat, AppComponent.long).subscribe(
      (response)=>
        {
          distances.push(response);
          this.trySaveHotels(hotels, distances, roomCounts, ratings);
        }
    ));
    
  }
  
  trySaveHotels(hotels : Hotel[], distances : Distance[], roomCounts : number[], ratings : number[])
  {
    if(hotels.length == distances.length && roomCounts.length == hotels.length && ratings.length == hotels.length)
    {
      for(let i = 0; i < hotels.length; i++)
      {
        this.hotels.push(new HotelData(hotels[i], distances[i], roomCounts[i], ratings[i]));
      }
    }
  }
}
