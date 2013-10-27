package com.example.mandown;

import java.util.List;

public interface AccelerometerMeasurmentSaver {

	public void addValue(long time, float[] values);
	
	public void addValue(AccelerometerValue val);
	
	public void clearState();
	
	public List<AccelerometerValue> getCurrentValues();
	
}