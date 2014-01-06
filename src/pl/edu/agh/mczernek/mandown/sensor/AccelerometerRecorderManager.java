package pl.edu.agh.mczernek.mandown.sensor;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import pl.edu.agh.mczernek.mandown.MenuButtons;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.Toast;

public class AccelerometerRecorderManager implements SensorEventListener {

	private static final String TAG = "AccelerometerRecordingManager";

	AccelerometerMeasurmentSaver measurmentSaver;
	MenuButtons buttons;

	private boolean recordingInProgress = false;

	public AccelerometerRecorderManager(Context context,
			AccelerometerSensorHandler sensorHandler, MenuButtons buttons) {
		measurmentSaver = AccelerometerMeasurmentSaverFactory.getDefaultSaver();
		this.buttons = buttons;
	}

	public void startRecording() {
		buttons.setRecordingState();
		recordingInProgress = true;
	}

	public void stopRecording() {
		buttons.setRecordedState();
		recordingInProgress = false;
	}

	public void saveResults(Context context, String filename) {
		stopRecording();
		List<AccelerometerValue> values = measurmentSaver.getCurrentValues();
		File plik = new File(context.getExternalFilesDir(null), filename);
		try {
			// plik.createNewFile();
			Log.d(this.getClass().getCanonicalName(),
					"Path to file: " + plik.getAbsolutePath());
			PrintStream ps = new PrintStream(plik);
			for (AccelerometerValue val : values) {
				ps.print(val);
			}
			ps.close();
			values.clear();
			buttons.setSavedState();
		} catch (IOException ex) {
			Toast.makeText(context, "Unable to access a file!",
					Toast.LENGTH_SHORT).show();
			Log.e(TAG, "Unable to write to a file! " + ex.getMessage());
		} finally {

		}

	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// Do nothing

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (recordingInProgress) {
			long time = System.currentTimeMillis();
			measurmentSaver
					.addValue(new AccelerometerValue(time, event.values));
		}
	}

}
