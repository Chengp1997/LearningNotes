package STL;

import Model.Vertex;
import Model.VirtualCar;

public class Stack<E> {
    private Object [] stackList;
    private int size;
    private int top = 0; //index of top

    public Stack() {
        int size = 1000;
        stackList = new Object[size];
    }

    public Stack(int size) {
        this.size = size;
        stackList = new Object[size];
    }

    public boolean isEmpty() {
        if(top > 0)
            return false;
        return true;
    }

    public boolean isFull() {
        return top==size;
    }

    public void push(E data) {
        if(!isFull())
            stackList[top++] = data;
    }

    public E pop() {
        if(!isEmpty()) {
            top--;
            return (E)stackList[top];
        }
        return null;
    }

    public E peek() {
        if(!isEmpty())
            return (E)stackList[top - 1];
        return null;
    }

    public int depth() {
        return top;
    }

    public E element(int i) {
        return (E) stackList[i];
    }

    public boolean contains(E object){
        for (int i=0;i<stackList.length;i++){
            System.out.println(i);
            if (stackList[i].equals(object)){
                return true;
            }
        }
        return false;
    }

}
