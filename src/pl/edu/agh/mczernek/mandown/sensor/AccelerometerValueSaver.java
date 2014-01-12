package pl.edu.agh.mczernek.mandown.sensor;

import java.util.List;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public interface AccelerometerValueSaver extends
		SavedAccelerometerValueListener {

	public void clearState();

	public List<AccelerometerValue> getCurrentValues();

}
