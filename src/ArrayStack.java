import java.lang.reflect.Method;
import java.sql.Array;
import java.util.Iterator;

public class ArrayStack<E extends Cloneable> implements Stack<E>{
        private int capacity; // represents maximum capacity of the arrayStack
        private Object[] elements;
        private int head; // represents the last element inserted

    /** constructor method */
        public ArrayStack(int capacity) throws NegativeCapacityException {
            if(capacity<0) {
                throw new NegativeCapacityException(); //in case of illegal input
            }
            this.capacity = capacity;
            this.elements = new Object[capacity];
            head = -1;
        }
        /** A method used to insert items to an arrayStack object */

        @Override
        public void push(E element){
            if (head == elements.length-1) {
                throw new StackOverflowException(); // in case the stack is full
            }
            head++;
            elements[head] = element;
        }
    /** A method used to remove items out of an arrayStack object */
        @Override
        public E pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            E element = (E) elements[head];
            elements[head] = null;
            head--;
            return element;
        }
    /** A method used to show the last item inserted to the arrayStack */
        @Override
        public E peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return (E)elements[head];
        }
    /** A method used to display arrayStack object's size */
        @Override
        public int size() {
            return head+1;
        }
    /** A method used to check whether an arrayStack is empty  */
        @Override
        public boolean isEmpty() {
            return head == -1;
        }
    /** A method used to clone an arrayStack object, returns a copy of an arrayStack object */
        @Override
        public ArrayStack<E> clone() {
            ArrayStack<E> clone; // Create a clone of the ArrayStack object
            try {
                clone = (ArrayStack<E>) super.clone();
            } catch (CloneNotSupportedException e) { // Cloning is not supported
                return null;
            }
            // Clone the elements array
            clone.elements = this.elements.clone();
            for (int i = 0; i <= this.head; i++) {
                try {
                    // Get the clone method for the element's class
                    Method method = this.elements[i].getClass().getMethod("clone", null);
                    method.setAccessible(true);
                    // Invoke the clone method to clone the element
                    clone.elements[i] = (E) method.invoke(elements[i]);
                } catch (Exception e) {
                    return null;
                }
            }
            return clone;
        }
    /** An iterator implementation for the ArrayStack */
        class StackIterator implements Iterator<E> {
            private int currentIndex;
        /**
         * Constructs a StackIterator object with the given head index.
         * @param head - the index of the current head element in the Arraystack.
         */
            public StackIterator(int head){
                this.currentIndex = head;
            }
        /** Checks if there is a next element in the iterator. */
        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }
        /** Retrieves the next element in the iterator. */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new StackOverflowException();
            }
            E next = (E)elements[currentIndex];
            currentIndex--;
            return next;
        }
    }
/**  Returns an iterator over the elements in the ArrayStack */
        @Override
        public Iterator<E> iterator() {
            Iterator<E> stackIterator= new StackIterator(this.head);
            return stackIterator;
        }
    }



