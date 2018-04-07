package sensor.quadility.thobho.com.quadility.fixedqueue;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.LinkedList;

public class FixedSizeQueue<E> extends LinkedList<E> {

    private static final int DEFAULT_MAX_SIZE = 16;
    private int maxSize;

    public FixedSizeQueue(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    public FixedSizeQueue() {
        super();
        this.maxSize = DEFAULT_MAX_SIZE;
    }

    public FixedSizeQueue(@NonNull Collection<? extends E> c, int maxSize) {
        super(c);
        this.maxSize = maxSize;
    }

    public boolean addFirstIfAllowed(E e) {
        if (this.size() < maxSize) {
            addFirst(e);
            return true;
        } else {
            return false;
        }
    }

    public boolean isQueueFull(){
        return this.size() == maxSize;
    }

    @Override
    public void addFirst(E e) {
        if (this.size() < maxSize) {
            super.addFirst(e);
        } else {
            throw new MaxSizeExceededExeption();
        }

    }

    @Override
    public void addLast(E e) {
        if (this.size() < maxSize) {
            super.addLast(e);
        } else {
            throw new MaxSizeExceededExeption();
        }
    }

    @Override
    public boolean add(E e) {
        if (this.size() < maxSize) {
            return super.add(e);
        } else {
            throw new MaxSizeExceededExeption();
        }
    }

    @Override
    public void add(int index, E element) {
        if (this.size() < maxSize) {
            super.add(index, element);
        } else {
            throw new MaxSizeExceededExeption();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.size() < maxSize) {
            return super.addAll(c);
        } else {
            throw new MaxSizeExceededExeption();
        }
    }


    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() < maxSize) {
            return super.addAll(index, c);
        } else {
            throw new MaxSizeExceededExeption();
        }
    }

    @Override
    public String toString() {
        return "FixedSizeQueue{" +
                "maxSize=" + maxSize +
                "} " + super.toString();
    }
}
