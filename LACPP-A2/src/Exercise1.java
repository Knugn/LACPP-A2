
public class Exercise1 {
	
	public static int fib(int i) {
		switch (i) {
			case 0: 
				return 0;
			case 1: 
				return 1;
			default:
				return fib(i-1) + fib(i-2);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(fib(3));
	}
	
}
