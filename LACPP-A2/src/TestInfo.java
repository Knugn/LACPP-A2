
class TestInfo {
	public int n, swap;
	//public ArrayList<Double> msTimes = new ArrayList<>();
	public TestInfo() { }

	public TestInfo(int n, int swap) {
		this.n = n;
		this.swap = swap;
	}

	public TestInfo copy() {
		return new TestInfo(n,swap);
	}

	@Override
	public int hashCode() {
		return n ^ swap;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TestInfo))
			return false;
		TestInfo oti = (TestInfo)o;
		return n == oti.n && swap == oti.swap;
	}

	@Override
	public String toString() {
		return String.format("<TestInfo { n: %1$d, swap: %2$d }>", n, swap);
	}
}
