/*
 * Данный программный код написан в учебных целях.
 */

import java.util.Optional;

class App {
    public static void main(String[] args) {

        new MyStack<Integer>().min();
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(20);
        stack.push(1);
        stack.push(5);
        stack.push(30);
        stack.push(0);
        System.out.println("Test pop 1: " + stack.pop().get());
        System.out.println("Test pop 2: " + stack.pop().get());
        System.out.println("Test peek 3: " + stack.peek().get());
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
        private Item<T> prevItem;

        public Item() {

        }

        public Item(U currentItem, Item<T> prevItem) {
            this.currentItem = currentItem;
            this.prevItem = prevItem;
        }

        protected U peek() {
            return this.currentItem;
        }

        protected Item<T> getPrev() {
            return this.prevItem;
        }


        protected boolean first() {
            return this.prevItem == null;
        }

        @Override
        public int compareTo(Item<U> item) {
            int result = this.peek().compareTo(item.peek());
            return result;
        }

        protected static <U extends Comparable<U>> U max(U x, U y) {
            if (x == null) x = y;
            if (x.compareTo(y) > 0) {
                return x;
            }
            return y;
        }

        protected static <U extends Comparable<U>> U min(U x, U y) {
            if (x == null) x = y;
            if (x.compareTo(y) < 0) {
                return x;
            }
            return y;
        }
    }

    public MyStack() {

    }

    /* Вносит объект в стек */
    public void push(T obj) {
        this.top = new Item<T>(obj, this.top);

        this.max = Item.max(this.max, this.top);
        this.min = Item.min(this.min, this.top);
    }

    private void checkMin(Item<T> top) {
        this.min = Item.min(this.min, top);
        if (!top.first()) checkMin(top.getPrev());
    }

    private void checkMax(Item<T> top) {
        this.max = Item.max(this.max, top);
        if (!top.first()) checkMax(top.getPrev());
    }

    /* Возвращает ссылку на верхний объект стека с последующим удалением объекта из стека */
    public Optional<T> pop() {
        Optional<Item<T>> obj = Optional.ofNullable(this.top);
        Optional<T> result = Optional.empty();
        if (obj.isPresent()) {
            Item<T> item = obj.get();
            result = Optional.ofNullable(item.peek());
            if (!item.first()) {
                if (this.top == this.min) {
                    this.min = item.getPrev();
                    checkMin(item.getPrev());
                }
                if (this.top == this.max) {
                    this.max = item.getPrev();
                    checkMax(item.getPrev());
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