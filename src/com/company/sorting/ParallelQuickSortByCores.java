package com.company.sorting;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSortByCores<T> extends AbstractQuickSort<T>{
    @Override
    public void sort(Comparator<? super T> comparator, T[] data, int size) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        pool.invoke(new ParallelSortAction(data, 0, size - 1, comparator));
        pool.shutdown();
    }
    private class ParallelSortAction extends RecursiveAction {
        private T[] data;
        private int left;
        private int right;
        private final int SHEDULE;
        private Comparator<? super T> comparator;

        public ParallelSortAction(T[] data, int left, int right, Comparator<? super T> comparator) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.comparator = comparator;
            SHEDULE = data.length / Runtime.getRuntime().availableProcessors();
        }

        @Override
        protected void compute() {
            if(right - left + 1 <= SHEDULE) return;
            else{
                int middle = partition(data, left, right, comparator);

                invokeAll(new ParallelSortAction(data, left, middle - 1 ,comparator), new ParallelSortAction(data, middle + 1, right, comparator));
            }
        }
    }
}
