package org.example.entities;

public class Locker {
    private final Size size;
    private Package aPackage;

    public Locker(Size size) {
        this.size = size;
    }

    public boolean isOccupied() {
        return aPackage != null;
    }

    public void assignPackage(Package aPackage) {
        if(this.aPackage != null) {
            throw new RuntimeException("Already occupied");
        }
        this.aPackage = aPackage;
    }

    public void markFree() {
        this.aPackage = null;
    }

    public Size getSize() {
        return size;
    }
}
