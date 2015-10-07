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
		
		public static void printReduceOnSwapResults(PrintStream out, Iterable<OneVarStatistics.Property> props, boolean compact) {
			for (OneVarStatistics.Property p : props) {
				printReduceOnSwapResults(out, p, compact);
			}
		}
		
		public static void printReduceOnSwapResults(PrintStream out, OneVarStatistics.Property p, boolean compact) {
			SortedSet<Integer> swapVals = getSwapValues();
			List<List<Double>> allPropLists = new ArrayList<>();
			for (Integer swap : swapVals) {
				
				SortedMap<Integer, List<Double>> dataMap = new TreeMap<>();
				for (Entry<TestInfo, List<Double>> e : map.entrySet()) {
					TestInfo key = e.getKey();
					if (key.swap != swap)
						continue;
					dataMap.put(key.n, e.getValue());
				}
				
				List<Double> propList = new ArrayList<>(dataMap.size());
				OneVarStatistics stats = new OneVarStatistics();
				for (List<Double> l : dataMap.values()) {
					stats.setData(l);
					propList.add(stats.getProperty(p));
				}
				if (!compact) {
					out.println(String.format("<Cut-off: %1$d>", swap));
					out.print("N = ");
					MatlabUtil.printMatlabArray(dataMap.keySet(), Integer.MAX_VALUE, out);
					out.println();
					out.print(p + " = ");
					MatlabUtil.printMatlabArray(propList, Integer.MAX_VALUE, out);
					out.println();
					out.println();
				}
				allPropLists.add(propList);
			}
			if (compact) {
				out.println("Compact " + p.toString().toLowerCase() + " time results sorted primarily on cut-off and secondarily on n.");
				MatlabUtil.printMatlabArray2D(allPropLists, Integer.MAX_VALUE, out);
			}
		}
	}