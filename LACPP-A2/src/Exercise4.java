import java.util.concurrent.ForkJoinPool;


public class Exercise4 {

	public static void main(String[] args) {
			
		int maxThreshold = 16384;
		int startSize = 65536;
		int maxSize = 67108864;

		for (int size = startSize; size < maxSize; size*=2){
			for (int threshold = 2; threshold < maxThreshold; threshold*=2){
				for (int i = 0; i < 5; i++){
					int array[] = ArrayUtils.createArray(size);
					QuickSortParallel quick = new QuickSortParallel(array, threshold);
					long t1 = System.nanoTime();
					ForkJoinPool pool = new ForkJoinPool();
					pool.invoke(quick);
					long endTime = System.nanoTime();

					System.out.println("Threshold is: " + threshold + " size is: " + size);
					System.out.println("Time taken: " + (endTime-t1)/1000000d);

				}
			}
		}

	}

}
