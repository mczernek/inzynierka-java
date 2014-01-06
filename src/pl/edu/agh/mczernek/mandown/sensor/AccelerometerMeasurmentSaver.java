package pl.edu.agh.mczernek.mandown.sensor;

import java.util.List;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public interface AccelerometerMeasurmentSaver {

	public void addValue(long time, float[] values);
	
	public void addValue(AccelerometerValue val);
	
	public void clearState();
	
	public List<AccelerometerValue> getCurrentValues();
	
}