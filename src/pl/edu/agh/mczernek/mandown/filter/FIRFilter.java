package pl.edu.agh.mczernek.mandown.filter;

import java.util.LinkedList;

import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;

public abstract class FIRFilter implements AccelerometerValueFilter {

	protected int length;
	protected double[] factors;

	protected LinkedList<AccelerometerValue> filterValues;

	/***
	 * After using this constructor setFactors must be called!
	 */
	protected FIRFilter() {
		filterValues = new LinkedList<AccelerometerValue>();
	}

	protected FIRFilter(double[] factors) {
		setFactors(factors);

		filterValues = new LinkedList<AccelerometerValue>();

	}

	@Override
	public abstract String introduce();

	public static AccelerometerValue calculateConvolution(
			LinkedList<AccelerometerValue> values, double[] factors)
			throws IllegalArgumentException {
		if (values.size() > factors.length) {
			throw new IllegalArgumentException(
					"Values and factors dimensions must match");
		} else if (values.size() < factors.length) {
			return values.getLast();
		}

		long time = values.getLast().getTime();
		float valueX = 0.0f;
		float valueY = 0.0f;
		float valueZ = 0.0f;

		int i = 0;
		for (AccelerometerValue val : values) {
			valueX += val.getValues()[0] * factors[i];
			valueY += val.getValues()[1] * factors[i];
			valueZ += val.getValues()[2] * factors[i];
			i++;
		}

		return new AccelerometerValue(time, new float[] { valueX, valueY,
				valueZ });
	}

	@Override
	public AccelerometerValue getFilteredValue(AccelerometerValue val) {
		addValue(val);
		return calculateConvolution(filterValues, factors);
	}

	protected void setFactors(double[] factors) {
		this.factors = factors;
		this.length = factors.length;
	}

	private void addValue(AccelerometerValue val) {
		if (filterValues.size() == length) {
			filterValues.removeFirst();
		}
		filterValues.addLast(val);
	}
}
