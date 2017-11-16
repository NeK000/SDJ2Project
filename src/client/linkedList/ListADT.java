package client.linkedList;

public interface ListADT<T> extends Iterable<T> {

    void add(int index, T element) throws IllegalStateException, IndexOutOfBoundsException, IllegalArgumentException;

    void add(T element) throws IllegalStateException, IllegalArgumentException;

    boolean contains(T element);

    T get(int index) throws IndexOutOfBoundsException;

    int indexOf(T element);

    boolean isEmpty();

    boolean isFull();

    T remove(int index) throws IndexOutOfBoundsException;

    T remove(T element) throws IllegalStateException;

    void set(int index, T element) throws IndexOutOfBoundsException, IllegalArgumentException;

    int size();

}
