package pl.edu.agh.mczernek.mandown;

import java.util.List;

import pl.edu.agh.mczernek.mandown.sensor.AccelerometerListener;
import pl.edu.agh.mczernek.mandown.sensor.AccelerometerRecorderManager;
import pl.edu.agh.mczernek.mandown.sensor.AccelerometerSensorHandler;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mandown.R;

public class MainActivity extends Activity implements ActionBar.TabListener,
		AccelerometerSensorHandler {

	public interface UpdatableAccordingToAccelerometer {
		public void update(AccelerometerSensorHandler sensorHandler);
	}

	private SaveToFileDialogFragment saveDialog;

	private SensorManager sensorManager;
	private List<Sensor> accelerometersList;
	private Sensor maximumResolutionAccelerometerSensor;
	private AccelerometerListener accelerometerListener;

	private MenuButtons data;
	private AccelerometerRecorderManager recorder;

	private Tab measureTab;
	private Tab chartsTab;

	private ActionBar actionBar;

	private MeasureFragment measureFragment;
	private DumbToDatabaseFragment databaseFragment;

	private Boolean[] handlerSynchronizerObject = new Boolean[0];

	float[] accelerometerValues;
	float[] maxAccelerometerValues;
	float[] minAccelerometerValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		actionBar = getActionBar();

		measureFragment = new MeasureFragment(this);
		databaseFragment = new DumbToDatabaseFragment();

		measureTab = actionBar.newTab().setTag(measureFragment)
				.setText("Values").setTabListener(this);
		chartsTab = actionBar.newTab().setTag(databaseFragment)
				.setText("Charts").setTabListener(this);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.addTab(measureTab, true);
		actionBar.addTab(chartsTab, false);

		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);

		setContentView(R.layout.activity_main);

		initializeSensor();
		initiateValues();
		accelerometerValues = new float[3];

		accelerometerListener.add(measureFragment);

		saveDialog = new SaveToFileDialogFragment();

		super.onCreate(savedInstanceState);

		Log.d("CHUJ",
				"Sensor range: "
						+ maximumResolutionAccelerometerSensor
								.getMaximumRange() + ", resolution: "
						+ maximumResolutionAccelerometerSensor.getResolution()
						+ "delay: "
						+ maximumResolutionAccelerometerSensor.getMinDelay());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		data = new MenuButtons(menu);
		data.setInitialState();

		recorder = new AccelerometerRecorderManager(this, this, data);

		startProcessing();

		return true;
	}

	private void initializeSensor() {
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

	private void initiateValues() {
		final int minimumValue = -100;
		final int maximumValue = 100;
		minAccelerometerValues = new float[] { maximumValue, maximumValue,
				maximumValue };
		maxAccelerometerValues = new float[] { minimumValue, minimumValue,
				minimumValue };

	}

	private void startProcessing() {
		sensorManager.registerListener(accelerometerListener,
				maximumResolutionAccelerometerSensor,
				SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(recorder,
				maximumResolutionAccelerometerSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public void setValues(float[] values) {
		assert (3 == values.length);
		synchronized (handlerSynchronizerObject) {
			setXValue(values[0]);
			setYValue(values[1]);
			setZValue(values[2]);
		}
	}

	private void setXValue(float value) {
		accelerometerValues[0] = value;
		if (value > maxAccelerometerValues[0]) {
			maxAccelerometerValues[0] = value;
		}
		if (value < minAccelerometerValues[0]) {
			minAccelerometerValues[0] = value;
		}
	}

	private void setYValue(float value) {
		accelerometerValues[1] = value;
		if (value > maxAccelerometerValues[1]) {
			maxAccelerometerValues[1] = value;
		}
		if (value < minAccelerometerValues[1]) {
			minAccelerometerValues[1] = value;
		}
	}

	private void setZValue(float value) {
		accelerometerValues[2] = value;
		if (value > maxAccelerometerValues[2]) {
			maxAccelerometerValues[2] = value;
		}
		if (value < minAccelerometerValues[2]) {
			minAccelerometerValues[2] = value;
		}
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

	@Override
	public float[] getMaxValues() {
		return maxAccelerometerValues;
	}

	@Override
	public float[] getMinValues() {
		return minAccelerometerValues;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.play_button:
			recorder.startRecording();
			return true;
		case R.id.stop_button:
			recorder.stopRecording();
			return true;
		case R.id.save_button:
			saveDialog.show(getFragmentManager(), "Si", recorder);
			return true;
		case R.id.action_settings:
			showSettings();
			return true;
		default:
			return false;
		}
	}

	private void showSettings() {
		Intent intent = new Intent("pl.edu.agh.mczernek.mandown.SETTINGS");
		startActivity(intent);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragments, (Fragment) tab.getTag());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}