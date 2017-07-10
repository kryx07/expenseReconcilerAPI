package com.kryx07.expensereconcilerapi.utils;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class StringUtilities {

    private static Logger logger = Logger.getLogger(StringUtilities.class);

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static URI buildUri(String value) {

        URI uri = null;
        try {
            uri = new URI(value);
        } catch (URISyntaxException e) {
            logger.error(e.getStackTrace());
        }
        return uri;
    }
}
