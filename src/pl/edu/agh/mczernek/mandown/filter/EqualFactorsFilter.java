package pl.edu.agh.mczernek.mandown.filter;

public class EqualFactorsFilter extends FIRFilter {

	private String name;

	public EqualFactorsFilter(String name, int factorsNumber) {
		super();
		this.name = name;

		double[] factors = new double[factorsNumber];
		for (int i = 0; i < factors.length; ++i) {
			factors[i] = 1.0 / factorsNumber;
		}

		setFactors(factors);
	}

	@Override
	public String introduce() {
		return name;
	}

}
