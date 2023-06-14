public class ArrayStack<E extends Cloneable> implements Stack<E>{
        private int capacity;
        private E[] elements;
        private int head;

        public ArrayStack(int capacity) {
            if(capacity<0) {
                throw new NegativeCapacityException;
            }
            this.capacity = capacity;
            elements = (E[]) new Object[capacity];
            head = -1;
        }

        @Override
        public void push(E element) {
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
        public Stack<E> clone() {
            MyStack<E> clone = new MyStack<>();
            clone.elements = elements.clone();
            clone.size = head;
            return clone;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private int currentIndex = head - 1;

                @Override
                public boolean hasNext() {
                    return currentIndex >= 0;
                }

                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException("No more elements in the stack.");
                    }
                    return elements[currentIndex--];
                }
            };
        }
    }


