package com.example.mandown;

public interface AccelerometerSensorHandler {

	public float[] getValues();

	public void setValues(float[] f);

	public float[] getMaxValues();

	public float[] getMinValues();

}
