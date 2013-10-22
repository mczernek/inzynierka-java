package com.example.mandown;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ResultsDatabaseHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "mandown.db";
	public static final String ACCELEROMETER_TABLE_NAME = "AccelerometerResults";
	public static final String TIME_COLUMN_NAME = "time";
	public static final String AXISX_COLUMN_NAME = "axisX";
	public static final String AXISY_COLUMN_NAME = "axisY";
	public static final String AXISZ_COLUMN_NAME = "axisZ";

	public static final String[] ACCELEROMETER_TABLE_COLUMNS = new String[] {
			TIME_COLUMN_NAME, AXISX_COLUMN_NAME, AXISY_COLUMN_NAME,
			AXISZ_COLUMN_NAME };

	public static final String ACCELEROMETER_TABLE_CREATE = "CREATE TABLE "
			+ ACCELEROMETER_TABLE_NAME + " ( " + TIME_COLUMN_NAME
			+ " INTEGER, " + AXISX_COLUMN_NAME + " REAL, " + AXISY_COLUMN_NAME
			+ " REAL, " + AXISZ_COLUMN_NAME + " REAL);";

	public ResultsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(ACCELEROMETER_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) {
		database.execSQL("DROP TABLE IF EXISTS " + ACCELEROMETER_TABLE_NAME);
		database.execSQL(ACCELEROMETER_TABLE_CREATE);
	}

	public void insertValue(AccelerometerValue value) {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(TIME_COLUMN_NAME, value.getTime());
		contentValues.put(AXISX_COLUMN_NAME, value.getValues()[0]);
		contentValues.put(AXISY_COLUMN_NAME, value.getValues()[1]);
		contentValues.put(AXISZ_COLUMN_NAME, value.getValues()[2]);
		database.insert(ACCELEROMETER_TABLE_NAME, null, contentValues);
	}

	public void clearDatabase() {
		SQLiteDatabase database = getWritableDatabase();
		database.delete(ACCELEROMETER_TABLE_NAME, null, null);
	}

	public List<AccelerometerValue> getValues() {
		SQLiteDatabase database = getReadableDatabase();
		List<AccelerometerValue> result = new LinkedList<AccelerometerValue>();
		long time;
		float[] values = new float[3];
		Cursor cursor = database.query(ACCELEROMETER_TABLE_NAME,
				ACCELEROMETER_TABLE_COLUMNS, null, null, null, null, null);
		while (cursor.moveToNext()) {
			time = cursor.getLong(cursor.getColumnIndex(TIME_COLUMN_NAME));
			values[0] = cursor.getFloat(cursor
					.getColumnIndex(AXISX_COLUMN_NAME));
			values[1] = cursor.getFloat(cursor
					.getColumnIndex(AXISY_COLUMN_NAME));
			values[2] = cursor.getFloat(cursor
					.getColumnIndex(AXISZ_COLUMN_NAME));
			result.add(new AccelerometerValue(time, values));
		}
		return result;
	}
}
