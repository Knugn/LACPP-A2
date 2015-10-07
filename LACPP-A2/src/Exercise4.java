import java.util.concurrent.ForkJoinPool;


public class Exercise4 {
 
	public static void main(String[] args) {
		int array[] = ArrayUtils.createArray(100000000);
		int threshold = 10000;
		QuickSortParallel quick = new QuickSortParallel(array, threshold);
		long t1 = System.nanoTime();
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(quick);
		//Arrays.sort(array);
		System.out.println("Time taken: " + (System.nanoTime()-t1)/1000000000d);
		System.out.println("Is sorted " + Quicksort.isAscending(array));

	}
	
}
