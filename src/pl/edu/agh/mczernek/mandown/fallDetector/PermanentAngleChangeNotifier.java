package pl.edu.agh.mczernek.mandown.fallDetector;

import java.util.LinkedList;

public class PermanentAngleChangeNotifier implements PermanentAngleListener {

	private LinkedList<PermanentAngle> trendsList;

	private long currentTrendBegin;
	private double currentTrendValue;

	private boolean trendInProgress;

	private PermanentAngleRegistrer trendRegistrer;

	public PermanentAngleChangeNotifier(PermanentAngleRegistrer registrer) {
		trendsList = new LinkedList<PermanentAngle>();
		trendRegistrer = registrer;
	}

	@Override
	public void beginOfPermanentAngleDetected(long time, double value) {
		currentTrendBegin = time;
		currentTrendValue = value;
		trendInProgress = true;
		trendRegistrer.onNewAngleDetected(time);
	}

	@Override
	public void endOfPermanentAngleDetected(long endTime, double value) {
		double lastValue;
		trendsList.addFirst(new PermanentAngle(currentTrendBegin, endTime,
				value));
		trendInProgress = false;

	}

	public LinkedList<PermanentAngle> getList() {
		return trendsList;
	}

	public double getLastTrendValue() {
		return currentTrendValue;
	}

	public double getLastTrendBegin() {
		return currentTrendBegin;
	}

	public boolean isTrendInProgress() {
		return trendInProgress;
	}

	public double getTrendsDelta(long startTime) {
		double minValue = 0.0;
		double maxValue = 0.0;
		if (isTrendInProgress()) {
			minValue = currentTrendValue;
			maxValue = currentTrendValue;
		}

		for (PermanentAngle val : trendsList) {

			if (minValue > val.getValue()) {
				minValue = val.getValue();
			}
			if (maxValue < val.getValue()) {
				maxValue = val.getValue();
			}
			if (val.getTimeFinished() < startTime) {
				break;
			}
		}

		return maxValue - minValue;
	}

}
