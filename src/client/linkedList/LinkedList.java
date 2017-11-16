package client.linkedList;

import java.util.Iterator;

public class LinkedList<T> implements ListADT<T> {

    private LinearNode<T> head;
    private int count;

    public LinkedList() {
        count = 0;
        head = null;
    }

    @Override
    public void add(int index, T element) throws IllegalStateException, IllegalArgumentException, IndexOutOfBoundsException {
        if (element == null) {
            throw new IllegalArgumentException("No nulls allowed");
        }
        if (index > count) {
            throw new IndexOutOfBoundsException("Index is bigger than the size of the list");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index should be bigger than -1");
        }
        LinearNode<T> t = new LinearNode<>(element);
        if (head == null) {
            head = t;
            count += 1;
        } else if (index == 0) {
            LinearNode<T> temp = head;
            head = t;
            t.setNext(temp);
            count += 1;
        } else {
            LinearNode<T> cur = head;
            for (int i = 0; i < index - 1; i++) {
                cur = cur.getNext();
            }
            LinearNode<T> oldNext = cur.getNext();

            // last element
            if (oldNext == null) {
                cur.setNext(t);
            }

            if (oldNext != null && oldNext.getNext() != null) {
                oldNext.setNext(oldNext.getNext());
                t.setNext(oldNext);
                cur.setNext(t);
            } else {
                t.setNext(oldNext);
                cur.setNext(t);
            }
            count += 1;

        }
    }

    @Override
    public void add(T element) throws IllegalStateException, IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("No nulls allowed");
        }
        LinearNode<T> t = new LinearNode<>(element);
        if (head == null) {
            head = t;
            count += 1;
        } else {
            LinearNode<T> cur = head;
            for (int i = 0; i < count - 1; i++) {
                cur = cur.getNext();
            }
            LinearNode<T> oldNext = cur.getNext();

            if (oldNext == null) {
                cur.setNext(t);
            }
            count += 1;

        }

    }

    @Override
    public void set(int index, T element) throws IndexOutOfBoundsException, IllegalArgumentException {

        if (element == null) {
            throw new IllegalArgumentException("No nulls allowed");
        }
        if (index > count - 1) {
            throw new IndexOutOfBoundsException("Index is bigger than the size of the list");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index should be bigger than -1");
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.getNext();
        }
        cur.setElement(element);

    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index > count - 1) {
            throw new IndexOutOfBoundsException("Index is bigger than the size of the list");
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index should be bigger than -1");
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.getNext();
        }
        return cur.getElement();
    }

    @Override
    public T remove(int index) throws IllegalStateException {
        if (index > count - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        LinearNode<T> cur = head;

        if (index == 0) {
            // remove head
            LinearNode<T> temp = head;
            if (head.getNext() != null) {
                head = head.getNext();
            } else {
                head = null;
            }
            count -= 1;
            return temp.getElement();
        }

        for (int i = 0; i < index - 1; i++) {
            cur = cur.getNext();
        }
        if (cur.getNext().getNext() == null) {
            LinearNode<T> temp = cur.getNext();
            cur.setNext(null);
            count -= 1;
            return temp.getElement();
        }

        LinearNode<T> oldNext = cur.getNext().getNext();
        LinearNode<T> temp = cur.getNext();
        cur.setNext(oldNext);
        count -= 1;
        return temp.getElement();
    }

    @Override
    public T remove(T element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("Null element");
        }
        int index = indexOf(element);

        if (index <= -1) {
            throw new IllegalStateException("Element not in list");
        }
        if (index == 0) {
            // remove head
            LinearNode<T> t = head;
            head = head.getNext();
            count -= 1;
            return t.getElement();
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < index - 1; i++) {
            cur = cur.getNext();
        }

//        System.out.println("Trying to remove right of " + cur.getElement());

        if (cur.getNext().getNext() == null) {
            LinearNode<T> temp = cur.getNext();
            cur.setNext(null);
            count -= 1;
            return temp.getElement();
        }
        LinearNode<T> temp = cur.getNext();
        cur.setNext(temp.getNext());
        count -= 1;
        return temp.getElement();
    }

    @Override
    public int indexOf(T element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < count; i++) {
            if (cur.getElement().equals(element)) {
                return i;
            }
            cur = cur.getNext();
        }
        return -1;
    }

    @Override
    public boolean contains(T element) throws IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException("No nulls allowed");
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < count; i++) {
            if (cur.getElement().equals(element)) {
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return count <= 0;
    }

    public boolean isFull() {
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    public LinearNode<T> getNode(int index) throws IllegalStateException {
        if (index > count - 1 || index < 0) {
            throw new IllegalStateException("Index does not exist");
        }
        LinearNode<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.getNext();
        }
        return cur;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        if (head == null) {
            return "List is empty";
        }

        LinearNode<T> l = head;
        s.append(l.getElement());

        while (l.getNext() != null) {
            s.append(l.getNext().getElement());
            l = l.getNext();
        }

        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private LinearNode<T> current;

        public LinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null; // && current.getNext() != null;
        }

        @Override
        public T next() {
            LinearNode<T> temp = current;
            if (temp == null) {
                return null;
            }
            current = current.getNext();
            return temp.getElement();
        }
    }
}
