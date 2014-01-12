package pl.edu.agh.mczernek.mandown.sensor;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public interface AccelerometerMeasurmentSolver {

	public void addValue(long time, float[] values);

	public void addValue(AccelerometerValue val);

	public void registerSavedAccelerometerValueListener(
			SavedAccelerometerValueListener listener);

	public void unregisterSavedAccelerometerValueListener(
			SavedAccelerometerValueListener listener);

}