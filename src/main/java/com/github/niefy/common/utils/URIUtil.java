package com.github.niefy.common.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class URIUtil {
    public static String appendUri(String uri, String appendQuery) throws URISyntaxException {
        URI oldUri = new URI(uri);

        String newQuery = oldUri.getQuery();
        if (newQuery == null) {
            newQuery = appendQuery;
        } else {
            newQuery += "&" + appendQuery;
        }

        URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
            oldUri.getPath(), newQuery, oldUri.getFragment());

        return newUri.toString();
    }
}
