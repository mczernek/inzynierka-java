package pl.edu.agh.mczernek.mandown.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.edu.agh.mczernek.mandown.fallDetector.FreeFall;
import pl.edu.agh.mczernek.mandown.fallDetector.FreeFallDetector;
import pl.edu.agh.mczernek.mandown.fallDetector.FreeFallListener;

public class FreeFallDetectorTest {

	@Mock
	private FreeFallListener listenerMock;

	private FreeFallDetector detector;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		detector = new FreeFallDetector(5, 0.2, 10);

		detector.registerFreeFallListener(listenerMock);
	}

	@Test
	public void testDetectorExists() {

		detector.newValue(0, new float[] { 10f, 0f, 0f });
		detector.newValue(10, new float[] { 9f, 0f, 0f });
		detector.newValue(20, new float[] { 8f, 0f, 0f });
		detector.newValue(50, new float[] { 7f, 0f, 0f });
		detector.newValue(60, new float[] { 6f, 0f, 0f });
		detector.newValue(70, new float[] { 6.3f, 0f, 0f });
		detector.newValue(80, new float[] { 6.5f, 0f, 0f });

		Mockito.verify(listenerMock, Mockito.times(1)).freeFallDetected(
				Mockito.any(FreeFall.class));
	}
}
