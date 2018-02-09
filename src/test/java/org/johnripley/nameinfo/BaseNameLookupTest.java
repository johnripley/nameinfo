package org.johnripley.nameinfo;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseNameLookupTest {

	@Test
	public void testNameLookup() {
		NameLookup names = new BaseNameLookup();
		GenderInfo john = names.getGender(new BaseNameInfo("John"));
		assertEquals("M",john.getGender());
		assertEquals(0,john.getYear());
		assertEquals(0.996d,john.getConfidence(),0.0d);

		GenderInfo susan = names.getGender(new BaseNameInfo("Susan"));
		assertEquals("F",susan.getGender());
		assertEquals(0,susan.getYear());
		assertEquals(0.998d,susan.getConfidence(),0.0d);
		
		GenderInfo madison = names.getGender(new BaseNameInfo("Madison"));
		assertEquals("F",madison.getGender());
		assertEquals(0,madison.getYear());
		assertEquals(0.98d,madison.getConfidence(),0.0d);

		GenderInfo madison1950 = names.getGender(new BaseNameInfo("Madison",1950));
		assertEquals("M",madison1950.getGender());
		assertEquals(1950,madison1950.getYear());
		assertEquals(1.0d,madison1950.getConfidence(),0.0d);


		GenderInfo madison2015 = names.getGender(new BaseNameInfo("Madison",2015));
		assertEquals("F",madison2015.getGender());
		assertEquals(2015,madison2015.getYear());
		assertEquals(0.993d,madison2015.getConfidence(),0.0d);
		
		NameStats riley = names.getStatistic(new BaseNameInfo("Riley"));
		assertEquals(91316, riley.getCountMale());
		assertEquals(94493, riley.getCountFemale());
		assertEquals(173894326, riley.getTotalMale());
		assertEquals(170639571, riley.getTotalFemale());
		
		NameStats riley1930 = names.getStatistic(new BaseNameInfo("Riley",1930));
		assertEquals(163, riley1930.getCountMale());
		assertEquals(5, riley1930.getCountFemale());
		assertEquals(1097258, riley1930.getTotalMale());
		assertEquals(1125853, riley1930.getTotalFemale());
		
		NameStats riley2010 = names.getStatistic(new BaseNameInfo("Riley",2010));
		assertEquals(3642, riley2010.getCountMale());
		assertEquals(5537, riley2010.getCountFemale());
		assertEquals(1915331, riley2010.getTotalMale());
		assertEquals(1774186, riley2010.getTotalFemale());
		
		
		assertEquals("M", names.getGender(new BaseNameInfo("Riley",1930)).getGender());
		assertEquals("F", names.getGender(new BaseNameInfo("Riley",2010)).getGender());
		
		
	}
}
