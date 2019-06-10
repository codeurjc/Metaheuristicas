package ssmdp.util;

import java.util.Iterator;

public class WeightedIterator<E> implements Iterator<E> {

    Iterator<Weighted<E>> it;

    public WeightedIterator(Iterator<Weighted<E>> it) {
        this.it = it;
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public E next() {
        return it.next().getElement();
    }

    public void remove() {
        it.remove();
    }
}
