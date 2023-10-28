import java.util.ArrayList;

public class AkbarWorks<T extends Comparable> {
    private ArrayList<T> allItems;
    private ArrayList<T> deletedItems;

    public AkbarWorks() {
        allItems = new ArrayList<>();
        deletedItems = new ArrayList<>();
    }

    public void add(T item) {
        allItems.add(item);
    }

    public T getMin() throws IllegalStateException {
        if (allItems.isEmpty())
            throw new IllegalStateException();
        T min = allItems.get(0);
        for (T item : allItems) {
            if (min.compareTo(item) > 0)
                min = item;
        }
        allItems.remove(min);
        deletedItems.add(min);
        return min;
    }

    public T getLast(boolean remove) throws IllegalStateException {
        if (allItems.isEmpty())
            throw new IllegalStateException();
        T item = allItems.get(allItems.size() - 1);
        if (remove) {
            allItems.remove(item);
            deletedItems.add(item);
        }
        return item;
    }

    public T getFirst(boolean remove) throws IllegalStateException {
        if (allItems.isEmpty())
            throw new IllegalStateException();
        T item = allItems.get(0);
        if (remove) {
            allItems.remove(item);
            deletedItems.add(item);
        }
        return item;
    }

    public Comparable[] getLess(T element, boolean remove) {
        ArrayList<T> less = new ArrayList<>();
        for (T item : allItems) {
            if (element.compareTo(item) > 0)
                less.add(item);
        }
        if (remove) {
            allItems.removeAll(less);
            deletedItems.addAll(less);
        }
        Comparable[] array = new Comparable[less.size()];
        less.toArray(array);
        return array;
    }

    public Comparable[] getRecentlyRemoved(int n) {
        ArrayList<T> removed = new ArrayList<>();
        for (int i = deletedItems.size() - 1; i >= 0 && i >= deletedItems.size() - n; i--) {
            removed.add(deletedItems.get(i));
        }
        Comparable[] array = new Comparable[removed.size()];
        removed.toArray(array);
        return array;

    }


}
