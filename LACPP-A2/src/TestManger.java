import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;


	class TestManager {
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
			List<List<Double>> allAvgMsResults = new ArrayList<>();
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
				allAvgMsResults.add(avgMsResults);
			}
			out.println("Compact mean time results sorted primarily on swap and secondarily on n.");
			MatlabUtil.printMatlabArray2D(allAvgMsResults, Integer.MAX_VALUE, out);
		}
	}