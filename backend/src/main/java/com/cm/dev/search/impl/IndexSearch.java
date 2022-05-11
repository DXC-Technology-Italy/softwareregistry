package com.cm.dev.search.impl;

import com.cm.dev.search.IndexFields;
import com.cm.dev.search.Searcher;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cm.dev.util.ApplicationConstants.APP_CONST_ERROR_CODE_8001;


/**
 * Implements search scenarios using Lucene APIs
 */
@Component("indexSearcher")
public class IndexSearch implements Searcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexSearch.class);
    private static final String AND_CONDITION = " AND ";

    @Value("${search.index.directory}")
    private String indexPath;

    /**
     * Search given keywords
     *
     * @param keyWords : Words to be searched
     * @param fuzzy    : Search is fuzzy or all words must be present in file
     *                 <TR> TRUE : OR will be performed
     *                 <TR> FALSE : AND will be performed
     */
    @Override
    public List<String> searchKeywords(List<String> keyWords, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) throws IOException, ParseException{
        List<String> fileNames;
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = CustomAnalyzer.builder()
                    .withTokenizer(WhitespaceTokenizerFactory.NAME)
                    .addTokenFilter("lowercase")
                    .build();
            QueryParser parser = new QueryParser("", analyzer);
            if (!fuzzy) {
                parser.setDefaultOperator(QueryParser.Operator.AND);
            }
            String searchArea = !("").equals(area) ? AND_CONDITION + IndexFields.INDEX_FIELD_AREA + ":/" + area + "/" : "";
            String searchRepository = (!("").equals(repository) ? AND_CONDITION + IndexFields.INDEX_FIELD_REPOSITORY + ":/" + repository + "/" : "");
            String searchProject = (!("").equals(project) ? AND_CONDITION + IndexFields.INDEX_FIELD_PROJECT + ":/" + project + "/" : "");
            String searchExtension = (!("").equals(extension) ? AND_CONDITION + IndexFields.INDEX_FIELD_EXTENSION + ":/" + extension + "/" : "");
            String searchKind = (!("").equals(kind) ? AND_CONDITION + IndexFields.INDEX_FIELD_KIND + ":/" + kind + "/" : "");
            String searchitems = (!("").equals(filename) ? AND_CONDITION + IndexFields.INDEX_FIELD_FILENAME + ":/" + filename + "/" : "");

            Query query = parser.parse(IndexFields.INDEX_FIELD_CONTENTS + ":/" + String.join(" ", keyWords) + "/" + searchArea + searchRepository + searchProject + searchExtension + searchKind + searchitems);
            StringBuilder queryContent = new StringBuilder();
            if (keyWords.size() > 1) {
                for (String elem : keyWords) {
                    queryContent.append("+").append(IndexFields.INDEX_FIELD_CONTENTS).append(":").append(elem).append(" ");
                }
                query = parser.parse(queryContent + searchArea + searchRepository + searchProject + searchExtension + searchKind + searchitems);
            }

            TopDocs docs = searcher.search(query, Integer.parseInt(limitResult));
            List<ScoreDoc> hitsList = Arrays.asList(docs.scoreDocs);
            fileNames = hitsList.stream().map(scoreDoc -> {
                try {
                    return searcher.doc(scoreDoc.doc).get(IndexFields.INDEX_FIELD_PATH);
                } catch (IOException e) {
                    LOGGER.error(String.valueOf(e));
                    return "";
                }

            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException | ParseException e) {
            LOGGER.error(String.valueOf(e));
            return Collections.singletonList(APP_CONST_ERROR_CODE_8001);
        }
        return fileNames;
    }

    
    /** 
     * @param filename
     * @param area
     * @param repository
     * @param kind
     * @param limitResult
     * @return List<String>
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) throws IOException, ParseException{
        List<String> fileNames;
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser parser = new QueryParser("", analyzer);
            String searchArea = !("").equals(area) ? AND_CONDITION + IndexFields.INDEX_FIELD_AREA + ":/" + area + "/" : "";
            String searchRepository = (!("").equals(repository) ? AND_CONDITION + IndexFields.INDEX_FIELD_REPOSITORY + ":/" + repository + "/" : "");
            String searchKind = (!("").equals(kind) ? AND_CONDITION + IndexFields.INDEX_FIELD_KIND + ":/" + kind + "/" : "");
            Query query = parser.parse(IndexFields.INDEX_FIELD_FILENAME + ":/" + filename + "/" + searchArea + searchRepository + searchKind);
            TopDocs docs = searcher.search(query, Integer.parseInt(limitResult));
            List<ScoreDoc> hitsList = Arrays.asList(docs.scoreDocs);
            fileNames = hitsList.stream().map(scoreDoc -> {
                try {
                    return searcher.doc(scoreDoc.doc).get(IndexFields.INDEX_FIELD_PATH);
                } catch (IOException e) {
                    LOGGER.error(String.valueOf(e));
                    return "";
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException | ParseException e) {
            LOGGER.error(String.valueOf(e));
            return Collections.singletonList(APP_CONST_ERROR_CODE_8001);
        }
        return fileNames;

    }
}
