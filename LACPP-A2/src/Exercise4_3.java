import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Find optimal value for changing to insertion sort,
 *
 */
public class Exercise4_3 {
	


	
	public static void main(String[] args) {
		TestManager.init();
		TestInfo ti = new TestInfo();
		int nMax = (1024*1024*8);
		for (int n=nMax/64; n <= nMax; n*=2) {
			ti.n = n;
			int[] arr = new int[n];
			int numRuns = nMax / n * 10; //Integer.numberOfTrailingZeros(nMax / n) + 1;
			//System.out.println(numRuns);
			for (int threshold=2; threshold <= 262144; threshold*=2) {
				ti.swap = threshold;
				System.out.println(ti);
				for (int run=0; run < numRuns; run++) {
					ArrayUtils.initRandomArray(arr);
					
					QuickSortParallel quick = new QuickSortParallel(arr, threshold);
					ForkJoinPool pool = new ForkJoinPool();
					long t1 = System.nanoTime();
					pool.invoke(quick);
					long t2 = System.nanoTime();
					double ms = (t2-t1) / 1000000;
					TestManager.addResult(ti, ms);
					//System.out.println(ms + "ms");
				}
			}
		}
		TestManager.printResults(System.out);
	}
	
}
