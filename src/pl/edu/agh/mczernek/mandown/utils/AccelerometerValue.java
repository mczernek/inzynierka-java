package pl.edu.agh.mczernek.mandown.utils;

public class AccelerometerValue {

	private float[] values;
	private long time;

	public AccelerometerValue(long time, float[] values) {
		this.time = time;
		if (null != values) {
			this.values = new float[values.length];
			System.arraycopy(values, 0, this.values, 0, values.length);
		}
	}

	public long getTime() {
		return time;
	}

	public float[] getValues() {
		return values;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(time);
		sb.append("\t");
		sb.append(values[0]);
		sb.append("\t");
		sb.append(values[1]);
		sb.append("\t");
		sb.append(values[2]);
		sb.append("\t\n");
		return sb.toString();
	}
}
