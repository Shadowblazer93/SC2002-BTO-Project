package printer;

import java.util.List;
import java.util.Map;

/**
 * Generic interface for printing of various objects in system
 * @param <K> Key type used in map structures
 * @param <T> Type of element to print
 */
public interface Print<K, T> {

    /**
     * Print contents of a list
     * @param list List of elements to print
     */
    void printList(List<T> list);

    /**
     * Print contents of a map
     * @param map Map of key-value pairs to print
     */
    void printMap(Map<K, T> map);

    /**
     * Print contents of map where each key maps to a list of elements
     * @param mapList Map of key to list of values to print
     */
    void printMapList(Map<K, List<T>> mapList);
}
