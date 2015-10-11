import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Find optimal value for changing to sequential sort.
 *
 */
public class Exercise5_2 {

	public static void main(String[] args) {
		TestManager.init();
		//TestInfo ti = new TestInfo();
		int nrCores = 16;
		ArrayList<ArrayList<Double>> allITimes = new ArrayList<>(16);

		ForkJoinPool pool[] = new ForkJoinPool[nrCores];
		for (int i = 0; i < nrCores; i++){
			pool[i] = new ForkJoinPool(nrCores+1);
			allITimes.add(new ArrayList<Double>());
		}
		int nMax = (1024*1024*8);

		int n = 1024*1024*8;	
		//ti.n = n;
		int[] arr = new int[n];
		int numRuns = 10;
		int threshold=64;
		//ti.swap = threshold;
		//System.out.println(ti); 


		for (int run=0; run < numRuns; run++) {
			for (int core = 0; core < nrCores; core++){
				
				ArrayUtils.initRandomArray(arr);

				QuickSortParallel quick = new QuickSortParallel(arr, threshold);

				long t1 = System.nanoTime();
				pool[core].invoke(quick);
				long t2 = System.nanoTime();
				double ms = (t2-t1) / 1000000d;
				allITimes.get(core).add(ms);
				//TestManager.addResult(ti, ms);
				//System.out.println(ms + "ms");

			}

		}
		//TestManager.printResults(System.out);
	/*	OneVarStatistics.Property[] props = OneVarStatistics.Property.values();
		for (OneVarStatistics.Property p : props) {
			TestManager.printReduceOnSwapResults(System.out, p, true);
		}*/
		
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
