package com.example.mandown;

import java.util.List;

import android.content.Context;

public class SensorDatabaseManager {
	private ResultsDatabaseHelper databaseHelper;

	public SensorDatabaseManager(Context context) {
		databaseHelper = new ResultsDatabaseHelper(context);
	}

	public void close() {
		databaseHelper.close();
	}

	public void addValue(AccelerometerValue value) {
		databaseHelper.insertValue(value);
	}

	public List<AccelerometerValue> getValues() {
		return databaseHelper.getValues();
	}

	public void clearDatabase() {
		databaseHelper.clearDatabase();
	}
}
