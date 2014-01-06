package pl.edu.agh.mczernek.mandown.filter;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public interface AccelerometerValueFilter {

	public String introduce();

	public AccelerometerValue getFilteredValue(AccelerometerValue val);

}
