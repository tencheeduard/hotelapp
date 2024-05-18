import { Unit } from "../enums/Unit"

export class Distance
{
    value : number
    unit : Unit

    constructor(value: number, unit : Unit)
    {
        this.value = value;
        this.unit = unit;
    }
}