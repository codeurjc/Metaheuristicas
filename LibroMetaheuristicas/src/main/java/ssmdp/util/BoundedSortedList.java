package ssmdp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BoundedSortedList<T> {

    private static final Comparator inverseComparator = new InverseComparator();
    protected List<T> list;
    protected int maxSize;

    public BoundedSortedList(int numElems) {
        super();
        this.list = new ArrayList<T>(numElems + 1);
        this.maxSize = numElems;
    }

    public void addAll(Iterable<T> iterable) {
        for (T t : iterable) {
            this.add(t);
        }
    }

    public List<T> getList() {
        return list;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize < list.size()) {
            list.subList(maxSize, list.size()).clear();
        }
        this.maxSize = maxSize;
    }

    public T getBiggest() {
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public void clear(int from, int to) {
        list.subList(from, to).clear();
    }

    public int add(T elem) {

        int index = Collections.binarySearch(list, elem,
                (Comparator<T>) inverseComparator);

        int position;
        if (index < 0) {
            position = -index - 1;
        } else {
            if(list.get(index).equals(elem)){
                return -1;
            } else {
                position = index;
            }
        }

        int added = -1;
        if (list.size() < maxSize || position != maxSize) {
            list.add(position, elem);
            added = position;
        }

        if (list.size() > maxSize) {
            list.remove(list.size() - 1);
        }

        return added;
    }

    public void retain(int num){
        list.subList(num, list.size()).clear();
    }

    public String toString(){
        return list.toString();
    }
}
