package ca.lalalala.yelpapidemo.pojos;// File created by llin on 30/05/2016

import java.util.Collection;

public class Location {
    private String city;
    private Collection<String> display_address;
    private Coordinate coordinate;
    private String state_code;

    public Location() {
    }

    public String getDisplay_address() {
        if(display_address == null) return "";
        StringBuffer stringBuffer = new StringBuffer();
        for(String s : display_address){
            stringBuffer.append(s);
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
}
