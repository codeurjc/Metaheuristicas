package ssmdp.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomList<T> implements Iterable<T> {

    static class RandomIterator<T> implements Iterator<T> {

        List<? extends T> list;
        static Random r = new Random(System.currentTimeMillis());

        public RandomIterator(Collection<? extends T> list){
            this.list = new ArrayList<T>(list);
        }

        public boolean hasNext() {
            return !list.isEmpty();
        }

        public T next() {
            return list.remove(r.nextInt(list.size()));
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    Collection<? extends T> list;

    public RandomList(Collection<? extends T> list){
        this.list = list;
    }

    public Iterator<T> iterator() {
        return new RandomIterator<T>(list);
    }

    public static <T> RandomList<T> create(Collection<T> collection) {
        return new RandomList<T>(collection);
    }

}
