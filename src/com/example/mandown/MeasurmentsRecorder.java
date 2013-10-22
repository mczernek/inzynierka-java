package com.example.mandown;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.Toast;

public class MeasurmentsRecorder implements SensorEventListener {

	SensorDatabaseManager databaseManager;
	AccelerometerSensorHandler sensorHandler;

	private boolean recordingInProgress = false;

	public MeasurmentsRecorder(Context context,
			AccelerometerSensorHandler sensorHandler) {
		databaseManager = new SensorDatabaseManager(context);
		this.sensorHandler = sensorHandler;
	}

	public void startRecording(MenuButtons buttons) {
		buttons.setRecordingState();
		databaseManager.clearDatabase();
		recordingInProgress = true;
	}

	public void stopRecording(MenuButtons buttons) {
		buttons.setRecordedState();
		recordingInProgress = false;
	}

	public void stopRecordingAndSaveResults(MenuButtons buttons,
			Context context, String filename) {
		stopRecording(buttons);
		try {
			List<AccelerometerValue> list = databaseManager.getValues();
			FileOutputStream fos = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			PrintStream ps = new PrintStream(fos);
			for (AccelerometerValue av : list) {
				ps.print(av.toString());
			}
			ps.close();
		} catch (IOException ex) {
			Toast.makeText(context, "Unable to open/create a file!",
					Toast.LENGTH_LONG).show();
			Log.e("Chuj", ex.getMessage());
		}

		// databaseManager.saveResults(filename);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// Do nothing

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (recordingInProgress) {
			long time = System.currentTimeMillis();
			databaseManager
					.addValue(new AccelerometerValue(time, event.values));
		}
	}
}
