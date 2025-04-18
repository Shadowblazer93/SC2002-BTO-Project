package printer;

import java.util.List;
import java.util.Map;

/**
 * Generic interface for printing map of lists of various objects in system
 * @param <K> Key type used in map structures
 * @param <T> Type of element to print
 */
public interface PrintMapList<K, T> {

    /**
     * Print contents of map where each key maps to a list of elements
     * @param mapList Map of key to list of values to print
     */
    void printMapList(Map<K, List<T>> mapList);

}
