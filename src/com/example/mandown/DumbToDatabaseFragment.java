package com.example.mandown;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DumbToDatabaseFragment extends Fragment implements
		MainActivity.UpdatableAccordingToAccelerometer {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedStateInstance) {
		return inflater.inflate(R.layout.dumbtodatabase_layout, null);
	}

	@Override
	public void update(AccelerometerSensorHandler sensorHandler) {
		// TODO Auto-generated method stub

	}
}
