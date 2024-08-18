package boundedpipe;

/**
 * <p>
 * A bounded, queue-liked liner data structure. Element can be added at both side, remove at both side. Elements can not be null.
 * </p>
 * <p>
 * A typical string representation of a bounded pipe is
 * <code>[e1, e2, ..., en-1, en]:c</code>
 * where <code>e1</code> is the the first element of the pipe
 * and <code>en</code> is the last element of the pipe
 * and <code>c</code> is the capacity.
 * </p>
 * <p>
 * Implementations of this interface should have one-argument constructor
 * that takes the desired capacity that takes the desired capacity
 * and creates an empty pipe.
 * The capacity must be strictly greater than zero.
 * </p>
 * <p><code>public Pipe(int capacity)</code></p>
 * <p>
 * A pipe iterator iterates through the pipe from the first element to the last element
 * </p>
 *
 * @param <E> the type of elements in the pipe
 */
public interface Pipe<E> extends Iterable<E> {
    /**
     * <p>
     * adds the specified element at the head of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code> <em>and</em> <code>x=X</code>}<br>
     * <code>p.prepend(x)</code><br>
     * {<code>p=[X, A, B, C]:6</code> <em>and</em> <code>x=X</code>}<br>
     * </p>
     *
     * @param element the element to be append at the head of the pipe
     * @throws IllegalStateException    if the pipe is full
     * @throws IllegalArgumentException if the specified element is null
     */
    void prepend(E element) throws IllegalStateException, IllegalArgumentException;

    /**
     * <p>
     * adds the specified element at the tail of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code> <em>and</em> <code>x=X</code>}<br>
     * <code>p.append(x)</code><br>
     * {<code>p=[A, B, C, X]:6</code> <em>and</em> <code>x=X</code>}<br>
     * </p>
     *
     * @param element the element to be append at the tail of the pipe
     * @throws IllegalStateException    if the pipe is full
     * @throws IllegalArgumentException if the specified element is null
     */
    void append(E element) throws IllegalStateException, IllegalArgumentException;

    /**
     * <p>
     * remove the first element of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>p.removeFist()</code><br>
     * {<code>p=[B, C]:6</code>}<br>
     * </p>
     *
     * @return return the removed element of the pipe
     * @throws IllegalStateException if the pipe is empty
     */
    E removeFirst() throws IllegalStateException;

    /**
     * <p>
     * remove the last element of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>p.removeLast()</code><br>
     * {<code>p=[A, B]:6</code>}<br>
     * </p>
     *
     * @return return the removed element of the pipe
     * @throws IllegalStateException if the pipe is empty
     */
    E removeLast() throws IllegalStateException;

    /**
     * <p>
     * return the size of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>p.length()</code><br>
     * {<code>3</code>}
     * </p>
     *
     * @return the size of the pipe
     */
    int length();

    /**
     * <p>
     * return the capacity of the pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>p.capacity()</code><br>
     * {<code>6</code>}<br>
     * </p>
     *
     * @return the capacity of the pipe
     */
    int capacity();

    /**
     * <p>
     * creates a new,empty bounded pipe instance
     * with the same capacity as the calling pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code> <em>and</em> <code>Pipe newPipe</code> }<br>
     * <code>newPipe=p.newInstance()</code><br>
     * {<code>newPipe=[]:6</code>}<br>
     * </p>
     *
     * @return the new, empty bounded pipe instance
     */
    Pipe<E> newInstance();

    /**
     * <p>clear the element of the pipe</p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>p.clear();</code><br>
     * {<code>p=[]:6</code>}<br>
     * </p>
     */
    void clear();

    /**
     * <p>
     * test if the pipe is empty or not
     * </p>
     * <p>
     * Example1:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>System.out.println(p.isEmpty())</code><br>
     * {<code>false</code>}<br>
     * Example2:<br>
     * {<code>p=[]:6</code>}<br>
     * <code>System.out.println(p.isEmpty())</code><br>
     * {<code>true</code>}<br>
     * </p>
     *
     * @return the result whether the pipe is empty or not
     */
    boolean isEmpty();

    /**
     * <p>
     * test if the pipe is full or not
     * </p>
     * <p>
     * Example1:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>System.out.println(p.isFull())</code><br>
     * {<code>false</code>}<br>
     * Example2:<br>
     * {<code>p=[A, B, C, D, E, F]:6</code>}<br>
     * <code>System.out.println(p.isFull())</code><br>
     * {<code>true</code>}<br>
     * </p>
     *
     * @return the result whether the pipe is full or not
     */
    boolean isFull();

    /**
     * <p>
     * append all the element of  a specified pipe to this pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>old=[]:6</code> <em>and</em> <code>new=[A, B, C]:6</code>}<br>
     * <code>old.appendAll(new)</code><br>
     * {<code>old=[A, B, C]:6</code> <em>and</em> <code>new=[]:6</code>}<br>
     * </p>
     *
     * @param that the specified pipe that is used to append its element to this pipe
     * @throws IllegalStateException    when the pipe is full, throws IllegalStateException
     * @throws IllegalArgumentException if the that pipe is null, throws Illegal ArgumentException
     */
    void appendAll(Pipe<E> that) throws IllegalStateException, IllegalArgumentException;

    /**
     * <p>
     * copy all the element from this pipe to a new pipe
     * </p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>  <em>and</em> <code>Pipe newPipe</code>}<br>
     * <code>newPipe=p.copy()</code><br>
     * {<code>newPipe=[A, B, C]:6</code>  <em>and</em> <code>Pipe newPipe</code>}<br>
     * </p>
     *
     * @return return the new pipe copied from this pipe
     */
    Pipe<E> copy();

    /**
     * <p>return(and not remove) the first element of the pipe, return null if it is empty</p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>System.out.println(first())</code>
     * {<code>A</code>}
     * </p>
     * @return the first element of the pipe, return null if it is empty
     */
    E first();

    /**
     * <p>return(and not remove) the last element of the pipe, return null if it is empty</p>
     * <p>
     * Example:<br>
     * {<code>p=[A, B, C]:6</code>}<br>
     * <code>System.out.println(last())</code>
     * {<code>C</code>}
     * </p>
     * @return the last element of the pipe, return null if it is empty
     */
    E last();
}
