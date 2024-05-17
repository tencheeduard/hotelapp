package com.tencheeduard.hotelapp.enums;

public enum Unit {

    METERS {
        @Override
        public Double toMetersFactor() { return 1.0; }
        @Override
        public Double fromMetersFactor() { return 1.0; }
        @Override
        public String toString() { return "m"; }
    },
    KILOMETERS {
        @Override
        public Double toMetersFactor() { return 1000.0; }
        @Override
        public Double fromMetersFactor() { return 0.001; }
        @Override
        public String toString() { return "km"; }
    },
    FEET {
        @Override
        public Double toMetersFactor() { return 0.3048; }
        @Override
        public Double fromMetersFactor() { return 3.2808399; }
        @Override
        public String toString() { return "ft"; }
    },
    MILES {
        @Override
        public Double toMetersFactor() { return 1609.344; }
        @Override
        public Double fromMetersFactor() { return 0.000621371192; }
        @Override
        public String toString() { return "mi"; }
    };

    public abstract Double toMetersFactor();
    public abstract Double fromMetersFactor();
}
