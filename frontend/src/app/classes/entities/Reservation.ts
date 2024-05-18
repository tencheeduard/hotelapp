import { ReservationId } from "../ids/ReservationId";
import { Account } from "./Account";

export class Reservation
{
    id : ReservationId
    endDate : Date
    account : Account
    cancelled : boolean

    constructor(id: ReservationId, endDate : Date, account : Account, cancelled : boolean)
    {
        this.id = id;
        this.endDate = endDate;
        this.account = account;
        this.cancelled = cancelled;
    }
}