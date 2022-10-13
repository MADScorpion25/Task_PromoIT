package com.company.sorting;

import com.company.interfaces.Sorting;

import java.util.Comparator;

public class QuickSort<T> extends AbstractQuickSort<T>{
    public void quickSort(T[] data, Comparator<? super T> comparator, int left, int right){
        if(left >= right) return;
        int middle = partition(data, left, right, comparator);

        quickSort(data, comparator, left, middle - 1);
        quickSort(data, comparator, middle + 1, right);
    }

    @Override
    public void sort(Comparator<? super T> comparator, T[] data, int size) {
        quickSort(data, comparator, 0, size - 1);
    }
}
