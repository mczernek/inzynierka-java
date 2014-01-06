package pl.edu.agh.mczernek.mandown.filter;

import java.util.LinkedList;

public class FilterFactory {

	private static AccelerometerValueFilter currentFilter;

	private static int currentFilterIndex;

	private static String ONE_CELL_FILTER_NAME = "No filtration virtual filter";
	private static String FIVE_CELLS_FILTER_NAME = "Filter with 5 equal cells";
	private static String EIGHT_CELLS_FILTER_NAME = "Filter with 8 equal cells";
	private static String TEN_CELLS_FILTER_NAME = "Filter with 10 equal cells";
	private static String FIFTEEN_CELLS_FILTER_NAME = "Filter with 15 equal cells";

	private static LinkedList<AccelerometerValueFilter> filters;

	static {
		filters = new LinkedList<AccelerometerValueFilter>();

		addNoFilter();
		addLyonsFilter();
		add5CellsFilter();
		add8CellsFilter();
		add10CellsFilter();
		add15CellsFilter();

		currentFilter = filters.getFirst();
		currentFilterIndex = 0;
	}

	public static String[] getFiltersNames() {
		String[] result = new String[filters.size()];

		int i = 0;
		for (AccelerometerValueFilter filter : filters) {
			result[i] = filter.introduce();
			i++;
		}

		return result;
	}

	public static synchronized void setFilter(int position)
			throws IllegalArgumentException {
		if (position < 0 || position >= filters.size()) {
			throw new IllegalArgumentException(
					"Position must be greater than zero and within range (there are "
							+ filters.size()
							+ "available filters, and position " + position
							+ " is not accesible!");
		}

		currentFilter = filters.get(position);
		currentFilterIndex = position;
	}

	public static synchronized AccelerometerValueFilter getFilter() {
		return currentFilter;
	}

	public static synchronized int getFilterIndex() {
		return currentFilterIndex;
	}

	public static void addFilter(AccelerometerValueFilter filter) {
		filters.addLast(filter);
	}

	private static void addLyonsFilter() {
		filters.addLast(new LyonsFilter());
	}

	private static void addNoFilter() {
		filters.addLast(new EqualFactorsFilter(ONE_CELL_FILTER_NAME, 1));
	}

	private static void add5CellsFilter() {
		filters.addLast(new EqualFactorsFilter(FIVE_CELLS_FILTER_NAME, 5));
	}

	private static void add8CellsFilter() {
		filters.addLast(new EqualFactorsFilter(EIGHT_CELLS_FILTER_NAME, 8));
	}

	private static void add10CellsFilter() {
		filters.addLast(new EqualFactorsFilter(TEN_CELLS_FILTER_NAME, 10));
	}

	private static void add15CellsFilter() {
		filters.addLast(new EqualFactorsFilter(FIFTEEN_CELLS_FILTER_NAME, 15));
	}

}
