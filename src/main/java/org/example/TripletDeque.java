package org.example;
import lombok.Data;

import java.util.*;
@Data
public class TripletDeque <T> implements Deque<T>, Containerable {
    public ArrayWrap<T> last;
    public ArrayWrap<T> first;
    static int len = 0;
    public TripletDeque() {
        ArrayWrap<T> val = new ArrayWrap<T>(null, null);
        this.first = val;
        this.last = val;
    }
    @Override
    public void addFirst(T value) {
        if (value == null) {
            throw new NullPointerException();
        } else if (len > 999) {
            throw new IllegalStateException();
        }
        else {

            if (last == first && last.array[0] != null) {
                ArrayWrap<T> val2 = new ArrayWrap<>(first, null);
                first.prev = val2;
                first = val2;
                val2.array[4] = value;
                len++;
            }
//            if (len != 0) {
//                for (int i = 0; i < 4; i++) {
//                    if (first.array[i] == null && first.array[i+1] != null) {
//                        first.array[i] = value;
//                        len++;
//                    }
//                }
//            }
            else {

                int counter = 0;
                for ( int i = first.array.length - 1; i >= 0; i--){
                    if (first.array[i] == null){
                        first.array[i] = value;
                        len++;
                        break;
                    }
                    else {
                        counter++;
                    }
                }
                if (counter >= 5){
                    ArrayWrap<T> val2 = new ArrayWrap<>(first, null);
                    first.prev = val2;
                    first = val2;
                    val2.array[4] = value;
                    len++;
                }
            }

        }
    }


    @Override
    public void addLast(T value) {
        if (value == null) {
            throw new NullPointerException();
        } else if (len > 999) {
            throw new IllegalStateException();
        }
        else {
            if (last == first && first.array[4] != null) {
                ArrayWrap<T> val2 = new ArrayWrap<>(null, last);
                last.next = val2;
                last = val2;
                val2.array[0] = value;
                len++;
            }
//            if (len != 0) {
//                for (int i = 4; i > 0; i--) {
//                    if (first.array[i] == null && first.array[i-1] != null) {
//                        first.array[i] = value;
//                        len++;
//                    }
//                }
//            }
            else {
                int counter = 0;
                for ( int i = 0; i < last.array.length; i++){
                    if (last.array[i] == null){
                        last.array[i] = value;
                        len++;
                        break;
                    }
                    else {
                        counter++;
                    }
                }
                if (counter >= 5){
                    ArrayWrap<T> val2 = new ArrayWrap<>(null, last);
                    last.next = val2;
                    last = val2;
                    val2.array[0] = value;
                    len++;
                }
            }

        }
    }

    @Override
    public boolean offerFirst(T value) {
        int len_before = len;
        boolean ready;
        addFirst(value);
        ready = (len == len_before + 1);
        return ready;
    }

    @Override
    public boolean offerLast(T value) {
        int len_before = len;
        boolean ready;
        addLast(value);
        ready = (len == len_before + 1);
        return ready;
    }

    @Override
    public T removeFirst() {
        Object deleted = pollFirst();
        if (deleted == null) {
            throw new NoSuchElementException();
        }
        else {
            return (T) deleted;
        }
    }

    @Override
    public T removeLast() {
        Object deleted = pollLast();
        if (deleted == null) {
            throw new NoSuchElementException();
        }
        else {
            return (T) deleted;
        }
    }

    @Override
    public T pollFirst() {
        Object deleted = null;
        if((last.array[0] != null) && (first == last)) {
            deleted = last.array[0];
            last.array[0] = null;
            len--;
            return (T) deleted;
        }
        for (int i = first.array.length - 1; i >= 0; i--) {
            if (first.array[i] == null) {
                if(i == 4) {
                    return null;
                }
                if(i == 3 && first!=last) {
                    deleted = first.array[4];
                    first.array[4] = null;
                    len--;
                    if (first.next != null) { ///////////////!!!!!!!!!!!!!!!
                        first.next.prev = null;
                        first = first.next;
                    }
                    break;
                }
                else if(i < 4) {
                    deleted = first.array[i+1];
                    first.array[i+1] = null;
                    len--;
                    break;
                }
            }
            if(i == 0) {
                deleted = first.array[0];
                first.array[0] = null;
                len--;
            }
        }
        return (T) deleted;
    }

