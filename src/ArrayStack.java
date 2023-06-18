import java.lang.reflect.Method;
import java.util.Iterator;

public class ArrayStack<E extends Cloneable> implements Stack<E>{
        private int capacity;
        private E[] elements;
        private int head;

        public ArrayStack(int capacity) throws NegativeCapacityException {
            if(capacity<0) {
                throw new NegativeCapacityException();
            }
            this.capacity = capacity;
            elements = (E[]) new Object[capacity];
            head = -1;
        }

        @Override
        public void push(E element){
            if (head == elements.length) {
                throw new StackOverflowException();
            }
            head++;
            elements[head] = element;
        }

        @Override
        public E pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            E element = elements[head];
            elements[head] = null;
            head--;
            return element;
        }

        @Override
        public E peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return elements[head];
        }

        @Override
        public int size() {
            return head+1;
        }

        @Override
        public boolean isEmpty() {
            return head == -1;
        }

        @Override
        public ArrayStack<E> clone() {
            ArrayStack<E> clone;
            try {
                clone = (ArrayStack<E>) super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
            clone.elements = (E[]) new Object[this.capacity];
            for (int i = 0; i <= this.head; i++) {
                try {
                    Method method = this.elements[i].getClass().getMethod("clone", null);
                    method.setAccessible(true);
                    clone.elements[i] = (E) method.invoke(elements[i]);
                } catch (Exception e) {
                    return null;
                }
            }
            return clone;
        }
        class StackIterator implements Iterator<E> {
            private int currentIndex;
            public StackIterator(int head){
                this.currentIndex = head;
            }
        @Override
        public boolean hasNext() {
            return head >= 0;
        }
        @Override
        public E next() {
            if (!hasNext()) {
                throw new StackOverflowException();
            }
            E next = elements[currentIndex];
            currentIndex--;
            return next;
        }
    }

        @Override
        public Iterator<E> iterator() {
            Iterator<E> stackIterator= new StackIterator(this.head);
            return stackIterator;
        }
    }



