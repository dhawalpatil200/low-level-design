package org.example.ShippingLockerSystem.entities;

public class Package {
    private final String id;
    private final PackageSize packageSize;

    public Package(String id, PackageSize packageSize) {
        this.id = id;
        this.packageSize = packageSize;
    }

    public String getId() {
        return id;
    }

    public PackageSize getPackageSize() {
        return packageSize;
    }
}
