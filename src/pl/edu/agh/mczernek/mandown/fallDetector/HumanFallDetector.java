package pl.edu.agh.mczernek.mandown.fallDetector;

import java.util.LinkedList;

public class HumanFallDetector implements FreeFallListener,
		PermanentAngleRegistrer {

	private long minFreeFallLength;
	private double maxFreeFallLowBoundary;
	private long maxEventLength;

	private LinkedList<FreeFall> fallsList;

	private LinkedList<HumanFallListener> listenersList;

	private ThreeAxisPermanentAngleDetector permanentAnglesDetector;
	private FreeFallDetector freeFallDetector;

	private PermanentAngleChangeNotifier xListener;
	private PermanentAngleChangeNotifier yListener;
	private PermanentAngleChangeNotifier zListener;

	public HumanFallDetector(long maxEventLength, long minFallLength,
			double maxFallLowBoundary, ThreeAxisPermanentAngleDetector angles,
			FreeFallDetector falls) {

		minFreeFallLength = minFallLength;
		maxFreeFallLowBoundary = maxFallLowBoundary;
		this.maxEventLength = maxEventLength;
		fallsList = new LinkedList<FreeFall>();
		xListener = new PermanentAngleChangeNotifier(this);
		yListener = new PermanentAngleChangeNotifier(this);
		zListener = new PermanentAngleChangeNotifier(this);
		permanentAnglesDetector.registerListeners(xListener, yListener,
				zListener);
		freeFallDetector.registerFreeFallListener(this);

	}

	@Override
	public void freeFallDetected(FreeFall detected) {
		if (detected.getDuration() >= minFreeFallLength
				&& detected.getMinValue() <= maxFreeFallLowBoundary) {
			fallsList.add(detected);
		}
	}

	public void registerHumanFallListener(HumanFallListener listener) {
		listenersList.add(listener);
	}

	private void notifyListeners(FreeFall fall, double anglesChange) {
		for (HumanFallListener lis : listenersList) {
			lis.onFallDetected(fall, anglesChange);
		}
	}

	@Override
	public void onNewAngleDetected(long time) {
		if (lastFall(time) < maxEventLength) {
			double anglesChange = anglesChangeSum(time - maxEventLength);
			if (anglesChange > 110) {
				notifyListeners(fallsList.getLast(), anglesChange);
			}
		} else {
			fallsList.clear();
		}
	}

	private double anglesChangeSum(long time) {
		double xChange = xListener.getTrendsDelta(time);
		double yChange = yListener.getTrendsDelta(time);
		double zChange = zListener.getTrendsDelta(time);
		return xChange + yChange + zChange;
	}

	private long lastFall(long time) {
		return time - fallsList.getLast().getBeginTime();
	}
}
