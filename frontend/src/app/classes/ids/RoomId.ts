import { Hotel } from "../entities/Hotel";

export class RoomId
{
    hotel : Hotel
    roomNumber : number

    constructor(hotel : Hotel, roomNumber : number)
    {
        this.hotel = hotel;
        this.roomNumber = roomNumber;
    }
}