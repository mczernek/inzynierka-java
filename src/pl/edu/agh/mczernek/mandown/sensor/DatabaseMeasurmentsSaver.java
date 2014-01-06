package pl.edu.agh.mczernek.mandown.sensor;

import java.util.List;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

import android.content.Context;

public class DatabaseMeasurmentsSaver implements AccelerometerMeasurmentSaver{

	SensorDatabaseManager databaseManager;

	public DatabaseMeasurmentsSaver(Context context) {
		databaseManager = new SensorDatabaseManager(context);
	}

	@Override
	public void addValue(long time, float[] values) {
		addValue(new AccelerometerValue(time, values));
		
	}

	@Override
	public void addValue(AccelerometerValue val) {
		databaseManager.addValue(val);
		
	}

	@Override
	public void clearState() {
		databaseManager.clearDatabase();
	}

	@Override
	public List<AccelerometerValue> getCurrentValues() {
		return databaseManager.getValues();
	}
}
