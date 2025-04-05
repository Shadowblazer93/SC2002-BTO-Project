package enums;

import java.util.HashMap;
import java.util.Map;

public enum FlatType {
    TWO_ROOM(2),
    THREE_ROOM(3);

    private final int numRooms;
    private static final Map<Integer, FlatType> map = new HashMap<>();

    static {
        for (FlatType flatType : FlatType.values()) {
            map.put(flatType.numRooms, flatType);
        }
    }

    FlatType(int numRooms) {
        this.numRooms = numRooms;
    }

    public int getNumRooms() {
        return this.numRooms;
    }

    public static FlatType getFlatType(int n) {
        FlatType flatType = map.get(n);
        return flatType;
    }
}