package com.company.sorting;

import com.company.interfaces.Sorting;

import java.util.Comparator;

public abstract class AbstractQuickSort<T> implements Sorting<T> {
    public int partition(T[] data, int left, int right, Comparator<? super T> comparator){
        T supportItem = data[right];
        int leftCursor = left;
        int rightCursor = right;
        while(true){
            while(comparator.compare(data[leftCursor], supportItem) <= 0 && leftCursor < rightCursor) leftCursor++;
            while(comparator.compare(data[rightCursor], supportItem) >= 0 && leftCursor < rightCursor) rightCursor--;
            if(leftCursor >= rightCursor) break;
            swapItems(data, leftCursor, rightCursor);
        }
        swapItems(data, right, rightCursor);
        return rightCursor;
    }
    private void swapItems(T[] data, int firstIndex, int secondIndex){
        T temp = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = temp;
    }
}
