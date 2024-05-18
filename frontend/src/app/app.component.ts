import { Component, inject, OnInit, ViewChild, TemplateRef  } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MapComponent } from './components/map/map.component';
import { HotelData, OffCanvasComponent } from './components/offcanvas/offcanvas.component';
import { BackendApiService } from './services/backend-api.service';
import { Hotel } from './classes/entities/Hotel';
import { Point } from './classes/Point';
import { NgbModal, NgbDatepickerModule, ModalDismissReasons, NgbCalendar, NgbDateStruct, NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule, JsonPipe } from '@angular/common';
import { Room } from './classes/entities/Room';
import { from } from 'rxjs';
import { Review } from './classes/entities/Review';
import { RoomId } from './classes/ids/RoomId';
import { Reservation } from './classes/entities/Reservation';
import { ReservationId } from './classes/ids/ReservationId';

enum ModalMode
  {
    HOTEL = 0,
    MY_RESERVATIONS
  }

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MapComponent, OffCanvasComponent, NgbDatepickerModule, FormsModule, JsonPipe, CommonModule ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'frontend';

  // look, all of this code is awful, i realize that. i was planning to stay up all night to finish the project, and i ended up falling asleep.
  // it is now about 2 hours before the deadline, so i can't be taking my time with things. 

  static app : AppComponent

  modalService = inject(NgbModal);
  calendar = inject(NgbCalendar);

  static lat : number
  static long : number

  today = inject(NgbCalendar).getToday();

	model: NgbDateStruct;

  selectedHotel : HotelData
  availableRooms : Room[]
  reviews : Review[]
  selectedRoom : Room

  radius: number

  madeReservation : boolean

  modalMode : ModalMode

	hoveredDate: NgbDate | null = null;
	fromDate: NgbDate = this.calendar.getToday();
	toDate: NgbDate | null = this.calendar.getNext(this.fromDate, 'd', 10);

  myReservations : Reservation[]

  constructor(private api : BackendApiService)
  {
    AppComponent.app = this;
    this.modalMode = ModalMode.HOTEL;
  }

  @ViewChild(OffCanvasComponent) sidebar:OffCanvasComponent;
  @ViewChild(MapComponent) map:MapComponent;
  @ViewChild(RouterOutlet) router:RouterOutlet;
  @ViewChild('modalcontent') templateRef:TemplateRef<any>;


  ngOnInit(): void {

    navigator.geolocation.getCurrentPosition(position=>
      {
        AppComponent.lat=position.coords.latitude;
        AppComponent.long=position.coords.longitude;

        this.map.initMap();
      }
    );
  }

  onclickbutton()
  {
    this.api.getHotelsInRadius(AppComponent.lat, AppComponent.long, this.radius).subscribe(
      (response) => 
      {
        this.map.clearHotelMarkers();
        for(let i = 0; i < response.length; i++)
        {
          this.map.createHotelMarker(response[i], this);
        }
        this.openOffCanvas(response);
      }
    );
  }

  openOffCanvas(hotels : Hotel[])
  {
    this.sidebar.open(hotels);
  }

  openModal(hotel? : HotelData) {


    if(hotel)
      this.modalMode = ModalMode.HOTEL

    if(this.modalMode == ModalMode.HOTEL && hotel)
      {
        this.selectHotel(hotel);
        this.getReviews();
      }
    this.modalService.open(this.templateRef, { size: 'xl' });
	}

  closeModal()
  {
    this.availableRooms=[]
    this.reviews = []
    this.selectedRoom.id.roomNumber = -1
    this.madeReservation = false;
  }

  selectHotel(hotel : HotelData)
  {
    this.selectedHotel = hotel;
  }

  selectRoom(room : Room)
  {
    this.selectedRoom = room;
    console.log(this.selectedRoom);
  }

  checkRooms()
  {
    if(this.fromDate != null && this.toDate != null && this.selectedHotel != null)
    {
        this.api.getAvailableRooms(this.selectedHotel.hotel.id, this.NgbDateToDate(this.fromDate), this.NgbDateToDate(this.toDate)).subscribe(
          (response=>this.availableRooms = response)
      )
    }
  }

  NgbDateToDate(date : NgbDate | null)
  {
    if(date)
      return new Date(date.year, date.month, date.day);

    // this shouldnt happen
    return new Date();
  }

  getReviews()
  {
    if(this.fromDate != null && this.toDate != null && this.selectedHotel != null)
    {
        this.api.getReviews(this.selectedHotel.hotel.id).subscribe(
          (response=>this.reviews = response)
      )
    }
  }

	onDateSelection(date: NgbDate) {
		if (!this.fromDate && !this.toDate) {
			this.fromDate = date;
		} else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
			this.toDate = date;
		} else {
			this.toDate = null;
			this.fromDate = date;
		}

    this.checkRooms();
	}

	isHovered(date: NgbDate) {
		return (
			this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate)
		);
	}

	isInside(date: NgbDate) {
		return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
	}

	isRange(date: NgbDate) {
		return (
			date.equals(this.fromDate) ||
			(this.toDate && date.equals(this.toDate)) ||
			this.isInside(date) ||
			this.isHovered(date)
		);
	}

  makeReservation()
  {
    this.api.makeReservation(this.selectedHotel.hotel.id, this.selectedRoom.id.roomNumber, this.NgbDateToDate(this.fromDate), this.NgbDateToDate(this.toDate), "User").subscribe(
      ()=>this.madeReservation=true
    )
  }

  cancelReservation(reservation : Reservation)
  {
    console.log(reservation.id.startDate);
    this.api.cancelReservation(reservation.id.room.id.hotel.id, reservation.id.room.id.roomNumber, reservation.id.startDate).subscribe()
    {
      ()=>this.myReservations.filter((x)=>x.id!==reservation.id);
    };
  }

  openMyReservations()
  {
    this.modalMode = ModalMode.MY_RESERVATIONS;
    this.openModal(); 
    this.api.getReservations("User").subscribe(
      (response)=>
        {
          this.myReservations = []
          for(let i of response)
            {
              // i dont know why this is necessary, but just copying the objects doesnt work
              this.myReservations.push(new Reservation(new ReservationId(i.id.room, i.id.startDate, i.id.startDate.toString()), new Date(Date.parse(i.endDate.toString())), i.account, i.cancelled));
            }
        }
    )
  }
}
