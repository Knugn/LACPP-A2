import java.util.ArrayList;


public class Exercise1 {
	
	public static int fib(int i) {
		switch (i) {
			case 0:
				return 0;
			case 1:
				return 1;
			default:
				return fib(i - 1) + fib(i - 2);
		}
	}
	

	
	public static void main(String[] args) {
		int maxi = 40;
		int nRuns = 10;
		ArrayList<ArrayList<Double>> times = new ArrayList<>(nRuns);
		for (int run = 0; run < nRuns; run++) {
			ArrayList curRunTimes = new ArrayList(maxi);
			for (int i = 0; i < maxi; i++) {
				long t1 = System.nanoTime();
				fib(i);
				long t2 = System.nanoTime();
				double dt = (t2-t1) / 1000000d;
				curRunTimes.add(dt);
			}
			times.add(curRunTimes);
			
		}
		
		MatlabUtil.printMatlabArray2D( times, Integer.MAX_VALUE, System.out);
		System.out.println();
		for (int i = 0; i < maxi; i++) {
			ArrayList<Double> iTimes = new ArrayList<>(nRuns);
			for (int run = 0; run < nRuns; run++) {
				iTimes.add(times.get(run).get(i));
			}
			System.out.println("Fib " + i + " computation time (ms) statistics over " + nRuns + " runs.");
			OneVarStatistics stats = new OneVarStatistics(iTimes);
			System.out.println(stats);
		}
	}
	
}
