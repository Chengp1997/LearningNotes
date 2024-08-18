package boundedpipe;

import java.util.Iterator;

/**
 * <p>
 * The Abstract class of the Pipe. Implement some basic functions of the Pipe, in order to to minimize the effort required to implement this interface.
 * </p>
 *
 * @param <E> the type of the element in the pipe
 */
public abstract class AbstractPipe<E> implements Pipe<E> {

    /**
     * the capacity of the Abstract
     */
    private int capacity;

    /**
     * The constructor of the AbstractPipe, construct an empty pipe with a specified capacity
     *
     * @param capacity the initial capacity of the pipe
     * @throws IllegalArgumentException throw if the specified capacity is negative
     */
    public AbstractPipe(int capacity) throws IllegalArgumentException {
        if (capacity > 0) {
            this.capacity = capacity;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the capacity of the pipe
     *
     * @return the capacity of the pipe
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * return <code>true</code> if the pipe has no element
     *
     * @return <code>true</code> if the pipe has no element
     */
    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    /**
     * return <code>true</code> if the size of the pipe is equal to the capacity of the pipe
     *
     * @return <code>true</code> if the size of the pipe is equal to the capacity of the pipe
     */
    @Override
    public boolean isFull() {
        return length() == capacity();
    }

    /**
     * <p>
     * remove all the element from another pipe and append them all to this pipe
     * </p>
     * <p>
     * pre:old=[]:6 and new=[A, B, C]:6
     * STMT: old.prepend(new)
     * POST: old=[A, B, C]:6 and new=[]:6
     * </p>
     *
     * @param that the specified pipe that is used to append its element to this pipe
     * @throws IllegalArgumentException throws when that is null
     * @throws IllegalStateException    throws when the element is out of capacity
     */
    @Override
    public void appendAll(Pipe<E> that) throws IllegalArgumentException, IllegalStateException {
        if (that == null) {
            throw new IllegalArgumentException();
        }
        if (that.length() > this.capacity() || that.length() + this.length() > this.capacity()) {
            throw new IllegalStateException();
        }

        if (that.length() == 0) return;
        E element = that.removeFirst();
        this.append(element);
        this.appendAll(that);
    }

    /**
     * <p>
     * copy and return a pipe that is the same as current one. The
     * elements in the copy are references to the elements in this stack
     * </p>
     *
     * @return a pipe that is the same as current one
     */
    @Override
    public Pipe<E> copy() {
        Pipe<E> pipe = this.newInstance();
        for (E element : this) {
            pipe.append(element);
        }
        return pipe;
    }

    /**
     * <p>
     * return true if the two object are the same
     * </p>
     *
     * @param o the object used to compare
     * @return true if the two object are the same
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pipe)) {
            return false;
        }

        Pipe<?> that = (Pipe) o;
        if (this.capacity() != that.capacity()) {
            return false;
        }
        if (this.length() != that.length()) {
            return false;
        }
        Iterator<E> thisIterator = this.iterator();
        Iterator<?> thatIterator = that.iterator();
        while (thisIterator.hasNext()) {
            if (!(thisIterator.next().equals(thatIterator.next()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * the hashcode used to override the new equal()
     * </p>
     *
     * @return new hashcode
     */
    @Override
    public int hashCode() {
        int result = 17;
        for (E e : this) {
            result = 31 * result + e.hashCode();
        }
        return 31 * result + capacity;
    }

    /**
     * <p>
     * return a new format of the toString() method. It should look like <code>[A, B, C]:6</code>
     * </p>
     *
     * @return return new format of the toString method
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) stringBuilder.append(", ");
        }
        stringBuilder.append("]:");
        stringBuilder.append(capacity());
        return stringBuilder.toString();
    }
}
