package org.example.SnakeAndLadder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Integer, Integer> entitiesMap;
    private final int numCells;

    public Board(List<BoardEntity> entities, int size) {
        this.entitiesMap = new HashMap<>();
        this.numCells = size * size;
        for(BoardEntity entity: entities) {
            entitiesMap.put(entity.getStart(), entity.getEnd());
        }
    }

    public int getUpdatedPosition(int position) {
        return entitiesMap.getOrDefault(position, position);
    }

    public int getSize() {
        return numCells;
    }
}
