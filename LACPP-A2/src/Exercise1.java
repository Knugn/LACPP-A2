import java.io.OutputStream;
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
	
	public static void PrintMatlabArray(Iterable<Double> l, int elementsPerLine, PrintStream out) {
		boolean first = true;
		int eCount = 0;
		out.print("[");
		for (Double d : l) {
			if (first)
				first = false;
			else
				out.print(", ");
			if (eCount++ >= elementsPerLine)
				out.println("");
			out.print(d);
		}
		out.print("]");
	}
	
	public static void PrinMatlabArray2D(ArrayList<ArrayList<Double>> ll, int elementsPerLine, PrintStream out) {
		boolean first = true;
		out.println("[");
		for (Iterable<Double> l : ll) {
			if (first)
				first = false;
			else
				out.println(", ");
			PrintMatlabArray(l, elementsPerLine, System.out);
		}
		out.println();
		out.print("]");
	}
	
	public static void main(String[] args) {
		int maxi = 40;
		int nRuns = 2;
		ArrayList<ArrayList<Double>> times = new ArrayList<>(maxi);
		for (int run = 0; run < nRuns; run++) {
			ArrayList curRunTimes = new ArrayList(maxi);
			for (int i = 0; i < maxi; i++) {
				long t1 = System.nanoTime();
				fib(i);
				long t2 = System.nanoTime();
				double dt = (t2-t1) / 1000d;
				curRunTimes.add(dt);
			}
			times.add(curRunTimes);
		}
		PrinMatlabArray2D( times, Integer.MAX_VALUE, System.out);
	}
	
}
