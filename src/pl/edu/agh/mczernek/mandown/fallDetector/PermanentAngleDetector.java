package pl.edu.agh.mczernek.mandown.fallDetector;

import java.util.LinkedList;
import java.util.List;

public class PermanentAngleDetector {

	private final static long INVALID_TIME = -1;
	private final static double GRAVITY = 9.81;

	private long count = 1;
	private long currentTime = INVALID_TIME;
	private double currentMean = 0.0;

	private long threshold;
	private double tolerance;
	private long period;

	private long trendBeginTime;

	private List<PermanentAngleListener> listenersList;

	public PermanentAngleDetector(long threshold, double tolerance, long period) {
		this.threshold = threshold;
		this.tolerance = tolerance;
		new LinkedList<PermanentAngleListener>();
	}

	public void registerListener(PermanentAngleListener listener) {
		listenersList.add(listener);
	}

	public void unregisterListener(PermanentAngleListener listener) {
		listenersList.remove(listener);
	}

	public void newValue(long time, float value) {
		long countInc = (time - currentTime) / period;
		if (Math.abs(value - currentMean) < tolerance) {
			processDetectingTrend(time, value, countInc);
		} else {
			processTrendBroken(time, value);
		}
		refreshValues(time);
	}

	private void refreshValues(long time) {
		currentTime = time;
	}

	private void processTrendBroken(long time, float value) {
		if (count > threshold) {
			trendFinished(time);
		}
		trendBroken(value);
	}

	private void trendBroken(float value) {
		count = 1;
		currentMean = value;
	}

	private void trendFinished(long time) {
		notifyEndOfTrend(time, getCurrentAngle());
	}

	private double getCurrentAngle() {
		double angle = Math.asin(currentMean / GRAVITY) * 180 / Math.PI;
		return angle;
	}

	private void processDetectingTrend(long time, float value, long countInc) {
		if (count == 1) {
			trendBeginTime = time;
		}
		refreshTrendParameters(time, value, countInc);
	}

	private void refreshTrendParameters(long time, float value, long countInc) {
		count += countInc;
		currentMean += (value - currentMean) / count;
		if (count > threshold) {
			notifyBeginOfTrend(trendBeginTime, getCurrentAngle());
		}
	}

	private void notifyBeginOfTrend(long time, double value) {
		for (PermanentAngleListener lis : listenersList) {
			lis.beginOfPermanentAngleDetected(time, value);
		}
	}

	private void notifyEndOfTrend(long time, double value) {
		for (PermanentAngleListener lis : listenersList) {
			lis.endOfPermanentAngleDetected(time, value);
		}
	}
}
