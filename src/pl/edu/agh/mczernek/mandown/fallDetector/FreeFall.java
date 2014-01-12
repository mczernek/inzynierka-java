package pl.edu.agh.mczernek.mandown.fallDetector;

public class FreeFall implements Comparable {

	private long beginTime;
	private long endTime;
	private double minValue;

	public FreeFall(long begin, long end, double min) {
		if (begin > end) {
			throw new IllegalArgumentException(
					"Fall cannot end before it began!");
		}
		beginTime = begin;
		endTime = end;
		minValue = min;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public double getMinValue() {
		return minValue;
	}

	public long getDuration() {
		return endTime - beginTime;
	}

	@Override
	public int compareTo(Object toCompare) {
		if (toCompare instanceof FreeFall) {
			FreeFall comparable = (FreeFall) toCompare;
			long difference = this.getDuration() - comparable.getDuration();
			if (difference > 0) {
				return 1;
			} else if (difference == 0) {
				return 0;
			} else {
				return -1;
			}
		} else {
			throw new IllegalArgumentException("Unable to compare "
					+ this.getClass().getName() + " with "
					+ toCompare.getClass().getName());
		}
	}
}
