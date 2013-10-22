package com.example.mandown;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SensorDatabaseManager {
	private SQLiteDatabase database;
	private ResultsDatabaseHelper databaseHelper;

	public SensorDatabaseManager(Context context) {
		databaseHelper = new ResultsDatabaseHelper(context);
	}

	public void open() {
		database = databaseHelper.getWritableDatabase();
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
