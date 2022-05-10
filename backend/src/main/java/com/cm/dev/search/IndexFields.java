package com.cm.dev.search;

/**
 * Fields included in Lucene Index
 * 
 */
public final class IndexFields {

    public static final String INDEX_FIELD_PATH = "path";
    public static final String INDEX_FIELD_MODIFIED = "modified";
    public static final String INDEX_FIELD_CONTENTS = "contents";
    public static final String INDEX_FIELD_AREA = "area";
    public static final String INDEX_FIELD_REPOSITORY = "repository";
    public static final String INDEX_FIELD_PROJECT = "project";
    public static final String INDEX_FIELD_EXTENSION = "extension";
    public static final String INDEX_FIELD_KIND = "kind";
    public static final String INDEX_FIELD_FILENAME = "filename";

    private IndexFields() {
        throw new IllegalStateException("Utility class");
    }


}
