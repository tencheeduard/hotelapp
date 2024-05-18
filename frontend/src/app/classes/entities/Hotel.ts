import { Point } from "../Point"

export class Hotel
{
    id : number
    name : string
    coordinates : Point

    constructor(id : number, name : string, coordinates : Point)
    {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
    }
}