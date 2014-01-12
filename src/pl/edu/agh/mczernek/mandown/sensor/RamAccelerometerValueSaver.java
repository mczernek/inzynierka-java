package pl.edu.agh.mczernek.mandown.sensor;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class RamAccelerometerValueSaver implements AccelerometerValueSaver {

	private List<AccelerometerValue> allValues;

	public RamAccelerometerValueSaver() {
		allValues = new LinkedList<AccelerometerValue>();
	}

	@Override
	public void newValue(AccelerometerValue val) {
		allValues.add(val);
	}

	@Override
	public void newValue(long time, float[] values) {
		allValues.add(new AccelerometerValue(time, values));
	}

	@Override
	public void clearState() {
		allValues.clear();
	}

	@Override
	public List<AccelerometerValue> getCurrentValues() {
		return allValues;
	}

}
