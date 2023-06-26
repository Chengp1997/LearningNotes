package STL;

public class Node<E> {
    private E data;
    private Node<E> next;
    private Node<E> pre;

    public Node() {
    }

    public Node(E data) {
        this.data = data;
        this.next = null;
        this.pre = null;
    }

    public Node(E data, Node<E> next, Node<E> pre) {
        this.data = data;
        this.next = next;
        this.pre = pre;
    }

    public E getdata() {
        return data;
    }

    public void setdata() {
        this.data = data;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPre() {
        return pre;
    }

    public void setPre(Node<E> pre) {
        this.pre = pre;
    }

}
