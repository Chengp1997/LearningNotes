package STL;

public class LinkListIterator<E>{
    private LinkList<E> linkList;
    private Node<E> currentNode;

    public LinkListIterator(LinkList<E> linkList) {
        this.linkList = linkList;
        currentNode = linkList.getHeader();
    }

    public boolean hasNext() {
        if(currentNode.getNext() == linkList.getTail()) {
            return false;
        }
        return  true;
    }

    public Node<E> next() {
        currentNode = currentNode.getNext();
        return currentNode;
    }

    public boolean remove() {
        if(linkList.size() == 0) {
            return false;
        }
        currentNode.getPre().setNext(currentNode.getNext());
        currentNode.getNext().setPre(currentNode.getPre());
        currentNode = currentNode.getPre();
        linkList.minusSize();
        return true;
    }

}
