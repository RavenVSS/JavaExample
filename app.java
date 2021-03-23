class App {
    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.Push(10);
        stack.Push(11);
        System.out.println("Test: " + stack.Pop());
        System.out.println("Test: " + stack.Pop());
        System.out.println("Test: " + stack.Pop());
    }
}

class MyStack<T> {
    Item top;

    class Item {
        Object currentItem;
        Item prevItem;

        public Item(Object item, Item prev) {
            this.currentItem = item;
            this.prevItem = prev;
        }

        public Object Peek() {
            return this.currentItem;
        }

        public boolean First() {
            return this.prevItem == null;
        }
    }

    public MyStack() {
        this.top = null;
    }

    void Push(T obj) {
        this.top = new Item(obj, this.top);
    }

    T Pop() {
        Object result = this.top!=null ? top.Peek() : 0;

        if (!top.First()) {
            top = top.prevItem;
        } else top = null;

        return (T)result;
    }

    T Peek() {
        return (T)top.Peek();
    }
}