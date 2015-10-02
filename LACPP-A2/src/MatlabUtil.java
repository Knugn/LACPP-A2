import java.io.PrintStream;


public class MatlabUtil {
	public static void printMatlabArray(Iterable<Double> l, int elementsPerLine, PrintStream out) {
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
	
	public static void printMatlabArray2D(Iterable<? extends Iterable<Double>> ll, int elementsPerLine, PrintStream out) {
		boolean first = true;
		out.println("[");
		for (Iterable<Double> l : ll) {
			if (first)
				first = false;
			else
				out.println(", ");
			printMatlabArray(l, elementsPerLine, System.out);
		}
		out.println();
		out.println("]");
	}
}
