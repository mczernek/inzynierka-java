package com.example.mandown;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class MeasureFragment extends Fragment implements
		MainActivity.UpdatableAccordingToAccelerometer {

	private MeasureFragmentUIHandler uiHandler;

	float[] accelerometerValues;

	private AccelerometerSensorHandler accelerometerSensorHandler;

	public MeasureFragment(Context context) {
		accelerometerSensorHandler = (AccelerometerSensorHandler) context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initiateFields();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedStateInstance) {
		View view = inflater.inflate(R.layout.measure_fragment, null);
		initiateViews(view, accelerometerSensorHandler);
		return view;

	}

	private void initiateFields() {
		accelerometerValues = new float[3];
	}

	private void initiateViews(View view,
			AccelerometerSensorHandler sensorHandler) {
		uiHandler = new MeasureFragmentUIHandler(view, sensorHandler);
	}

	@Override
	public void update(AccelerometerSensorHandler sensorHandler) {
		uiHandler.assignValuesToUI(sensorHandler.getValues());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
