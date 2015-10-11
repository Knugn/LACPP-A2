import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Find optimal value for changing to sequential sort.
 *
 */
public class Exercise5_3 {

	public static void main(String[] args) {
		int maxCores = 16;
		ArrayList<ArrayList<Double>> allITimes = new ArrayList<>(16);

		ForkJoinPool pool[] = new ForkJoinPool[maxCores];
		for (int i = 0; i < maxCores; i++){
			pool[i] = new ForkJoinPool(i+1);
			allITimes.add(new ArrayList<Double>());
		}

		int n = 1024*1024;
		int[][] arr = new int[maxCores][];
		for (int i = 0; i < maxCores; i++){
			arr[i] = new int[n*(i+1)];
		}
		int numRuns = 10;
		int threshold=64;
		
		for (int run=0; run < numRuns; run++) {
			for (int core = 0; core < maxCores; core++){
				
				ArrayUtils.initRandomArray(arr[core]);

				QuickSortParallel quick = new QuickSortParallel(arr[core], threshold);

				long t1 = System.nanoTime();
				pool[core].invoke(quick);
				long t2 = System.nanoTime();
				double ms = (t2-t1) / 1000000d;
				allITimes.get(core).add(ms);
			}
		}
		
		List<OneVarStatistics> statList = OneVarStatUtils.getStatList(allITimes);
		String[] keyStatNamesArr = {"Mean", "Max", "Q3", "Median", "Q1", "Min"};
		List<String> keyStatNamesList = new ArrayList<>();
		for (String s : keyStatNamesArr) {
			keyStatNamesList.add(s);
		}
		List<List<Double>> keyStatLists = OneVarStatUtils.getKeyLists(statList, keyStatNamesList);
		
		PrintStream out = System.out;
		out.println();
		out.print("Key statisical value list in order: ");
		MatlabUtil.printMatlabArray(keyStatNamesList, Integer.MAX_VALUE, out);
		out.println();
		MatlabUtil.printMatlabArray2D(keyStatLists, Integer.MAX_VALUE, out);

	}

}
