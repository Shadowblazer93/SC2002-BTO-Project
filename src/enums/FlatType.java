package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing different types of HDB flats based on the number of rooms
 */
public enum FlatType {

    /**
     * Represents a 2-room flat
     */
    TWO_ROOM(2),

    /**
     * Represents a 3-room flat
     */
    THREE_ROOM(3);

    private final int numRooms;

    /**
     * Map of number of rooms to corresponding {@link FlatType}
     */
    private static final Map<Integer, FlatType> map = new HashMap<>();

    // Static block to populate map for quick lookup
    static {
        for (FlatType flatType : FlatType.values()) {
            map.put(flatType.numRooms, flatType);
        }
    }

    /**
     * Constructor to associate number of rooms to flat type
     * @param numRooms Number of rooms in the flat type
     */
    FlatType(int numRooms) {
        this.numRooms = numRooms;
    }

    /**
     * Gets number of rooms for this flat type
     * @return Number of rooms
     */
    public int getNumRooms() {
        return this.numRooms;
    }

    /**
     * Retrieves the {@link FlatType} corresponding to the given number of rooms
     * @param n Number of rooms
     * @return Corresponding {@link FlatType} or {@code null} if not found
     */
    public static FlatType getFlatType(int n) {
        FlatType flatType = map.get(n);
        return flatType;
    }
}