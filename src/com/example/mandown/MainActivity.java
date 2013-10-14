package com.example.mandown;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements
		AccelerometerSensorHandler {

	private SensorManager sensorManager;
	private List<Sensor> accelerometersList;
	private Sensor maximumResolutionAccelerometerSensor;
	private AccelerometerListener accelerometerListener;

	private MainActivityUIHandler uiHandler;

	private Boolean[] handlerSynchronizerObject = new Boolean[0];

	float[] accelerometerValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initiateFields();
		startProcessing();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initiateFields() {
		initiateViews();
		initiateSensor();
		accelerometerValues = new float[3];
	}

	private void initiateViews() {
		uiHandler = new MainActivityUIHandler(this, this);
	}

	private void initiateSensor() {
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometersList = sensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);
		maximumResolutionAccelerometerSensor = getMaximumResolutionSensor(accelerometersList);
		accelerometerListener = new AccelerometerListener(this);
	}

	private Sensor getMaximumResolutionSensor(List<Sensor> sensors) {
		Sensor maxRes = null;
		for (Sensor s : sensors) {
			if (null == maxRes)
				maxRes = s;
			else
				maxRes = maxRes.getResolution() > s.getResolution() ? maxRes
						: s;
		}
		return maxRes;
	}

	private void startProcessing() {
		sensorManager.registerListener(accelerometerListener,
				maximumResolutionAccelerometerSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void setValues(float[] values) {
		assert (3 == values.length);
		synchronized (handlerSynchronizerObject) {
			accelerometerValues[0] = values[0];
			accelerometerValues[1] = values[1];
			accelerometerValues[2] = values[2];
		}
		uiHandler.assignValuesToUI(accelerometerValues);
	}

	@Override
	public float[] getValues() {
		float[] result = new float[3];
		synchronized (accelerometerValues) {
			result[0] = accelerometerValues[0];
			result[1] = accelerometerValues[1];
			result[2] = accelerometerValues[2];
		}
		return result;
	}
}
