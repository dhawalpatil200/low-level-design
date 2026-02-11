package org.example.VendingMachine;


import org.example.VendingMachine.entities.Rack;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    private final Map<String, Rack> racks = new HashMap<>();

    public void addRack(Rack rack) {
        racks.put(rack.getRackCode(), rack);
    }

    public Rack getRack(String rackCode) {
        return racks.get(rackCode);
    }

    public void dispenseProduct(Rack rack) {
        rack.decrement();
    }
}
