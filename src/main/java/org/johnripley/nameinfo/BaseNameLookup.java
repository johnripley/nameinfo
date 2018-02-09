package org.johnripley.nameinfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseNameLookup implements NameLookup {

	Logger logger = LogManager.getLogger(BaseNameLookup.class);

	private Map<String, Map<String, Integer>> males = new HashMap<>();
	private Map<String, Map<String, Integer>> females = new HashMap<>();

	private Map<String, Integer> maleTotalByYear = new HashMap<>();
	private Map<String, Integer> femaleTotalByYear = new HashMap<>();

	private int startYear;
	private int endYear;

	public BaseNameLookup() {
		this(1880, 2016);
	}

	public BaseNameLookup(int start, int end) {
		this.startYear = start;
		this.endYear = end;

		for (int year = startYear; year <= endYear; year++) {
			maleTotalByYear.put(String.valueOf(year), 0);
			femaleTotalByYear.put(String.valueOf(year), 0);
			try {
				loadResource("names/yob" + year + ".txt", String.valueOf(year));
			} catch (Exception exc) {
				logger.error("Unable to load year " + year, exc);
			}
		}
		summarize();
		logger.info("Loaded " + males.size() + " unique male names from " + startYear + " to " + endYear);
		logger.info("Loaded " + females.size() + " unique female names from " + startYear + " to " + endYear);
	}

	protected void loadResource(String fileName, String year) throws Exception {
		fileName = "/" + fileName;
		logger.info("Loading resource as stream: " + fileName);
		try (InputStream is = getClass().getResourceAsStream(fileName)) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while ((line = reader.readLine()) != null) {
					processLine(line, year);
				}
			}
		}
	}

	protected void summarize() {

		for (String name : males.keySet()) {
			Map<String, Integer> stats = males.get(name);
			int cnt = stats.values().stream().mapToInt(i -> i.intValue()).sum();
			stats.put("all", cnt);
		}

		for (String name : females.keySet()) {
			Map<String, Integer> stats = females.get(name);
			int cnt = stats.values().stream().mapToInt(i -> i.intValue()).sum();
			stats.put("all", cnt);
		}

		int allMales = maleTotalByYear.values().stream().mapToInt(i -> i.intValue()).sum();
		maleTotalByYear.put("all", allMales);

		int allFemales = femaleTotalByYear.values().stream().mapToInt(i -> i.intValue()).sum();
		femaleTotalByYear.put("all", allFemales);

	}

	private void processLine(String line, String year) {
		String[] parts = line.split(",");
		String name = parts[0].toLowerCase();
		String gender = parts[1].toLowerCase();
		int count = Integer.parseInt(parts[2]);

		switch (gender) {
		case "m": {
			Map<String, Integer> namestat = males.get(name);
			if (namestat == null) {
				namestat = new HashMap<>();
				males.put(name, namestat);
			}
			namestat.put(year, count);
			maleTotalByYear.put(year, maleTotalByYear.get(year) + count);

			break;
		}
		default: {
			Map<String, Integer> namestat = females.get(name);
			if (namestat == null) {
				namestat = new HashMap<>();
				females.put(name, namestat);
			}
			namestat.put(year, count);
			femaleTotalByYear.put(year, femaleTotalByYear.get(year) + count);
			break;
		}
		}
	}

	public GenderInfo getGender(NameInfo nameInfo) {
		NameStats ns = getStatistic(nameInfo);
		int males = ns.getCountMale();
		int females = ns.getCountFemale();
		int total = males + females;
		if (total == 0) {
			return new BaseGenderInfo(nameInfo.getName(), nameInfo.getYear(), "U", 0.0d);
		} else if (males > females) {
			double confidence = (double) males / (double) total;
			return new BaseGenderInfo(nameInfo.getName(), nameInfo.getYear(), "M",
					Math.round(confidence * 1000) / 1000.0d);

		} else {
			double confidence = (double) females / (double) total;
			return new BaseGenderInfo(nameInfo.getName(), nameInfo.getYear(), "F",
					Math.round(confidence * 1000) / 1000.0d);
		}
	}

	@Override
	public NameStats getStatistic(NameInfo nameInfo) {
		BaseNameStats stats = new BaseNameStats(nameInfo.getName(), nameInfo.getYear());
		Map<String, Integer> maleStats = males.get(nameInfo.getName().toLowerCase());
		Map<String, Integer> femaleStats = females.get(nameInfo.getName().toLowerCase());

		String year = nameInfo.getYear() == 0 ? "all" : String.valueOf(nameInfo.getYear());
		if (maleStats != null && maleStats.get(year) != null)
			stats.setCountMale(maleStats.get(year));
		if (femaleStats != null && femaleStats.get(year) != null)
			stats.setCountFemale(femaleStats.get(year));
		if (maleTotalByYear.get(year) != null)
			stats.setTotalMale(maleTotalByYear.get(year));
		if (femaleTotalByYear.get(year) != null)
			stats.setTotalFemale(femaleTotalByYear.get(year));
		return stats;
	}

	@Override
	public NameLookupInfo getInfo() {
		return new BaseNameLookupInfo(this.startYear, endYear);
	}
}
