import java.util.Random;

public class QuickSortThreaded {
	private static int minSizeForQuick = 40;

	

	public static void main(String[] args) {
		int size[] = {1,2,4,8,16,32,64,128};
		for (int sizeIndex = 0; sizeIndex < size.length; sizeIndex++){
			int arrays[][] = createArrays(size[sizeIndex],10000000);
			for (int i = 0; i < arrays.length; i++){
				threadedQuicksort(arrays[i]);
			}
		}
		//boolean isAsc = isAscending(array[i]);
		//System.out.println(isAsc);
	}

	static boolean isDone(){
		
	}
	
	
	static int[][] createArrays(int nummber, int size){
		int arrays[][] = new int[nummber][];
		for (int i = 0; i < nummber; i++){
			arrays[i] = createArray(size);
		}
		return arrays;
	}

	static int[] createArray(int size){
		int values[] = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++){
			values[i] = rand.nextInt();
		}
		return values;
	}
	static boolean isAscending(int values[]){
		int old = values[0];
		for (int i = 1; i < values.length; i++){
			if (old > values[i])
				return false;
		}
		return true;
	}


	private static void insertionSort(final int[] arr, final int start, final int end) {
		for (int i = start + 1; i < end; ++i) {
			int j = i;
			int tmp = arr[i];
			while (j > 0 && arr[j-1] > tmp) {
				arr[j] = arr[j-1];
				j--;
			}
			arr[j] = tmp;
		}
	}

	// Quicksort (in pseudo code):
		// fun qs(arr):
			//   pivot = some element in arr (e.g., arr[0])
	//   left  = { x | x <- arr if x < pivot}
	//   right = { x | x <- arr if x > pivot}
	//   return qs(left) + {pivot} + qs(right)
	//
	// NOTE: numbers equal to pivot may be included in either left or right.
	//
	private static void sequentialQuicksort(final int[] arr, final int start, final int end) {
		int left = start;
		int right = end + 1;
		// We simply pick the first element as pivot..
		final int pivot = arr[start];
		int tmp;

		// Rearranging the elements around the pivot, so that
		// elements smaller than the pivot end up to the left
		// and elements bigger than the pivot end up to the
		// right.
		do {

			// As long as elements to the left are less than
			// the pivot we just continue.
			do {
				left++;
			} while (left <= end && arr[left] < pivot);

			// As long as the elements to the right are
			// greater than the pivot we just continue.
			do {
				right--;
			} while (arr[right] > pivot);

			// If left is less than right we have values on
			// the wrong side of the pivot, so we swap them.
			if (left < right) {
				tmp = arr[left];
				arr[left] = arr[right];
				arr[right] = tmp;
			}

			// We continue doing this until all elements are
			// arranged correctly around the pivot.
		} while (left <= right);

		// Now put the pivot in the right place.
		tmp = arr[start];
		arr[start] = arr[right];
		arr[right] = tmp;

		// We have now "split" the range arr[start, end] into
		// two parts around the pivot value. We recurse to
		// sort those parts.
		if (start < right) {
			if  (right - start > minSizeForQuick){
				sequentialQuicksort(arr, start, right);
			}
			else{
				insertionSort(arr, start, right);
			}
		}
		if (left < end) {
			if  (end -left > minSizeForQuick){
				sequentialQuicksort(arr, left, end);
			}
			else{
				insertionSort(arr, left, end);
			}
		}
	}

	public static void sequentialQuicksort(final int[] arr) {
		double startTime = System.currentTimeMillis();
		sequentialQuicksort(arr, 0, arr.length-1);
		System.out.print("time taken for quicksort ");
		System.out.println((System.currentTimeMillis() - startTime)/1000);
	}

	public static void threadedQuicksort(final int[] arr){
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				sequentialQuicksort(arr);
			}
		});  
		t1.start();
	}

	public static void parallelQuicksort(final int[] arr) {
		// TODO: implement a parallel quicksort
	}

}