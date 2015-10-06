import java.util.concurrent.RecursiveAction;



public class QuickSortParallel extends RecursiveAction {

	static Quicksort quicksort = new Quicksort();
	
	int start; 
	int end;
	int[] arr;
	int threshold = 10000;
	/**
	 * 
	 */
	private static final long serialVersionUID = 17897987987L;

	
    public QuickSortParallel(int [] arr, int threshold){
        this.arr=arr;
        this.start = 0;
        this.end = arr.length;
        this.threshold = threshold;
    }

	
	public QuickSortParallel(int[] arr, int start, int end, int threshold) {
		 this.start = start; 
		 this.end = end;
		 this.arr = arr;
		 this.threshold = threshold;
	}


	
	   
	/**
	 * Sorts interval [start, end) of arr sequentially
	 * @param arr
	 * @param start
	 * @param end
	 */
	private int parition() {
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

		
		return left;
	}


	@Override
	protected void compute() {
	      if (start < end){
	    	  if (end - start < threshold){
	    		  quicksort.sequentialQuicksort(arr, start, end);
	    	  }
	    	  else{
	            int pivotIndex = parition();
		        invokeAll(new QuickSortParallel(arr, start, pivotIndex-1,threshold),
	                      new QuickSortParallel(arr, pivotIndex, end,threshold));
	    	  }
	        }
		
	}





}






