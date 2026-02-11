package org.example.ShippingLockerSystem;


import org.example.ShippingLockerSystem.entities.*;
import org.example.ShippingLockerSystem.entities.Package;

import java.math.BigDecimal;
import java.util.List;

public class ShippingLockerSystemDemo {
    public static void main(String[] args) {
        Site site = new Site(generateLockers());
        Package smallPackage = new Package("P1", new PackageSize(new BigDecimal("5.00"), new BigDecimal("5.00"), new BigDecimal("5.00")));
        Package largePackage = new Package("P2", new PackageSize(new BigDecimal("40.00"), new BigDecimal("10.00"), new BigDecimal("20.00")));

        String code = site.assignPackage(smallPackage);
        site.pickup(code);
        try {
            site.assignPackage(largePackage);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static List<Locker> generateLockers() {
        return List.of(new Locker(Size.SMALL), new Locker(Size.MEDIUM), new Locker(Size.LARGE));
    }
}