import java.util.LinkedList;


public class Exercise3 {

	public static void main(String[] args) {
		Quicksort quicksort = new Quicksort();
		int Ns[] = {1,2,4,8,16,32,64,128};
		int size = 500000;
		LinkedList<LinkedList<Double>> allTimeTaken = new LinkedList<LinkedList<Double>>();
		for (int sizeIndex = 0; sizeIndex < Ns.length; sizeIndex++){
			 LinkedList<Double> timeTaken = new LinkedList<Double>();
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
				System.out.println("Nummber of threads: " + Ns[sizeIndex]);
				System.out.println("time taken is: " + totalTimeTaken);
			}
			allTimeTaken.add(timeTaken);
		}

	}
}