    @Override
    public T pollLast() {
        Object deleted = null;
        if((first.array[4] != null)  && (first == last)) {
            deleted = first.array[4];
            first.array[4] = null;
            len--;
            return (T) deleted;
        }
        for (int i = 0; i < last.array.length; i++) {
            if (last.array[i] == null) {
                if (i == 0 && first!=last) { /////////////////////////////////////////////////////////////////////
                    return null;
                }
                if(i == 1) {
                    deleted = last.array[0];
                    last.array[0] = null;
                    len--;
                    if (last.prev != null) {
                        last.prev.next = null;
                        last = last.prev;
                    }
                    break;
                }
                else if (i > 1){
                    deleted = last.array[i-1];
                    last.array[i-1] = null;
                    len--;
                    break;
                }
            }
            if(i == 4) {
                deleted = last.array[4];
                last.array[4] = null;
                len--;
            }
        }
        return (T) deleted;
    }

    @Override
    public T getFirst() {
        Object found = peekFirst();
        if (found == null) {
            throw  new NoSuchElementException();
        }
        else {
            return (T) found;
        }
    }

    @Override
    public T getLast() {
        Object found = peekLast();
        if (found == null) {
            throw  new NoSuchElementException();
        }
        else {
            return (T) found;
        }
    }

    @Override
    public T peekFirst() {
        if((last.array[0] != null) && (first == last)) {
            return (T) last.array[0];
        }
        Object found = null;
        for (int i = first.array.length - 1; i >= 0; i--) {
            if (first.array[i] == null) {
                if(i == 4) {
                    return null;
                }
                else {
                    found = first.array[i+1];
                    break;
                }
            }
            if(i == 0) {
                found = first.array[0];
            }
        }
        return (T) found;
    }

    @Override
    public T peekLast() {
        if((first.array[4] != null) && (first == last)) {
            return (T) first.array[4];
        }
        Object found = null;
        for (int i = 0; i < last.array.length; i++) {
            if (last.array[i] == null) {
                if (i == 0) {
                    return null;
                }
                else {
                    found = last.array[i-1];
                    break;
                }
            }
            if(i == 4) {
                found = last.array[4];
            }
        }
        return (T) found;
    }

