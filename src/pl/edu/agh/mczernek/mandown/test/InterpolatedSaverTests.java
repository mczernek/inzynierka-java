package pl.edu.agh.mczernek.mandown.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pl.edu.agh.mczernek.mandown.sensor.AccelerometerMeasurementInterpolatedSaver;
import pl.edu.agh.mczernek.mandown.utils.AccelerometerValue;


public class InterpolatedSaverTests {

	@Test
	public void testClassAddsFirstTwoValue() {
		AccelerometerMeasurementInterpolatedSaver saver = new AccelerometerMeasurementInterpolatedSaver(
				10);
		AccelerometerValue firstVal = createValue(0, 0.0, 0.1, 0.1);
		saver.addValue(firstVal);
		assertTrue(saver.getCurrentValues().size() == 1);

		AccelerometerValue secVal = createValue(5, 0.0, 0.0, 0.0);
		saver.addValue(secVal);
		assertTrue(saver.getCurrentValues().size() == 1);

		AccelerometerValue thirdVal = createValue(15, 1.0, 1.0, 1.0);
		saver.addValue(thirdVal);
		assertTrue(saver.getCurrentValues().size() == 2);
		assertTrue(saver.getCurrentValues().get(1).getTime() == 10);
		assertEquals(saver.getCurrentValues().get(1).getValues()[0], 0.5, 0.01);

		AccelerometerValue fourthVal = createValue(75, 1.0, 1.0, 1.0);
		saver.addValue(fourthVal);
		assertTrue(saver.getCurrentValues().size() == 3);
		assertEquals(70, saver.getCurrentValues().get(2).getTime());
		assertEquals(1.0, saver.getCurrentValues().get(2).getValues()[0], 0.01);

		AccelerometerValue fifthVal = createValue(95, 0.0, 0.0, 0.0);
		saver.addValue(fifthVal);
		assertEquals(4, saver.getCurrentValues().size());
		assertEquals(90, saver.getCurrentValues().get(3).getTime());
		assertEquals(0.25, saver.getCurrentValues().get(3).getValues()[0], 0.01);

		AccelerometerValue sixthVal = createValue(115, 0.0, 0.0, 0.0);
		saver.addValue(sixthVal);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue seven = createValue(115, 0.0, 0.0, 0.0);
		saver.addValue(seven);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue eight = createValue(116, 0.0, 0.0, 0.0);
		saver.addValue(eight);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue nine = createValue(117, 0.0, 0.0, 0.0);
		saver.addValue(nine);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue ten = createValue(118, 0.0, 0.0, 0.0);
		saver.addValue(ten);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue eleven = createValue(119, 0.0, 0.0, 0.0);
		saver.addValue(eleven);
		assertEquals(5, saver.getCurrentValues().size());
		assertEquals(110, saver.getCurrentValues().get(4).getTime());

		AccelerometerValue twelve = createValue(121, 0.0, 0.0, 0.0);
		saver.addValue(twelve);
		assertEquals(6, saver.getCurrentValues().size());
		assertEquals(120, saver.getCurrentValues().get(5).getTime());
	}

	private AccelerometerValue createValue(long time, double x, double y,
			double z) {
		return new AccelerometerValue(time, new float[] { (float) x, (float) y,
				(float) z });
	}
}
