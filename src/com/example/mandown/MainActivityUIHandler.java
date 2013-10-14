package com.example.mandown;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityUIHandler implements OnClickListener {
	private TextView axisXValue;;
	private TextView axisYValue;
	private TextView axisZValue;

	private TextView axisXSnap;
	private TextView axisYSnap;
	private TextView axisZSnap;

	private TextView axisXMax;
	private TextView axisYMax;
	private TextView axisZMax;

	private Button measureButton;
	private Button clearMaxButton;

	private Activity activity;

	Boolean canAssign = true;

	float[] maxValues = new float[3];

	private AccelerometerSensorHandler sensorHandler;

	public MainActivityUIHandler(Activity activity,
			AccelerometerSensorHandler sensorHandler) {
		initializeUIElements(activity);
		initializeSensor(sensorHandler);
	}

	private void initializeSensor(AccelerometerSensorHandler sensorHandler) {
		this.sensorHandler = sensorHandler;
		initializeMaxValues();
	}

	private void initializeMaxValues() {
		maxValues[0] = -100;
		maxValues[1] = -100;
		maxValues[2] = -100;
	}

	public void assignValuesToUI(float[] values) {
		assignCurrentValues(values);
		assignMaxValues(values);
	}

	private void initializeUIElements(Activity activity) {
		initializeTextViews(activity);
		initializeButtons(activity);
	}

	private void initializeButtons(Activity activity) {
		measureButton = (Button) activity.findViewById(R.id.measureButton);
		measureButton.setOnClickListener(this);
		clearMaxButton = (Button) activity.findViewById(R.id.clearMaxButton);
		clearMaxButton.setOnClickListener(this);
	}

	private void initializeTextViews(Activity activity) {
		initializeValueFields(activity);
		initializeCurrentFields(activity);
		initializeMaxFields(activity);
	}

	private void initializeMaxFields(Activity activity) {
		axisXMax = (TextView) activity.findViewById(R.id.axisX_max);
		axisYMax = (TextView) activity.findViewById(R.id.axisY_max);
		axisZMax = (TextView) activity.findViewById(R.id.axisZ_max);
	}

	private void initializeCurrentFields(Activity activity) {
		axisXSnap = (TextView) activity.findViewById(R.id.axisXSnap);
		axisYSnap = (TextView) activity.findViewById(R.id.axisYSnap);
		axisZSnap = (TextView) activity.findViewById(R.id.axisZSnap);
	}

	private void initializeValueFields(Activity activity) {
		axisXValue = (TextView) activity.findViewById(R.id.axisX_value);
		axisYValue = (TextView) activity.findViewById(R.id.axisY_value);
		axisZValue = (TextView) activity.findViewById(R.id.axisZ_value);
	}

	private void assignCurrentValues(float[] values) {
		if (canAssign) {
			axisXValue.setText(Float.toString(values[0]));
			axisYValue.setText(Float.toString(values[1]));
			axisZValue.setText(Float.toString(values[2]));
			blockAssigning();
		}
	}

	private void assignMaxValues(float[] values) {
		if (values[0] > maxValues[0]) {
			axisXMax.setText(Float.toString(values[0]));
			maxValues[0] = values[0];
		}
		if (values[1] > maxValues[1]) {
			axisYMax.setText(Float.toString(values[1]));
			maxValues[1] = values[1];
		}
		if (values[2] > maxValues[2]) {
			axisZMax.setText(Float.toString(values[2]));
			maxValues[2] = values[2];
		}
	}

	private void blockAssigning() {
		Thread a = new Thread(new Runnable() {
			public synchronized void run() {
				try {
					canAssign = false;
					wait(80);
				} catch (InterruptedException ex) {
				} finally {
					canAssign = true;
				}
			}
		});
		a.start();
	}

	private void showErrorMessage() {
		Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_LONG)
				.show();
		axisXValue.setText("Try restarting app!");
		axisYValue.setText(null);
		axisZValue.setText(null);

	}

	@Override
	public void onClick(View button) {
		float[] values = sensorHandler.getValues();

		switch (button.getId()) {
		case R.id.measureButton:
			resetCurrents(values);
			break;
		case R.id.clearMaxButton:
			resetMax(values);
			break;
		}

	}

	private void resetCurrents(float[] values) {
		axisXSnap.setText(Float.toString(values[0]));
		axisYSnap.setText(Float.toString(values[1]));
		axisZSnap.setText(Float.toString(values[2]));
	}

	private void resetMax(float[] values) {
		axisXMax.setText(Float.toString(values[0]));
		axisYMax.setText(Float.toString(values[1]));
		axisZMax.setText(Float.toString(values[2]));
	}
}
