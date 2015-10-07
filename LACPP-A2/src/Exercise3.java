import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Exercise3 {

	public static void main(String[] args) {
		Quicksort quicksort = new Quicksort();
		int Ns[] = {1,2,4,8,16,32,64,128};
		int size = 500000;
		List<List<Double>> allTimeTaken = new ArrayList<>();
		for (int sizeIndex = 0; sizeIndex < Ns.length; sizeIndex++){
			List<Double> timeTaken = new ArrayList<Double>();
			for (int n = 0; n < 5; n++){
				Thread [] threds = new Thread[Ns[sizeIndex]];
				int arrays[][] = ArrayUtils.createArrays(Ns[sizeIndex],size);

				long time = System.nanoTime();
				for (int i = 0; i < arrays.length; i++){
					threds[i] = quicksort.threadedQuicksort(arrays[i]);
				}
				for (int i = 0; i < arrays.length; i++){
					try {
						threds[i].join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				long endTime = System.nanoTime();
				double totalTimeTaken = (endTime - time)/1000000d;
				timeTaken.add(totalTimeTaken);
				System.out.println("Number of threads: " + Ns[sizeIndex]);
				System.out.println("Time taken: " + totalTimeTaken + "ms");
			}
			allTimeTaken.add(timeTaken);
		}
		
		String[] keyStatNamesArr = {"Mean", "Max", "Q3", "Median", "Q1", "Min"};
		List<String> keyStatNamesList = new ArrayList<>();
		for (String s : keyStatNamesArr) {
			keyStatNamesList.add(s);
		}
		List<OneVarStatistics> allTimeTakenStats = OneVarStatUtils.getStatList(allTimeTaken);
		List<List<Double>> keyStatLists = OneVarStatUtils.getKeyLists(allTimeTakenStats, keyStatNamesList);
		
		PrintStream out = System.out;
		out.println();
		out.print("Key statisical value list in order: ");
		MatlabUtil.printMatlabArray(keyStatNamesList, Integer.MAX_VALUE, out);
		out.println();
		MatlabUtil.printMatlabArray2D(keyStatLists, Integer.MAX_VALUE, out);
	}
}
