package printer;

import java.util.Map;

/**
 * Generic interface for printing map of various objects in system
 * @param <K> Key type used in map structures
 * @param <T> Type of element to print
 */
public interface PrintMap<K, T> {
    /**
     * Print contents of a map
     * @param map Map of key-value pairs to print
     */
    void printMap(Map<K, T> map);
}
