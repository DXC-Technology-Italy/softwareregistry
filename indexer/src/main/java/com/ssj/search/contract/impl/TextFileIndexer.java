package com.ssj.search.contract.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ssj.search.contract.FileTypeToIndex;
import com.ssj.search.contract.IndexFields;
import com.ssj.search.contract.Indexer;
import com.ssj.search.exception.SearchException;
import com.ssj.search.service.RepositoryFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ssj.search.util.ApplicationConstants.APP_CONST_ERROR_CODE_7003;


@Component
public class TextFileIndexer implements Indexer {

    private static final Logger logger = LoggerFactory.getLogger(TextFileIndexer.class);

    @Value("${search.index.directory}")
    private String indexPath;

    /**
     * Document index based on required fields
     */
    @Override
    public boolean indexDocument(IndexWriter writer, Path file, long lastModified) throws SearchException {
        Pattern ptrnPath = Pattern.compile("(area[0-9]{4})(\\/[^\\/]+)(\\/[^\\/]+)");
//        Pattern ptrnPath      = Pattern.compile("(AREA[0-9]{4})(\\\\[^\\\\]+)(\\\\[^\\\\]+)");
        Pattern ptrnFile = Pattern.compile("([^\\/]*$)");
        logger.debug("Parent : {} ", file.getParent());
        try (InputStream stream = Files.newInputStream(file)) {
            Document doc = new Document();
            logger.debug("Considering file : {} ", file);
            String extension = FilenameUtils.getExtension(file.toString());
            if (EnumUtils.isValidEnum(FileTypeToIndex.class, extension)) {
                Field pathField = new StringField(IndexFields.INDEX_FIELD_PATH, file.toString(), Field.Store.YES);
                Matcher matcherPath = ptrnPath.matcher(file.toString());
                Matcher matcherFile = ptrnFile.matcher(file.toString());
                doc.add(new StringField(IndexFields.INDEX_FIELD_EXTENSION, extension, Field.Store.YES));
                if (matcherPath.find()) {
                    doc.add(new StringField(IndexFields.INDEX_FIELD_AREA, matcherPath.group(1).toLowerCase(), Field.Store.YES));
//                    doc.add(new StringField(IndexFields.INDEX_FIELD_AREA, matcherPath.group(1).toLowerCase(), Field.Store.YES ));
                    logger.debug("Area  : {} ", matcherPath.group(1).toLowerCase());
//                    doc.add(new StringField(IndexFields.INDEX_FIELD_REPOSITORY, matcherPath.group(2).replace("\\","").toLowerCase(), Field.Store.YES ));
                    doc.add(new StringField(IndexFields.INDEX_FIELD_REPOSITORY, matcherPath.group(2).replace("/", "").toLowerCase(), Field.Store.YES));
                    logger.debug("repository  : {} ", matcherPath.group(2).replace("\\", "").toLowerCase());
                    if (!extension.equals("pco")) {
                        doc.add(new StringField(IndexFields.INDEX_FIELD_PROJECT, matcherPath.group(3).replace("/", "").toLowerCase(), Field.Store.YES));
//                        doc.add(new StringField(IndexFields.INDEX_FIELD_PROJECT, matcherPath.group(3).replace("\\","").toLowerCase(), Field.Store.YES ));
                        logger.debug("project  : {} ", matcherPath.group(3).replace("\\", ""));
                    }
                    HashMap<String, JsonElement> repositoryMap = RepositoryFileService.getInstance().getRepositoryMap();
                    JsonObject obj = repositoryMap.get(matcherPath.group(1) + matcherPath.group(2)).getAsJsonObject();
                    doc.add(new StringField(IndexFields.INDEX_FIELD_KIND, obj.get("kind").getAsString().toLowerCase(), Field.Store.YES));
                    if (matcherFile.find()) {
                        doc.add(new StringField(IndexFields.INDEX_FIELD_FILENAME, matcherFile.group().toLowerCase(), Field.Store.YES));
                    }
                }

                doc.add(pathField);
                doc.add(new LongPoint(IndexFields.INDEX_FIELD_MODIFIED, lastModified));
                doc.add(new TextField(IndexFields.INDEX_FIELD_CONTENTS, new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

                if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                    // New index, so we just add the document (no old document can be there):
                    logger.debug("Creating Index : {} ", file);
                    writer.addDocument(doc);
                } else {
                    logger.debug("Updating/Creating Index : {} ", file);
                    writer.updateDocument(new Term("path", file.toString()), doc);
                }
            }

        } catch (IOException e) {
            throw new SearchException(APP_CONST_ERROR_CODE_7003, file.toString());
        }
        return Boolean.TRUE;
    }


    
    /** 
     * @return String
     */
    @Override
    public String getIndexDirectoryPath() {
        return indexPath;
    }
}
