package pl.edu.agh.mczernek.mandown.sensor;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public class AccelerometerRamMeasurmentsSaver implements AccelerometerMeasurmentSaver{
	
	List<AccelerometerValue> valuesList;

	public AccelerometerRamMeasurmentsSaver() {
		valuesList = new LinkedList<AccelerometerValue>();
	}

	public void addValue(long time, float[] values){
		valuesList.add(new AccelerometerValue(time, values));
	}
	
	public void clearState(){
		valuesList.clear();
	}
	
	public List<AccelerometerValue> getCurrentValues(){
		return valuesList;
	}

	@Override
	public void addValue(AccelerometerValue val) {
		valuesList.add(val);		
	}

}
