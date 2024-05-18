import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hotel } from '../classes/entities/Hotel';
import { Room } from '../classes/entities/Room';
import { Distance } from '../classes/Distance';
import { Observable } from 'rxjs';
import { Review } from '../classes/entities/Review';
import { Reservation } from '../classes/entities/Reservation';

@Injectable({
  providedIn: 'root'
})
export class BackendApiService {

  constructor(private http: HttpClient) { }

  static dateToString(date : Date) : string
  {
    return date.getFullYear().toString() + "-" + date.getMonth().toString() + "-" + date.getDate().toString(); 
  }

  getHotelsInRadius(lat : number, long : number, radius : number)
  {
    return this.http.get<Hotel[]>('http://localhost:8080/hotels/getHotelsInRadius?lat=' + lat + "&long=" + long + "&radius=" + radius);
  }

  getDistanceToHotel(hotelId : number, lat : number, long : number)
  {
    return this.http.get<Distance>('http://localhost:8080/hotels/getDistanceTo?hotel=' + hotelId + "&lat=" + lat + "&long=" + long);
  }

  getAvailableRooms(hotelId : number, from : Date, to : Date)
  {
    return this.http.get<Room[]>('http://localhost:8080/hotels/getRooms?hotel=' + hotelId + "&from=" + BackendApiService.dateToString(from) + "&to=" + BackendApiService.dateToString(to));
  }

  getAllRooms(hotelId : number)
  {
    return this.http.get<Room[]>('http://localhost:8080/hotels/getAllRooms?hotel=' + hotelId);
  }

  getRating(hotelId : number)
  {
    return this.http.get<number>('http://localhost:8080/hotels/getRating?hotel=' + hotelId);
  }

  getReviews(hotelId : number)
  {
    return this.http.get<Review[]>('http://localhost:8080/hotels/getReviews?hotel=' + hotelId);
  }

  getReservations(username : string)
  {
    return this.http.get<Reservation[]>('http://localhost:8080/reservations/getReservations?username=' + username);
  }

  makeReservation(hotelId : number, roomNumber : number, start : Date, end : Date, username : string)
  {
    class Payload
    {
      hotelId : number
      roomNumber : number
      start : string
      end : string
      username : string

      constructor(hotelId : number, roomNumber : number, start : Date, end : Date, username : string)
      {
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.start = BackendApiService.dateToString(start);
        this.end = BackendApiService.dateToString(end);
        this.username = username
      }
    }

    let body = new Payload(hotelId, roomNumber, start, end, username);

    return this.http.post('http://localhost:8080/reservations/makeReservation', body);
  }

  cancelReservation(hotelId : number, roomNumber : number, start : Date)
  {
    class Payload
    {
      hotelId : number
      roomNumber : number
      start : string
      username : string

      constructor(hotelId : number, roomNumber : number, start : Date, username : string)
      {
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.start = BackendApiService.dateToString(start);
        this.username = username;
      }
    }

    let body = new Payload(hotelId, roomNumber, start, "User");

    return this.http.post('http://localhost:8080/reservations/cancelReservation', body);
  }

  leaveReview(username : string, roomNumber : number, hotelId : number, body : string, rating : number, isPublic : boolean)
  {
    class Payload
    {
      username : string
      roomNumber : number
      hotelId : number
      body : string
      rating : number
      isPublic : boolean

      constructor(username : string, roomNumber : number, hotelId : number, body : string, rating : number, isPublic : boolean)
      {
        this.username = username;
        this.roomNumber = roomNumber;
        this.hotelId = hotelId;
        this.body = body;
        this.rating = rating;
        this.isPublic = isPublic;
      }
    }

    let payload = new Payload(username, roomNumber, hotelId, body, rating, isPublic);

    return this.http.post('http://localhost:8080/reviews/leaveReview', payload);
  }
  
}
