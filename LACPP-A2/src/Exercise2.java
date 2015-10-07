
public class Exercise2 {
	
	public static void main(String[] args) {
		Quicksort quick = new Quicksort(40);
		int array[] = ArrayUtils.createArray(10000000);
		quick.sequentialQuicksort(array);
		//boolean isAsc = Quicksort.isAscending(array);
		//System.out.println(isAsc);
	}
	
}
