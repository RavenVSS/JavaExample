/*
 * Данный программный код написан в учебных целях.
 */

import java.util.Optional;

class App {
    public static void main(String[] args) {

        new MyStack<Integer>().min();
        MyStack<Integer> stack = new MyStack<Integer>();
        stack.push(20);
        stack.push(5);
        stack.push(30);
        stack.push(0);
        System.out.println("Test pop: " + stack.pop().get());
        System.out.println("Test pop: " + stack.pop().get());
        //System.out.println("Test peek: " + stack.peek().get());
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
    private ItemElement element;
    private ItemMax max;
    private ItemMin min;

    private abstract class Item<T extends Comparable<T>> implements Comparable<Item<T>> {
        private T currentItem;
        private Item<T> prevItem;

        public Item(T currentItem, Item<T> prevItem) {
            this.currentItem = currentItem;
            this.prevItem = prevItem;
        }

        protected T peek() {
            return this.currentItem;
        }

        protected Item<T> getPrev() {
            return this.prevItem;
        }

        protected boolean first() {
            return this.prevItem == null;
        }

        protected ItemMax getPrevMax() {
            return null;
        }

        protected ItemMin getPrevMin() {
            return null;
        }

        @Override
        public int compareTo(Item<T> item) {
            int result = this.peek().compareTo(item.peek());
            return result;
        }
    }

    private class ItemMax extends Item<T> {
        private ItemMax prevMax;

        private ItemMax(Item<T> item) {
            super(item.currentItem, item.prevItem);
        }

        private ItemMax(Item<T> item, ItemMax prevMax) {
            super(item.currentItem, item.prevItem);
            this.prevMax = prevMax;
        }

        private ItemElement getItem() {
            return new ItemElement(super.currentItem, super.prevItem);
        }

        @Override
        protected ItemMax getPrevMax() {
            return this.prevMax;
        }
    }

    private class ItemMin extends Item<T> {
        private ItemMin prevMin;

        private ItemMin(Item<T> item) {
            super(item.currentItem, item.prevItem);
        }

        private ItemMin(Item<T> item, ItemMin prevMin) {
            super(item.currentItem, item.prevItem);
            this.prevMin = prevMin;
        }

        @Override
        protected ItemMin getPrevMin() {
            return this.prevMin;
        }
    }

    private class ItemElement extends Item<T> {

        private ItemElement(T currentItem, Item<T> prevItem) {
            super(currentItem, prevItem);
        }

        protected ItemMax max(ItemMax x) {
            if (x == null) x = new ItemMax(this);
            if (x.compareTo(this) > 0) {
                return x;
            } else {
                return new ItemMax(this, x);
            }
        }

        protected ItemMin min(ItemMin x) {
            if (x == null) x = new ItemMin(this);
            if (x.compareTo(this) < 0) {
                return x;
            } else {
                return new ItemMin(this, x);
            }
        }
    }

    public MyStack() {

    }

    /* Вносит объект в стек */
    public void push(T obj) {
        this.element = new ItemElement(obj, this.top);
        this.max = this.element.max(this.max);
        this.min = this.element.min(this.min);

        if (this.top == null) this.top = this.element;
        else if (this.max.peek() == this.element.peek()) this.top = this.max;
        else if (this.min.peek() == this.element.peek()) this.top = this.min;
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
            } else {
                this.top = null;
                this.min = null;
                this.max = null;
            }
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