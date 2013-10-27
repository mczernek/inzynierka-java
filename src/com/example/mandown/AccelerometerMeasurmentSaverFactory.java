package com.example.mandown;


public class AccelerometerMeasurmentSaverFactory {

	public static final AccelerometerMeasurmentSaver getDefaultSaver(){
		return new AccelerometerRamMeasurmentsSaver();
	}

}
