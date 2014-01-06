package pl.edu.agh.mczernek.mandown.sensor;


public class AccelerometerMeasurmentSaverFactory {

	public static final AccelerometerMeasurmentSaver getDefaultSaver() {
		return new AccelerometerMeasurementInterpolatedSaver(10);
		// return new AccelerometerRamMeasurmentsSaver();
	}

}
