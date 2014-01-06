package pl.edu.agh.mczernek.mandown.sensor;

public interface AccelerometerSensorHandler {

	public float[] getValues();

	public void setValues(float[] f);

	public float[] getMaxValues();

	public float[] getMinValues();

}
