import { Account } from "./Account"
import { Room } from "./Room"

export class Review
{
    reviewId : number

    room : Room

    account : Account

    rating : number

    body : string

    date : Date

    isPublic : boolean

    constructor(reviewId : number, room : Room, account : Account, rating : number, body : string, date : Date, isPublic : boolean)
    {
        this.reviewId = reviewId;
        this.room = room;
        this.account = account;
        this.rating = rating;
        this.body = body;
        this.date = date;
        this.isPublic = isPublic;
    }
}