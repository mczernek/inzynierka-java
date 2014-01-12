package pl.edu.agh.mczernek.mandown.sensor;


public class AccelerometerMeasurmentSolverFactory {

	public static final AccelerometerMeasurmentSolver getDefaultSolver() {
		return new AccelerometerMeasurementInterpolater(10);
		// return new AccelerometerRamMeasurmentsSaver();
	}

}
