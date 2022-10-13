package com.company.sorting;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort<T> extends AbstractQuickSort<T> {
    private int DELIMITER;
    private final int DEPTH_LIMIT = 1000;

    public ParallelQuickSort(T[] data, Comparator<? super T> comparator) {
        super(data, comparator);
    }

    @Override
    public void sort(int size) {
        DELIMITER = (getData().length / 100) * (Runtime.getRuntime().availableProcessors() / 2);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new ParallelSortAction(0, size - 1));
        pool.shutdown();
    }
    private class ParallelSortAction extends RecursiveAction{
        private int left;
        private int right;

        public ParallelSortAction(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if(left >= right) return;
            if(depth > DEPTH_LIMIT){
                MergeSort.mergeSort(getComparator(), getData(), left, right);
            }
            else if((right - left) <= DELIMITER){
                new QuickSort<T>(getData(), getComparator()).quickSort(left, right);
            }
            else{
                int middle = partition(left, right);
                depth++;
                invokeAll(new ParallelSortAction(left, middle - 1), new ParallelSortAction(middle + 1, right));
            }
        }
    }
}
