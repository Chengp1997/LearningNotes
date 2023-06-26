

package STL;

public class LinkList<E> {
    Node<E> header;
    Node<E> tail;
    int size;

    public LinkList() {
        header = new Node<E>();
        tail = new Node<E>();
        header.setNext(tail);
        tail.setPre(header);
        size = 0;
    }

    public int indexOf(E e){
// Node<E> returnNode = header;
// for(int i = 0; i <= this.size; i++) {
// if (returnNode.equals(e)){
// return i;
// }
// returnNode = returnNode.getNext();
// }
// return -1;
        int index = 0;
        if (e == null) {
            for (Node<E> x = header; x != null; x = x.getNext()) {
                if (x.getdata() == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = header; x != null; x = x.getNext()) {
                if (e.equals(x.getdata()))
                    return index;
                index++;
            }
        }
        return -1;
    }
    public int size() {
        return size;
    }

    public void minusSize() {
        size --;
    }

    public void addSize() {
        size ++;
    }

    public boolean isEmpty() {
        if(size == 0)
            return true;
        return false;
    }

    public Node<E> getHeader() {
        return header;
    }

    public Node<E> getTail() {
        return tail;
    }

    public void add(E data) {
        Node<E> newNode = new Node<E>(data);
        if(size == 0) {
            header.setNext(newNode);
            tail.setPre(newNode);
            newNode.setPre(header);
        }else {
            tail.getPre().setNext(newNode);
            newNode.setPre(tail.getPre());
            tail.setPre(newNode);
        }
        newNode.setNext(tail);
        size ++;
    }

    public void addAll(LinkList<E> anotherList) {
        Node<E> node = new Node<>();
        node = anotherList.getHeader();
        while(node.getNext() != anotherList.getTail()) {
            add(node.getNext().getdata());
            node = node.getNext();
        }
    }

    public boolean remove(E data) {
        if(size == 0)
            return false;
        for(Node<E> nodeTemp = header.getNext(); nodeTemp.getdata() != null; nodeTemp = nodeTemp.getNext()) {
            if(nodeTemp.getdata() == data) {
                nodeTemp.getPre().setNext(nodeTemp.getNext());
                nodeTemp.getNext().setPre(nodeTemp.getPre());
                nodeTemp = null;
                size --;
                return true;
            }
        }
        return false;
    }

// public boolean remove(int index) {
// if (this.isEmpty()){
// return false;
// }
// Node<E> nodeTemp=header;
// for (nodeTemp;i<index;i++){
//
// }
// for(Node<E> nodeTemp = header.getNext(); nodeTemp.getdata() != null; nodeTemp = nodeTemp.getNext()) {
// if(nodeTemp.getdata() == data) {
// nodeTemp.getPre().setNext(nodeTemp.getNext());
// nodeTemp.getNext().setPre(nodeTemp.getPre());
// nodeTemp = null;
// size --;
// return true;
// }
// }
// return false;
// }

    public boolean removeFront() {
        if(isEmpty())
            return false;
        header.setNext(header.getNext().getNext());
        header.getNext().setPre(header);
        size--;
        return true;
    }

    public E getFirst() {
        return header.getNext().getdata();
    }

    public E getLast() {
        return tail.getPre().getdata();
    }

    public E get(int index) {
        Node<E> returnNode = new Node<>();
        returnNode = header;
        for(int i = 0; i <= index; i++) {
            returnNode = returnNode.getNext();
        }
        return returnNode.getdata();
    }

    public E[] toArray() {
        E [] array = (E[]) new Object[size];
        LinkListIterator<E> iterator = new LinkListIterator<>(this);
        int i = 0;
        while(iterator.hasNext()) {
            array[i] = iterator.next().getdata();
            i++;
        }
        return array;
    }

    public boolean contains(E e) {
        return indexOf(e) != -1;
    }
}
