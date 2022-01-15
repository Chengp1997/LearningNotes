package boundedpipe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Pipe implements by Linkedlist. Act as a normal list with specific capacity
 * User can append, prepend, removeFirst, removeLast, copy, clear the pipe
 * User can also see the first/last element, see the length and the capacity of the pipe
 * </p>
 *
 * @param <E> the type of element store in the pipe
 */
public class ListPipe<E> extends AbstractPipe<E>{

    //container
    private List<E> list;

    //constructor
    public ListPipe(int capacity){
        super(capacity);
        list=new LinkedList<>();
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
    public void prepend(E element) throws IllegalArgumentException, IllegalStateException {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        list.add(0, element);
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
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        list.add(element);
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
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        return list.remove(0);
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
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
        return list.remove(length() - 1);
    }

    /**
     * return the length of the ListPipe
     *
     * @return the length of the ListPipe
     */
    @Override
    public int length() {
        return list.size();
    }

    /**
     * return a new, empty instance with the same capacity
     *
     * @return new, empty instance with the same capacity
     */
    @Override
    public ListPipe<E> newInstance() {
        return new ListPipe<>(capacity());
    }

    /**
     * clear all the element in the pipe
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * return the first element of the list
     *
     * @return the first element of the list
     */
    @Override
    public E first() {
        if (list.isEmpty()) return null;
        else return list.get(0);
    }

    /**
     * return the last element of the list
     *
     * @return the last element of the list
     */
    @Override
    public E last() {
        if (list.isEmpty()) return null;
        else return list.get(list.size() - 1);
    }

    /**
     * return the iterator of the pipe
     *
     * @return the iterator of the pipe
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

}
