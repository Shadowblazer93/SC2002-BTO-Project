package printer;

import java.util.List;
import java.util.Map;

public interface Print<K, T> {
    void printList(List<T> list);
    void printMap(Map<K, T> map);
    void printMapList(Map<K, List<T>> mapList);
}
