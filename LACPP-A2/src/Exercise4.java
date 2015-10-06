
public class Exercise4 {
 
	public static void main(String[] args) {
		int array[] = ArrayUtils.createArray(10000000);
		int threshold = 10000;
		QuickSortParallel quick = new QuickSortParallel(array, threshold);
		long t1 = System.nanoTime();
		quick.compute();
		//Arrays.sort(array);
		System.out.println("Time taken: " + (System.nanoTime()-t1)/1000000000d);
		System.out.println("Is sorted " + Quicksort.isAscending(array));

	}
	
}
