package com.asterisk.nam.demomvpandloadapi.initApi;

public class StringUtil {
    public static String initGenreApi(String kind, String keyGenre, int limit, int offset) {
        return String.format(Constants.BASE_URL_GENRE
                , kind
                , keyGenre
                , Constants.BASE_URL_GENRE
                , limit
                , offset);
    }

    public static String initSearchApi(String keySearch, int limit) {
        return String.format(Constants.BASE_URL_SEARCH
                , keySearch
                , Constants.BASE_URL_GENRE
                , limit);
    }

    public static String initStreamUrl(int trackId) {
        return String.format(Constants.BASE_URL_STREAM
                , trackId
                , Constants.BASE_URL_GENRE);
    }

    public static String initDownloadUrl(int trackId) {
        return String.format(Constants.BASE_URL_DOWNLOAD
                , trackId
                , Constants.BASE_URL_GENRE);
    }
}
