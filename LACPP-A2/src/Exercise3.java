import java.util.ArrayList;


public class Exercise3 {
	
	public static void main(String[] args) {
		Quicksort quicksort = new Quicksort();
		int size[] = {1,2,4,8,16,32,64,128};

		for (int sizeIndex = 0; sizeIndex < size.length; sizeIndex++){
			Thread [] threds = new Thread[size[sizeIndex]];
			int arrays[][] = ArrayUtils.createArrays(size[sizeIndex],2000000);
			long time = System.currentTimeMillis();
			for (int i = 0; i < arrays.length; i++){
				threds[i] = (quicksort.threadedQuicksort(arrays[i]));
			}
			for (int i = 0; i < arrays.length; i++){
				try {
					threds[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Nummber of threads: " + size[sizeIndex]);
			System.out.println("time taken is: " + (System.currentTimeMillis() - time)/1000d);
		}
		//boolean isAsc = isAscending(array[i]);
		//System.out.println(isAsc);
	}
}
