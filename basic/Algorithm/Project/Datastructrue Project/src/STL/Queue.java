package STL;

public class Queue<E> {
    LinkList<E> queue = new LinkList<>();
    int count=0;

    public void add(E e){
        queue.add(e);
        count++;
    }

    //获取但不移除此队列的头；如果此队列为空，则返回 null
    public E peek(){
        return queue.getFirst();
    }

    //获取并移除此队列的头
    public E remove(){
        if(empty())
            return null;
        else {
            E data = peek();
            queue.removeFront();
            count--;
            return data;
        }
    }

    public int size() {
        return count;
    }

    //判空
    public boolean empty() {
        return queue.isEmpty();
    }

    public Object[] toArray() {
        Object [] array = new Object[queue.size()];
        LinkListIterator<E> iterator = new LinkListIterator<>(queue);
        int i = 0;
        while(iterator.hasNext()) {
            array[i] = iterator.next().getdata();
            i++;
        }
        return array;
    }

}
