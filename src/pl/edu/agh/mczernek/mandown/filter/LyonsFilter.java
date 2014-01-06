package pl.edu.agh.mczernek.mandown.filter;

public class LyonsFilter extends FIRFilter {

	public static double[] FACTORS = { 0.04f, 0.22f, 0.4f, 0.22f, 0.04f };

	public LyonsFilter() {
		super(FACTORS);
	}

	@Override
	public String introduce() {
		return "5-cell filter with factors according to Lyons";
	}

}
