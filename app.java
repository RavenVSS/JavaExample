/*
 * Данный программный код написан в учебных целях.
 */

import java.util.Optional;

class App {
    public static void main(String[] args) {

        new MyStack<Integer>().min();
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(20);
        stack.push(0);
        stack.push(5);
        stack.push(30);
        stack.push(0);
        System.out.println("Test pop: " + stack.pop().get());
        System.out.println("Test peek: " + stack.peek().get());
        System.out.println("Max: " + stack.max().get());
        System.out.println("Min: " + stack.min().get());
    }
}

interface Stack<T> {
    public void push(T obj);
    public Optional<T> pop();
    public Optional<T> peek();
    public Optional<T> max();
    public Optional<T> min();
}

class MyStack<T extends Comparable<T>> implements Stack<T> {
    private Item<T> top;
    private Item<T> max;
    private Item<T> min;

    private class Item<U extends Comparable<U>> implements Comparable<Item<U>> {
        private U currentItem;
        private Item<U> prevItem;
        private Item<U> prevMax;
        private Item<U> prevMin;

        public Item() {

        }

        public Item(U currentItem, Item<U> prevItem) {
            this.currentItem = currentItem;
            this.prevItem = prevItem;
        }

        protected U peek() {
            return this.currentItem;
        }

        protected Item<U> getPrev() {
            return this.prevItem;
        }

        protected Item<U> getPrevMax() {
            return this.prevMax;
        }

        protected Item<U> getPrevMin() {
            return this.prevMin;
        }

        protected boolean first() {
            return this.prevItem == null;
        }

        @Override
        public int compareTo(Item<U> item) {
            int result = this.peek().compareTo(item.peek());
            return result;
        }

        protected Item<U> max(Item<U> x) {
            if (x == null) x = this;
            if (x.compareTo(this) > 0) {
                return x;
            } else {
                this.prevMax = x;
                return this;
            }
        }

        protected Item<U> min(Item<U> x) {
            if (x == null) x = this;
            if (x.compareTo(this) < 0) {
                return x;
            } else {
                this.prevMin = x;
                return this;
            }
        }
    }

    public MyStack() {

    }

    /* Вносит объект в стек */
    public void push(T obj) {
        this.top = new Item<T>(obj, this.top);

        this.max = this.top.max(this.max);
        this.min = this.top.min(this.min);
    }

    /* Возвращает ссылку на верхний объект стека с последующим удалением объекта из стека */
    public Optional<T> pop() {
        Optional<Item<T>> obj = Optional.ofNullable(this.top);
        Optional<T> result = Optional.empty();
        if (obj.isPresent()) {
            Item<T> item = obj.get();
            result = Optional.ofNullable(item.peek());
            if (!item.first()) {
                if (item.getPrevMax() != null) {
                    this.max = item.getPrevMax();
                }
                if (item.getPrevMin() != null) {
                    this.min = item.getPrevMin();
                }
                this.top = item.getPrev();
            } else this.top = null;
        }
        return result;
    }

    /* Возвращает ссылку на верхний объект стека */
    public Optional<T> peek() {
        Optional<Item<T>> optional = Optional.ofNullable(this.top);
        if(optional.isPresent())
            return Optional.ofNullable(optional.get().peek());
        return Optional.empty();
    }

    /* Возвращает ссылку на максимальный объект в стеке */
    public Optional<T> max() {
        Optional<Item<T>> optional = Optional.ofNullable(this.max);
        if(optional.isPresent())
            return Optional.ofNullable(optional.get().peek());
        return Optional.empty();
    }

    /* Возвращает ссылку на минимальный объект в стеке */
    public Optional<T> min() {
        Optional<Item<T>> optional = Optional.ofNullable(this.min);
        if(optional.isPresent())
            return Optional.ofNullable(optional.get().peek());
        return Optional.empty();
    }
}