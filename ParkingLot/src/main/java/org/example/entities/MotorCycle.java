package org.example.entities;

public class MotorCycle implements Vehicle{
    private final String licencePlateNumber;

    public MotorCycle(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    @Override
    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }
}
