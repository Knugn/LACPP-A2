import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Find optimal value for changing to insertion sort,
 *
 */
public class Exercise2_7_a {
	
	static class TestInfo {
		public int n, swap;
		//public ArrayList<Double> msTimes = new ArrayList<>();
		public TestInfo() { }
		
		public TestInfo(int n, int swap) {
			this.n = n;
			this.swap = swap;
		}
		
		public TestInfo copy() {
			return new TestInfo(n,swap);
		}
		
		@Override
		public int hashCode() {
			return n ^ swap;
		}
		
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TestInfo))
				return false;
			TestInfo oti = (TestInfo)o;
			return n == oti.n && swap == oti.swap;
		}
		
		@Override
		public String toString() {
			return String.format("<TestInfo { n: %1$d, swap: %2$d }>", n, swap);
		}
	}
	
	static class TestManager {
		static HashMap<TestInfo, List<Double>> map;
		
		public static void init() {
			init(16, 0.75f);
		}
		
		public static void init(int initialCapacity, float loadFactor) {
			map = new HashMap<>(initialCapacity, loadFactor);
		}
		
		public static void addResult(int n, int swap, double ms) {
			TestInfo key = new TestInfo(n,swap);
			addResult(key, ms);
		}
		
		public static void addResult(TestInfo key, double ms) {
			List<Double> val = map.get(key);
			if (val == null) {
				val = new ArrayList<>();
				map.put(key.copy(), val);
			}
			val.add(ms);
		}
		
		public static void printResults(PrintStream out) {
			printDetailedPerTestConfigurationResult(out);
			printReduceOnSwapResults(out);
		}
		
		public static void printDetailedPerTestConfigurationResult(PrintStream out) {
			OneVarStatistics stats = new OneVarStatistics();
			for (Entry<TestInfo, List<Double>> e : map.entrySet()) {
				TestInfo ti = e.getKey();
				out.println(ti);
				stats.setData(e.getValue());
				out.println(stats);
			}
		}
		
		public static SortedSet<Integer> getSwapValues() {
			SortedSet<Integer> swapVals = new TreeSet<Integer>();
			for (TestInfo ti : map.keySet()) {
				swapVals.add(ti.swap);
			}
			return swapVals;
		}
		
		public static SortedSet<Integer> getNValues() {
			SortedSet<Integer> nVals = new TreeSet<Integer>();
			for (TestInfo ti : map.keySet()) {
				nVals.add(ti.n);
			}
			return nVals;
		}
		
		public static void printReduceOnSwapResults(PrintStream out) {
			SortedSet<Integer> swapVals = getSwapValues();
			for (Integer swap : swapVals) {
				out.println(String.format("<Swap to insertion sort at: %1$d>", swap));
				SortedMap<Integer, List<Double>> results = new TreeMap<>();
				for (Entry<TestInfo, List<Double>> e : map.entrySet()) {
					TestInfo key = e.getKey();
					if (key.swap != swap)
						continue;
					results.put(key.n, e.getValue());
				}
				out.print("N = ");
				MatlabUtil.printMatlabArray(results.keySet(), Integer.MAX_VALUE, out);
				out.println();
				
				List<Double> avgMsResults = new ArrayList<>(results.size());
				OneVarStatistics stats = new OneVarStatistics();
				for (List<Double> l : results.values()) {
					stats.setData(l);
					avgMsResults.add(stats.getMean());
				}
				out.print("MeanTimes = ");
				MatlabUtil.printMatlabArray(avgMsResults, Integer.MAX_VALUE, out);
				out.println();
				out.println();
			}
		}
	}
	
	public static void main(String[] args) {
		TestManager.init();
		TestInfo ti = new TestInfo();
		Quicksort qs = new Quicksort();
		int nMax = (1024*1024*8);
		for (int n=nMax/64; n <= nMax; n*=2) {
			ti.n = n;
			int[] arr = new int[n];
			int numRuns = nMax / n * 2; //Integer.numberOfTrailingZeros(nMax / n) + 1;
			//System.out.println(numRuns);
			for (int swap=2; swap <= 1024; swap*=2) {
				ti.swap = swap;
				System.out.println(ti);
				//qs.setMinSizeForQuicksort(swap);
				for (int run=0; run < numRuns; run++) {
					ArrayUtils.initRandomArray(arr);
					
					long t1 = System.nanoTime();
					qs.sequentialQuicksort(arr);
					long t2 = System.nanoTime();
					double ms = (t2-t1) / 1000000;
					TestManager.addResult(ti, ms);
					System.out.println(ms + "ms");
				}
			}
		}
		TestManager.printResults(System.out);
	}
	
}
