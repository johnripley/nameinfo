package org.johnripley.nameinfo;

public interface NameLookup {

    public GenderInfo getGender(NameInfo info);    
    public NameStats getStatistic(NameInfo info);
    public NameLookupInfo getInfo();
}