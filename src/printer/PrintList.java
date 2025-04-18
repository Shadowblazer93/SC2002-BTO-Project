package printer;

import java.util.List;

/**
 * Generic interface for printing list of various objects in system
 * @param <T> Type of element to print
 */
public interface PrintList<T> {

    /**
     * Print contents of a list
     * @param list List of elements to print
     */
    void printList(List<T> list);

}
