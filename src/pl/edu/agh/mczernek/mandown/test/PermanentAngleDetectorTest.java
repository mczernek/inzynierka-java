package pl.edu.agh.mczernek.mandown.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.edu.agh.mczernek.mandown.fallDetector.PermanentAngleDetector;
import pl.edu.agh.mczernek.mandown.fallDetector.PermanentAngleListener;

public class PermanentAngleDetectorTest {
	@Mock
	private PermanentAngleListener listenerMock;

	private PermanentAngleDetector detector;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		detector = new PermanentAngleDetector(5, 1, 10);

		detector.registerListener(listenerMock);

	}

	@Test
	public void testOne() {
		detector.newValue(0, 1.5f);
		detector.newValue(10, 2);
		detector.newValue(20, 2.5f);
		detector.newValue(30, 1.5f);
		detector.newValue(40, 2);
		detector.newValue(50, 2);
		detector.newValue(60, 100);

		Mockito.verify(listenerMock, Mockito.times(1))
				.beginOfPermanentAngleDetected(Mockito.anyLong(),
						Mockito.anyDouble());

		Mockito.verify(listenerMock, Mockito.times(1))
				.endOfPermanentAngleDetected(Mockito.anyLong(),
						Mockito.anyDouble());

	}
}
