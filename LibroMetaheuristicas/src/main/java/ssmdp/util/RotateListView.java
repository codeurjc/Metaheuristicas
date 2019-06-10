package ssmdp.util;

import java.util.Iterator;
import java.util.List;

public class RotateListView<T> implements Iterable<T> {

    private class IteratorRotate implements Iterator<T> {

        private int initialCounter;
        private boolean hasNext = true;

        public IteratorRotate() {
            initialCounter = actualCounter;
        }

        public boolean hasNext() {
            return hasNext;
        }

        public T next() {
            T elem = list.get(actualCounter);
            actualCounter = (actualCounter + 1) % list.size();
            hasNext = (actualCounter != initialCounter);
            return elem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private List<? extends T> list;
    int actualCounter;

    public RotateListView(List<? extends T> list) {
        this.list = list;
    }

    public Iterator<T> iterator() {
        return new IteratorRotate();
    }



}
