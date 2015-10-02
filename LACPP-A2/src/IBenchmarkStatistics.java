
/**
 * Interface of an object that manages benchmark test results
 * @author David
 *
 * @param <T> A user defined type which can hold information about a benchmark run.
 */
public interface IBenchmarkStatistics<T> {
	/**
	 * Adds a benchmark time
	 * @param info - information about the benchmark which gave this time
	 * @param time - the time in a unit determined by the user (lets agree on ms as standard)
	 * @return an id of the added value which can be used to reference it
	 */
	public void addBenchmarkTime(T info, double time);
	
	
}
