package com.thalasoft.learnintouch.web.rest.utils;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public static List<String> parseLinkHeader(String linkHeader) {
        List<String> linkHeaders = new ArrayList<String>();
        String[] links = linkHeader.split(", ");
        for (String link : links) {
            int positionOfSeparator = link.indexOf(';');
            linkHeaders.add(link.substring(1, positionOfSeparator - 1));
        }
        return linkHeaders;
    }

    public static String parseSingleLinkHeader(String linkHeader) {
        int positionOfSeparator = linkHeader.indexOf(';');
        return linkHeader.substring(1, positionOfSeparator - 1);
    }

}