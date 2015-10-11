import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


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
		int mini = 12;
		int maxi = 32;
		int numi = maxi - mini + 1;
		int nRuns = 10;
		List<List<Double>> allRunTimes = new ArrayList<>(nRuns);
		for (int run = 0; run < nRuns; run++) {
			ArrayList<Double> curRunTimes = new ArrayList<>(numi);
			for (int i = mini; i <= maxi; i++) {
				long t1 = System.nanoTime();
				fib(i);
				long t2 = System.nanoTime();
				double dt = (t2-t1) / 1000000d;
				curRunTimes.add(dt);
			}
			allRunTimes.add(curRunTimes);
			
		}
		ArrayList<ArrayList<Double>> allITimes = new ArrayList<>(nRuns);
		
		//MatlabUtil.printMatlabArray2D( allRunTimes, Integer.MAX_VALUE, System.out);
		//System.out.println();
		for (int i = mini; i <= maxi; i++) {
			ArrayList<Double> iTimes = new ArrayList<>(nRuns);
			for (int run = 0; run < nRuns; run++) {
				iTimes.add(allRunTimes.get(run).get(i - mini));
			}
			allITimes.add(iTimes);
			/*
			System.out.println("Fib " + i + " computation time (ms) statistics over " + nRuns + " runs.");
			OneVarStatistics stats = new OneVarStatistics(iTimes);
			System.out.println(stats);*/
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
