package com.example.mandown;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerListener implements SensorEventListener {

	AccelerometerSensorHandler sensorHandler;

	public AccelerometerListener(AccelerometerSensorHandler sensorHandler) {
		this.sensorHandler = sensorHandler;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int newAccuracy) {
		// doNothing();

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		sensorHandler.setValues(event.values);
	}

}
