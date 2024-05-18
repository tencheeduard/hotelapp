import { RoomId } from "../ids/RoomId";

export class Room
{
    id : RoomId
    type : number
    price : number
    isAvailable : boolean

    constructor(id : RoomId, type: number, price : number, isAvailable : boolean)
    {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }
}