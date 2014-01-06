package pl.edu.agh.mczernek.mandown.sensor;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.MainActivity;
import pl.edu.agh.mczernek.mandown.MainActivity.UpdatableAccordingToAccelerometer;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerListener implements SensorEventListener {

	List<MainActivity.UpdatableAccordingToAccelerometer> updatableList;
	AccelerometerSensorHandler sensorHandler;

	public AccelerometerListener(AccelerometerSensorHandler sensorHandler) {
		this.sensorHandler = sensorHandler;
		updatableList = new LinkedList<MainActivity.UpdatableAccordingToAccelerometer>();
	}

	public void add(UpdatableAccordingToAccelerometer updatable) {
		if (!updatableList.contains(updatable)) {
			updatableList.add(updatable);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int newAccuracy) {
		// doNothing();

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		updateSensorHandler(event);
		updateUpdatables();
	}

	private void updateUpdatables() {
		for (UpdatableAccordingToAccelerometer up : updatableList) {
			up.update(sensorHandler);
		}
	}

	private void updateSensorHandler(SensorEvent event) {
		sensorHandler.setValues(event.values);
	}

}
