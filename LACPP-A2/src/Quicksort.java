import java.util.Random;

public class Quicksort {
	private int minSizeForQuick = 4000;
	
	public Quicksort(int minSizeForQuick) {
		this.minSizeForQuick = minSizeForQuick;
	}
	
	

	
	public static int[] createArray(int size){
		int values[] = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++){
			values[i] = rand.nextInt();
		}
		return values;
	}
	
	public static boolean isAscending(int values[]){
		for (int i = 1; i < values.length; i++){
			if (values[i-1] > values[i]){
				System.out.println(values[i-1] + " " + i);
				System.out.println(values[i]);
				return false;
			}
		}
		return true;
	}
	
	/**
     * Sorts interval [start, end) of arr sequentially
     * @param arr
     * @param start
     * @param end
     */
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
    /**
     * Sorts interval [start, end) of arr sequentially
     * @param arr
     * @param start
     * @param end
     */
    private void sequentialQuicksort(final int[] arr, final int start, final int end) {
        int left = start;
        int right = end;
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
            } while (left < end && arr[left] < pivot);

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
        if (right - start > 1) {
        	
        	if  (right - start > minSizeForQuick){
        		sequentialQuicksort(arr, start, right);
        	}
        	else{
        		insertionSort(arr, start, right);
        	}
        }
        if (end - left > 1) {
        	if  (end - left > minSizeForQuick){
        		sequentialQuicksort(arr, left, end);
        	}
        	else{
        		insertionSort(arr, left, end);
        	}
        }
    }

    public void sequentialQuicksort(final int[] arr) {
    	double startTime = System.currentTimeMillis();
        sequentialQuicksort(arr, 0, arr.length);
        System.out.print("time taken for quicksort ");
        System.out.println((System.currentTimeMillis() - startTime)/1000);
    }

    
    public static void parallelQuicksort(final int[] arr) {
        // TODO: implement a parallel quicksort
    }

}
