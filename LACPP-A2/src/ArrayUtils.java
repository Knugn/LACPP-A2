import java.util.Random;


public class ArrayUtils {
	public static void initRandomArray(int[] arr) {
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt();
		}
	}
	
	public static int[] createArray(int size){
		int values[] = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++){
			values[i] = rand.nextInt();
		}
		return values;
	}
}
