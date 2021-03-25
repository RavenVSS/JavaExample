import java.util.*;

class App {
    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(40);
        stack.push(1);
        stack.push(0);
        stack.push(30);
        stack.push(5);
        System.out.println("Test 1: " + stack.pop());
        System.out.println("Test 2: " + stack.peek());
        System.out.println("Max: " + stack.max());
        System.out.println("Min: " + stack.min());
    }
}

class MyStack<T extends Comparable<T>> {
    private Item<T> top;
    private Item<T> max;
    private Item<T> min;

    private class Item<U extends Comparable<U>> implements Comparable<Item<U>> {
        private U currentItem;
        private Item<T> prevItem;

        public Item(U currentItem, Item<T> prevItem) {
            this.currentItem = currentItem;
            this.prevItem = prevItem;
        }

        public U peek() {
            return this.currentItem;
        }

        public boolean first() {
            return this.prevItem == null;
        }

        @Override
        public int compareTo(Item<U> item)
        {
            int result = this.peek().compareTo(item.peek());
            return result;
        }

        protected static <U extends Comparable<U>> U max(U x, U y) {
            if (x.compareTo(y) > 0) {
                return x;
            }
            return y;
        }

        protected static <U extends Comparable<U>> U min(U x, U y) {
            if (x.compareTo(y) < 0) {
                return x;
            }
            return y;
        }
    }

    public MyStack() {
        this.top = null;
        this.max = null;
        this.min = null;
    }

    void push(T obj) {
        this.top = new Item(obj, this.top);

        if (this.max == null) this.max = this.top;
        this.max = Item.max(this.max, this.top);

        if (this.min == null) this.min = this.top;
        this.min = Item.min(this.min, this.top);
    }

    T pop() {
        T result = this.top == null ? null : top.peek();

        if (this.top != null && !top.first()) {
            top = top.prevItem;
        } else top = null;

        return result;
    }

    T peek() {
        return top.peek();
    }

    T max() {
        return max.peek();
    }

    T min() {
        return min.peek();
    }
}