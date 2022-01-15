package boundedpipe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Pipe implements by linked node. Act as a normal list with specific capacity
 * User can append, prepend, removeFirst, removeLast, copy, clear the pipe
 * User can also see the first/last element, see the length and the capacity of the pipe
 * </p>
 *
 * @param <E> the type of element store in the pipe
 */
public class LinkedPipe<E> extends AbstractPipe<E> {

    /**
     * the first node of the linkedPipe
     */
    private Node first;
    /**
     * the last node of the linkedPipe
     */
    private Node last;
    /**
     * current length of the linkedPipe
     */
    private int length;

    /**
     * The constructor of the AbstractPipe, construct an empty pipe with a specified capacity
     *
     * @param capacity the initial capacity of the pipe
     * @throws IllegalArgumentException throw if the specified capacity is negative
     */
    public LinkedPipe(int capacity) {
        super(capacity);
        first = null;
        last = null;
        length = 0;
    }

    /**
     * <p>
     * add the element at the head of the pipe
     * </p>
     * <p>
     * pre:pipe=[A, B, C]:6 and x=X
     * STMT: pipe.prepend(x)
     * POST: pipe=[X, A, B, C]:6 and x=X
     * </p>
     *
     * @param element the element to be append at the head of the pipe
     * @throws IllegalStateException    throw when the linkedPipe is full
     * @throws IllegalArgumentException throw when the argument is null
     */
    @Override
    public void prepend(E element) throws IllegalStateException, IllegalArgumentException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        else if (isFull()) {
            throw new IllegalStateException();
        }
        else {
            Node newNode = new Node(element);
            if (isEmpty()) {
                first = newNode;
                last = newNode;
            }
            else {
                newNode.next = first;
                first = newNode;
            }
            length++;
        }
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
        else {
            Node newNode = new Node(element);
            if (isEmpty()) {
                first = newNode;
                last = newNode;
            }
            else {
                newNode.prev = last;
                last.next = newNode;
                last = newNode;
            }
            length++;
        }
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
            E returnElement = first.contents;
            if (length == 1) {
                first = null;
            }
            else {
                first = first.next;
                first.prev = null;
            }
            length--;
            return returnElement;
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
            E returnElement = last.contents;
            if (length == 1) {
                last = null;
            }
            else {
                last = last.prev;
                last.next = null;
            }
            length--;
            return returnElement;
        }
    }

    /**
     * clear all the element in the pipe
     */
    @Override
    public void clear() {
        first = null;
        last = null;
        length = 0;
    }

    /**
     * return the length of the ListPipe
     *
     * @return the length of the ListPipe
     */
    @Override
    public int length() {
        return length;
    }

    /**
     * return a new, empty instance with the same capacity
     *
     * @return new, empty instance with the same capacity
     */
    @Override
    public LinkedPipe<E> newInstance() {
        return new LinkedPipe<>(capacity());
    }

    /**
     * return the first element of the list
     *
     * @return the first element of the list
     */
    @Override
    public E first() {
        if (isEmpty()) return null;
        else return first.contents;
    }

    /**
     * return the last element of the list
     *
     * @return the last element of the list
     */
    @Override
    public E last() {
        if (isEmpty()) return null;
        else return last.contents;
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
     * The basic element of the LinkedPipe
     */
    class Node {
        E contents;
        Node prev;
        Node next;

        public Node(E contents) {
            this.contents = contents;
        }
    }

    /**
     * Iterator of the LinkedPipe
     */
    class PipeIterator implements Iterator<E> {

        Node currentNode;

        public PipeIterator() {
            currentNode = first;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E data = currentNode.contents;
            currentNode = currentNode.next;
            return data;
        }
    }
}
