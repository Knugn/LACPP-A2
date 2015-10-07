import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;


public class OneVarStatUtils {
	
	public static List<OneVarStatistics> getStatList(Iterable<? extends Collection<Double>> dataList) {
		List<OneVarStatistics> ret = new ArrayList<>();
		for (Collection<Double> data : dataList) {
			ret.add(new OneVarStatistics(data));
		}
		return ret;
	}
	
	public static <K extends Object> SortedMap<K, OneVarStatistics> getStatMap(Map<K, ? extends Collection<Double>> dataMap) {
		SortedMap<K, OneVarStatistics> ret = new TreeMap<>();
		for (Entry<K, ? extends Collection<Double>> kp : dataMap.entrySet()) {
			ret.put(kp.getKey(), new OneVarStatistics(kp.getValue()));
		}
		return ret;
	}
	
	public static List<Double> getMeanList(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getMean());
		}
		return l;
	}
	
	public static List<Double> getMedianList(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getMedian());
		}
		return l;
	}
	
	public static List<Double> getMinList(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getMin());
		}
		return l;
	}
	
	public static List<Double> getMaxList(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getMax());
		}
		return l;
	}
	
	public static List<Double> getQ1List(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getQ1());
		}
		return l;
	}
	
	public static List<Double> getQ3List(Iterable<OneVarStatistics> stats) {
		List<Double> l = new ArrayList<>();
		for (OneVarStatistics stat : stats) {
			l.add(stat.getQ3());
		}
		return l;
	}
	
	public static List<List<Double>> getKeyLists(Iterable<OneVarStatistics> stats, Iterable<String> keyNames) {
		List<List<Double>> ll = new ArrayList<>();
		for (String keyName : keyNames) {
			List<Double> l;
			switch(keyName.toLowerCase()) {
				case "mean":
					l = getMeanList(stats);
					break;
				case "median":
					l = getMedianList(stats);
					break;
				case "min":
					l = getMinList(stats);
					break;
				case "max:":
					l = getMaxList(stats);
					break;
				case "q1":
					l = getQ1List(stats);
					break;
				case "q3":
					l = getQ3List(stats);
					break;
				default:
					throw new IllegalArgumentException("Uknown statistics key name.");
			}
			ll.add(l);
		}
		return ll;
	}
}
