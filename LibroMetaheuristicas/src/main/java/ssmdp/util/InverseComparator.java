package ssmdp.util;

import java.util.Comparator;

public class InverseComparator<T> implements Comparator<T> {

    public int compare(T obj1, T obj2) {
        return -((Comparable<T>)obj1).compareTo(obj2);
    }

}