    @Override
    public boolean removeFirstOccurrence(Object deleted) {

        ArrayWrap<T> val = first;
        boolean is_ready = false;
        int counter = 0;
        boolean is_deleted_in_last = false;

        if (first.array[0] != null && first.array[0].equals(deleted)) { // Поиск и удаление элемента в первом триплете
            removeFirst();
            is_ready = true;
        }

        if (!is_ready) {
            for (int i = 0; i < 5; i++) {
                if (first.array[i] == null && first.array[i+1] != null && first.array[i+1].equals(deleted)) {
                    removeFirst();
                    is_ready = true;
                    break;
                }
            }
            ///////////////// test all
            boolean stop = false;
            while (val != null && !is_ready) { // Поиск и удаление элемента во всей очереди
                for (int i = 1; i < 4; i++) { // Поиск и удалние элемента будет в последнем триплете
                    if (last.array[i] == null && last.array[i-1] != null && last.array[i-1].equals(deleted)) {
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
                for (int i = 0; i < 5; i++) {
                    if (val.array[i] != null && val.array[i].equals(deleted)) {
                        val.array[i] = null;
                        len--;
                        is_ready = true;
                        break;
                    }
                    counter++;
                }

                val = val.next;
                if(val == last && !is_ready) {
                    is_deleted_in_last = true;
                }
                if (is_ready) {
                    break;
                }
            }
        }
/////////////////test last
        if (!is_ready) {
            for (int i = 0; i < 4; i++) { // Поиск и удалние элемента в последнем триплете
                if (last.array[i] == null && last.array[i-1] != null && last.array[i-1].equals(deleted)) {
                    removeLast();
                    is_ready = true;
                    break;
                }
            }
        }
        if (!is_ready && last.array[4] != null && last.array[4].equals(deleted)) {
            removeLast();
            is_ready = true;
        }


        if (counter < 6) {      // Поиск пустой ячейки и сдвиг в первом триплете
            for (int i = 4; i > 0; i--) {
                if (first.array[i] == null && first.array[i-1] != null) {
                    first.array[i] = first.array[i-1];
                    first.array[i-1] = null;
                }
            }
        }
        if (is_deleted_in_last) {      // Поиск пустой ячейки и сдвиг в последнем триплете
            for (int i = 0; i < 4; i++) {
                if (last.array[i] == null && last.array[i+1] != null) {
                    last.array[i] = last.array[i+1];
                    last.array[i+1] = null;
                }
            }
            if (last.array == null) {
                last.prev.next = null;
                last = last.prev;
            }
        }
        else { // Поиск пустой ячейки и сдвиг в каждом триплете

            val = first.next;
            while (val != null) {
                for (int i = 0; i < 5; i++) {
                    if(val.array[i] == null) {
                        if (i < 4) {
                            val.array[i] = val.array[i+1];
                            val.array[i+1] = null;
                        }
                        if (i == 4 && val.next != null) {
                            val.array[i] = val.next.array[0];
                            val.next.array[0] = null;
                            if (last.array == null) {
                                last.prev.next = null;
                                last = last.prev;
                            }
                            break;
                        }
                    }
                }
                val = val.next;
            }

        }
        if (first != last) {
            if (first.array[4] == null) {
                first.next.prev = null;
                first = first.next;
            }

            if (last.array[0] == null) {
                last.prev.next = null;
                last = last.prev;
            }

        }

        return is_ready;
    }

    @Override
    public boolean removeLastOccurrence(Object deleted) {
        ArrayWrap<T> val2 = last;
        boolean is_ready = false;
        int counter = 0;
        boolean is_deleted_in_first = false;

        if (last.array[4] != null && last.array[4].equals(deleted)) { // Поиск и удаление элемента в последнем триплете
            removeLast();
            is_ready = true;
        }

        if (!is_ready) {
            for (int i = 4; i >= 0; i--) {
                if (last.array[i] == null && last.array[i-1] != null && last.array[i-1].equals(deleted)) {
                    removeLast();
                    is_ready = true;
                    break;
                }
            }
            ///////////////// test all
            boolean stop = false;
            while (val2 != null && !is_ready) { // Поиск и удаление элемента во всей очереди
                for (int i = 3; i > 0; i--) { // Поиск и удалние элемента будет в первом триплете
                    if (first.array[i] == null && first.array[i+1] != null && first.array[i+1].equals(deleted)) {
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
                for (int i = 4; i >= 0; i--) {
                    if (val2.array[i] != null && val2.array[i].equals(deleted)) {
                        val2.array[i] = null;
                        len--;
                        is_ready = true;
                        break;
                    }
                    counter++;
                }

                val2 = val2.prev;
                if(val2 == first && !is_ready) {
                    is_deleted_in_first = true;
                }
                if (is_ready) {
                    break;
                }
            }
        }
/////////////////test last
        if (!is_ready) {
            for (int i = 4; i > 0; i--) { // Поиск и удалние элемента в первом триплете
                if (first.array[i] == null && first.array[i+1] != null && first.array[i+1].equals(deleted)) {
                    removeFirst();
                    is_ready = true;
                    break;
                }
            }
        }
        if (!is_ready && first.array[0] != null && first.array[0].equals(deleted)) {
            removeFirst();
            is_ready = true;
        }

        if (is_deleted_in_first) {      // Поиск пустой ячейки и сдвиг в первом триплете
            for (int i = 4; i > 0; i--) {
                if (first.array[i] == null && first.array[i-1] != null) {
                    first.array[i] = first.array[i-1];
                    first.array[i-1] = null;
                }
            }
            if (first.array == null) {
                first.next.prev = null;
                first = first.next;
            }
        }
        if (counter < 6) {      // Поиск пустой ячейки и сдвиг в последнем триплете
            for (int i = 0; i < 4; i++) {
                if (last.array[i] == null && last.array[i+1] != null) {
                    last.array[i] = last.array[i+1];
                    last.array[i+1] = null;
                }
            }
            if (last.array == null) {
                last.prev.next = null;
                last = last.prev;
            }
        }
        else { // Поиск пустой ячейки и сдвиг в каждом триплете

            val2 = first.next;
            while (val2 != null) {
                for (int i = 0; i < 5; i++) {
                    if(val2.array[i] == null) {
                        if (i < 4) {
                            val2.array[i] = val2.array[i+1];
                            val2.array[i+1] = null;
                        }
                        if (i == 4 && val2.next != null) {
                            val2.array[i] = val2.next.array[0];
                            val2.next.array[0] = null;
                            if (last.array == null) {
                                last.prev.next = null;
                                last = last.prev;
                            }
                            break;
                        }
                    }
                }
                val2 = val2.next;
            }

        }
        if (first != last) {
            if (first.array[4] == null) {
                first.next.prev = null;
                first = first.next;
            }

            if (last.array[0] == null) {
                last.prev.next = null;
                last = last.prev;
            }
        }

        return is_ready;
    }

    @Override
    public boolean add(T value) {
        boolean is_ok = offer(value);
        if(is_ok) {
            return true;
        }
        else throw new IllegalStateException();
    }

    @Override
    public boolean offer(T value) {
        return offerLast(value);
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int d = len;
        for (T element : c ){
            addLast(element);
        }
        return len - d == c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public void clear() {
        ArrayWrap<T> val = new ArrayWrap<T>(null, null);
        first = val;
        last = val;
        len = 0;
//        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public void push(T value) {
        addLast(value);
    }

    @Override
    public T pop() {
        return pollLast();
    }


    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public boolean contains(Object o) {
        ArrayWrap<T> val = first;
        while (val != null) {
            for (int i = 0; i < 5; i++) {
                if (val.array[i] != null) {
                    if(val.array[i].equals(o)) {
                        return true;
                    }
                }
            }
            val = val.next;
        }
        return false;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public boolean isEmpty() {
        if (len == 0) {
            return true;
        }
        else return false;
    }


    @Override
    public Iterator<T> iterator() {
        return new TripletIterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public Iterator<T> descendingIterator() {
        throw new UnsupportedOperationException("Метод не реализован");
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int ind = 0;
        ArrayWrap<T> val = first;
        while (ind != cIndex) {
            if (val.next != null) {
                val = val.next;
            }
            else {
                return null;
            }
            ind++;
        }
        return (Object[]) val.array;
    }





    public class TripletIterator implements Iterator<T> {

        ArrayWrap<T> val;
        int ind = 0;
        int how_much = len;
        TripletIterator() {val = first;}


        @Override
        public boolean hasNext() {
            return (how_much > 0);
        }

        @Override
        public T next() {
            if (how_much <= 0) {
                throw new NoSuchElementException();
            }
            if (how_much == len) {
                for (int i = 0; i < 5; i++) {

                    if (val.array[i] != null) {
                        ind = i;
                        how_much--;
                        return (T) val.array[ind];
                    }
                }
            }
            else {
                ind++;
                if (ind == 5) {
                    val = val.next;
                    ind = 0;
                }
                how_much--;
                return (T) val.array[ind];
            }
            return null;
        }
    }

}





class ArrayWrap<T> {

    public ArrayWrap(ArrayWrap<T> next, ArrayWrap<T> prev) {
        this.array = new Object[5];
       // this.value = value;
        this.next = next;
        this.prev = prev;
    }

    public String toString() {
        return Arrays.toString(array);
    }

//    @Override
//    public String toString() {
//        return "ArrayWrap{" +
//                "array=" + Arrays.toString(array) +
//                '}';
//    }

    Object [] array;
  //  T value;
    ArrayWrap<T> next;
    ArrayWrap<T> prev;

}