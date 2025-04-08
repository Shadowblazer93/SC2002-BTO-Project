package boundary;

import java.util.List;
import java.util.Map;

public interface Print<T> {
    void printList(List<T> list);
    void printMap(Map<String, T> map);
    void printMapList(Map<String, List<T>> mapList);
}
