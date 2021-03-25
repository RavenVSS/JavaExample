/*
 * Данный программный код написан в учебных целях.
 */

class App {
    public static void main(String[] args) {

        //new MyStack<Integer>().min();
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

interface Stack<T> {
    public void push(T obj);
    public T pop();
    public T peek();
    public T max();
    public T min();
}

class MyStack<T extends Comparable<T>> implements Stack<T> {
    private Item<T> mTop;
    private Item<T> mMax;
    private Item<T> mMin;

    private class Item<U extends Comparable<U>> implements Comparable<Item<U>> {
        private U mCurrentItem;
        private Item<T> mPrevItem;

        public Item() {

        }

        public Item(U mCurrentItem, Item<T> mPrevItem) {
            this.mCurrentItem = mCurrentItem;
            this.mPrevItem = mPrevItem;
        }

        protected U peek() {
            return this.mCurrentItem;
        }


        protected boolean first() {
            return this.mPrevItem == null;
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
        this.mTop = new Item<T>(obj, this.mTop);

        this.mMax = Item.max(this.mMax, this.mTop);
        this.mMin = Item.min(this.mMin, this.mTop);
    }

    /* Возвращает ссылку на верхний объект стека с последующим удалением объекта из стека */
    public T pop() {
        T result = this.mTop == null ? null : mTop.peek();

        if (this.mTop != null && !mTop.first()) {
            mTop = mTop.mPrevItem;
        } else mTop = null;

        // TODO: Сделать проверку на максимальное и минимальное значение в случае удаления объекта

        return result;
    }

    /* Возвращает ссылку на верхний объект стека */
    public T peek() {
        assert this.mTop != null : "mTop is null";
        return this.mTop.peek();
    }

    /* Возвращает ссылку на максимальный объект в стеке */
    public T max() {
        assert this.mMax != null : "mMax is null";
        return this.mMax.peek();
    }

    /* Возвращает ссылку на минимальный объект в стеке */
    public T min() {
        assert this.mMin != null : "mMin is null";
        return this.mMin.peek();
    }
}