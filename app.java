import java.util.*;

class App {
    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.Push(40);
        stack.Push(1);
        stack.Push(0);
        stack.Push(30);
        stack.Push(5);
        System.out.println("Test 1: " + stack.Pop());
        System.out.println("Test 2: " + stack.Peek());
        System.out.println("Max: " + stack.Max());
        System.out.println("Min: " + stack.Min());
    }
}

class MyStack<T extends Comparable<T>> {
    Item top;
    Item max;
    Item min;

    class Item<U extends Comparable<U>> implements Comparable<Item<U>> {
        U currentItem;
        Item prevItem;

        public Item(U currentItem, Item prevItem) {
            this.currentItem = currentItem;
            this.prevItem = prevItem;
        }

        public U Peek() {
            return this.currentItem;
        }

        public boolean First() {
            return this.prevItem == null;
        }

        @Override
        public int compareTo(Item<U> item)
        {
            int result = this.Peek().compareTo(item.Peek());
            return result;
        }

        public static <U extends Comparable<U>> U max(U x, U y) {
            if (x.compareTo(y) > 0) {
                return x;
            }
            return y;
        }

        public static <U extends Comparable<U>> U min(U x, U y) {
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

    void Push(T obj) {
        this.top = new Item(obj, this.top);

        if (this.max == null) this.max = this.top;
        this.max = Item.max(this.max, this.top);

        if (this.min == null) this.min = this.top;
        this.min = Item.min(this.min, this.top);
    }

    T Pop() {
        T result = this.top == null ? null : (T)top.Peek();

        if (this.top != null && !top.First()) {
            top = top.prevItem;
        } else top = null;

        return (T)result;
    }

    T Peek() {
        return (T)top.Peek();
    }

    T Max() {
        return (T)max.Peek();
    }

    T Min() {
        return (T)min.Peek();
    }
}