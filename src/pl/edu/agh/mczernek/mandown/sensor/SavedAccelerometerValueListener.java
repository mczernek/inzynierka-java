package pl.edu.agh.mczernek.mandown.sensor;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public interface SavedAccelerometerValueListener {

	public void newValue(AccelerometerValue val);

	public void newValue(long time, float[] values);

}
