import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class OneVarStatistics {
	
	double[] data;
	double sum;
	double mean;
	double variance;
	double deviation;
	double median;
	double q1, q3;
	
	public OneVarStatistics() {
		this(null);
	}
	
	public OneVarStatistics(Collection<Double> data) {
		setData(data);
	}
	
	public int getDataSize() {
		return data.length;
	}
	
	public double getValue(int idx) {
		return data[idx];
	}
	
	public double getSum() {
		return sum;
	}
	
	public double getMean() {
		return mean;
	}
	
	public double getVariance() {
		return variance;
	}
	
	public double getDeviation() {
		return deviation;
	}
	
	public double getMedian() {
		return median;
	}
	
	public double getQ1() {
		return q1;
	}
	
	public double getQ3() {
		return q3;
	}
	
	public double getQuantile(int quantile) {
		switch(quantile) {
			case 1:
				return getQ1();
			case 2:
				return getMedian();
			case 3: 
				return getQ3();
			default:
				throw new IllegalArgumentException("Quantile must be 1, 2 or 3.");
		}
	}
	
	public double getMin() {
		return data[0];
	}
	
	public double getMax() {
		return data[data.length - 1];
	}
	
	public List<Double> getData() {
		ArrayList<Double> c = new ArrayList<>(this.data.length);
		for (int i = 0; i < this.data.length; i++) {
			c.add(this.data[i]);
		}
		return c;
	}
	
	public void setData(Collection<Double> data) {
		if (data == null)
			this.data = null;
		if (data.size() == 0)
			throw new IllegalArgumentException("data must have at least 1 value.");
		this.data = new double[data.size()];
		int i=0;
		for (Double d : data) {
			this.data[i++] = d;
		}
		Arrays.sort(this.data);
		updateSum();
		updateMean();
		updateVariance();
		updateDeviation();
		updateMedian();
		updateQuantiles();
	}

	private void updateSum() {
		sum = calculateSum(data);
	}
	
	private void updateMean() {
		mean = sum / data.length;
	}
	
	private void updateVariance() {
		variance = calculateVariance(data, mean);
	}

	private void updateDeviation() {
		deviation = Math.sqrt(variance);
	}
	
	private void updateMedian() {
		if (data.length % 2 == 1)
			median = data[data.length/2];
		else {
			int upper = data.length/2;
			median = (data[upper-1] + data[upper]) / 2;
		}
	}
	
	private void updateQuantiles() {
		if (data.length % 2 == 0) {
			int h = data.length / 2;
			if (h % 2 == 1) {
				int q = h / 2;
				q1 = data[q];
				q3 = data[data.length - 1 - q];
			}
			else {
				int qUpper = h / 2;
				q1 = (data[qUpper-1] / data[qUpper]) / 2;
				qUpper += h;
				q3 = (data[qUpper-1] / data[qUpper]) / 2;
			}
		}
		else {
			
		}
		int q = data.length / 4;
		int r = data.length % 4;
		switch (r) {
			case 0:
				q1 = (data[q-1] + data[q]) / 2;
				q *= 3;
				q3 = (data[q-1] + data[q]) / 2;
				break;
			case 2:
				q1 = data[q];
				q3 = data[data.length - 1 - q];
				break;
			case 1:
				if (q == 0) {
					q1 = data[0];
					q3 = data[0];
				}
				q1 = (data[q-1] + data[q] * 3) / 4;
				q *= 3;
				q3 = (data[q] * 3 + data[q+1]) / 4;
				break;
			case 3:
				q1 = (data[q] * 3 + data[q+1]) / 4;
				q *= 3;
				q3 = (data[q+1] + data[q+2] * 3) / 4;
				break;
			default:
				throw new RuntimeException("This should never happen.");
					
		}
	}

	public static double calculateSum(double[] data) {
		double sum = 0;
		for (int i = 0; i < data.length; i++) {
			sum += data[i];
		}
		return sum;
	}
	
	public static double calculateMean(double[] data) {
		return calculateSum(data) / data.length;
	}
	
	public static double calculateVariance(double[] data, double mean) {
		if (data.length == 1)
			return Double.MAX_VALUE;
		double sum = 0;
		for (int i = 0; i < data.length; i++) {
			double d = data[i] - mean;
			sum += d*d;
		}
		return sum / (data.length - 1);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String br = System.lineSeparator();
		sb.append("Data size: " + getDataSize() + br);
		sb.append("Sum:       " + getSum() + br);
		sb.append("Mean:      " + getMean() + br);
		sb.append("Variance:  " + getVariance() + br);
		sb.append("Deviation: " + getDeviation() + br);
		sb.append("Min:       " + getMin() + br);
		sb.append("Q1:        " + getQ1() + br);
		sb.append("Median:    " + getMedian() + br);
		sb.append("Q3:        " + getQ3() + br);
		sb.append("Max:       " + getMax() + br);
		return sb.toString();
	}
}
