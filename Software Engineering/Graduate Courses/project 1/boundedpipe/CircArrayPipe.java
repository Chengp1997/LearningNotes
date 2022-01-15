package boundedpipe;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircArrayPipe<E> extends AbstractPipe<E> {

    /**
     * store all the elements of the circleArrayPipe
     */
    private final E[] elements;
    /**
     * the pointer to record the first element position
     */
    private int first;
    /**
     * the pointer to record the last element position
     */
    private int last;

    /**
     * The constructor of the CircArrayPipe, construct an empty pipe with a specified capacity
     *
     * @param capacity the initial capacity of the pipe
     * @throws IllegalArgumentException throw if the specified capacity is negative
     */
    @SuppressWarnings("unchecked")
    public CircArrayPipe(int capacity) {
        super(capacity);
        elements = (E[]) new Object[capacity];
        first = -1;
        last = -1;
    }

    /**
     * <p>
     * Append the element at the head of the ListPipe
     * </p>
     * <p>
     * pre:pipe=[A, B, C]:6 and x=X
     * STMT: pipe.prepend(x)
     * POST: pipe=[X, A, B, C]:6 and x=X
     * </p>
     *
     * @param element the element to be append at the head of the ListPipe
     * @throws IllegalArgumentException throws when element is null
     * @throws IllegalStateException    throws when the list is full
     */
    @Override
    public void prepend(E element) throws IllegalStateException, IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        else if (isFull()) {
            throw new IllegalStateException();
        }
        else if (isEmpty()) {
            first = 0;
            last = 0;
        }
        else {
            first = (first - 1 + capacity()) % capacity();
        }
        elements[first] = element;
    }

    /**
     * Append the element at the tail of the ListPipe
     * <p>
     * pre:pipe=[A, B, C]:6 and x=X
     * STMT: pipe.append(x)
     * POST: pipe=[A, B, C, X]:6 and x=X
     * </p>
     *
     * @param element the element to be append at the tail of the ListPipe
     * @throws IllegalStateException    throws when list is full
     * @throws IllegalArgumentException throws when element is null
     */
    @Override
    public void append(E element) throws IllegalStateException, IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        else if (isFull()) {
            throw new IllegalStateException();
        }
        else if (isEmpty()) {
            first = 0;
            last = 0;
        }
        else {
            last = (last + 1) % capacity();
        }
        elements[last] = element;
    }

    /**
     * remove the first element of the ListPipe
     * <p>
     * pre:pipe=[A, B, C]:6 and x=X
     * STMT: pipe.removeFirst(x)
     * POST: pipe=[B, C]:6 and x=X
     * </p>
     *
     * @return the first element of the ListPipe
     * @throws IllegalStateException throws when the list is empty
     */
    @Override
    public E removeFirst() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        else {
            E returnValue = elements[first];
            elements[first] = null;
            if (this.length()==1){
                first=-1;
                last=-1;
            }
            else {
                first = (first + 1) % capacity();
            }
            return returnValue;
        }

    }

    /**
     * remove the last element of the ListPipe
     * <p>
     * pre:pipe=[A, B, C]:6 and x=X
     * STMT: pipe.removeFirst(x)
     * POST: pipe=[A, B]:6 and x=X
     * </p>
     *
     * @return the last element of the ListPipe
     * @throws IllegalStateException throws when the list is empty
     */
    @Override
    public E removeLast() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        else {
            E returnValue = elements[last];
            elements[last] = null;
            if (this.length()==1){
                first=-1;
                last=-1;
            }
            else {
                last = (last - 1 + capacity()) % capacity();
            }
            return returnValue;
        }
    }

    /**
     * return the length of the ListPipe
     *
     * @return the length of the ListPipe
     */
    @Override
    public int length() {
        int length;
        if (first == -1) {
            length = 0;
        }
        else if ((last + 1) % capacity() == first) {
            length = capacity();
        }
        else {
            length = (last + 1 - first + capacity()) % capacity();
        }
        return length;
    }

    /**
     * return a new, empty instance with the same capacity
     *
     * @return new, empty instance with the same capacity
     */
    @Override
    public CircArrayPipe<E> newInstance() {
        return new CircArrayPipe<>(capacity());
    }

    /**
     * clear all the element in the pipe
     */
    @Override
    public void clear() {
        first = -1;
        last = -1;
    }

    /**
     * return the first element of the list
     *
     * @return the first element of the list
     */
    @Override
    public E first() {
        if (isEmpty()) return null;
        else return elements[first];
    }

    /**
     * return the last element of the list
     *
     * @return the last element of the list
     */
    @Override
    public E last() {
        if (isEmpty()) return null;
        else return elements[last];
    }

    /**
     * return the iterator of the pipe
     *
     * @return the iterator of the pipe
     */
    @Override
    public Iterator<E> iterator() {
        return new PipeIterator();
    }

    /**
     * The iterator of the CircArrayPipe
     */
    class PipeIterator implements Iterator<E> {

        int index;

        public PipeIterator() {
            index = first;
        }

        @Override
        public boolean hasNext() {
            return index != -1;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            E returnValue = elements[index];
            if (index == last) {
                index = -1;
            }
            else {
                index = (index + 1) % capacity();
            }
            return returnValue;
        }
    }

}
