package com.tencheeduard.hotelapp.classes;

import com.tencheeduard.hotelapp.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Distance {

    Double value;
    Unit unit;

    // returns a new object that will have the new unit
    public Distance toUnit(Unit unit)
    {
        return new Distance((value * this.unit.toMetersFactor()) * unit.fromMetersFactor(), unit);
    }

    @Override
    public String toString()
    {
        return new DecimalFormat("#.00").format(value) + unit.toString();
    }

    public String toFullString()
    {
        return this.value + unit.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if(other instanceof Distance otherDistance)
            if(otherDistance.unit == this.unit)
                return otherDistance.value.equals(this.value);
            else
                return otherDistance.toUnit(this.unit).value.equals(this.value);

        return super.equals(other);
    }

    public Double compare(Distance otherDistance)
    {
        return subtract(otherDistance).value;
    }

    public Distance subtract(Distance otherDistance)
    {
        if(otherDistance.unit == this.unit)
            return new Distance(this.value - otherDistance.value, this.unit);

        return new Distance(this.value - otherDistance.toUnit(this.unit).value, this.unit);
    }

    public Distance add(Distance otherDistance)
    {
        if(otherDistance.unit == this.unit)
            return new Distance(this.value + otherDistance.value, this.unit);

        return new Distance(this.value + otherDistance.toUnit(this.unit).value, this.unit);
    }

    public Distance Absolute()
    {
        if(this.value < 0)
            return new Distance(-this.value, this.unit);

        return new Distance(this.value, this.unit);
    }
}
