import { Room } from "../entities/Room";

export class ReservationId
{
    room : Room
    startDate : Date

    constructor(room: Room, startDate? : Date, dateString? : string)
    {
        this.room = room;
        if(startDate)
            this.startDate = startDate;
        if(dateString)
            this.startDate = new Date(Date.parse(dateString));
    }
    
}