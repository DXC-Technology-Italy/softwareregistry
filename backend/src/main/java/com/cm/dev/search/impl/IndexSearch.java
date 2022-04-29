package com.cm.dev.search.impl;

import com.cm.dev.search.IndexFields;
import com.cm.dev.search.Searcher;
import com.cm.dev.exception.SearchException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.cm.dev.util.ApplicationConstants.APP_CONST_ERROR_CODE_8001;


/**
 * Perform Search in already created index
 *
 * @author Sushil
 */
@Component("indexSearcher")
public class IndexSearch implements Searcher {

    private static final Logger logger = LoggerFactory.getLogger(IndexSearch.class);

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
    public List<String> searchKeywords(List<String> keyWords, boolean fuzzy, String area, String project, String repository, String extension, String kind, String filename, String limitResult) {
        List<String> fileNames = null;
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
//            Analyzer analyzer = new StandardAnalyzer();
//            Analyzer analyzer = new WhitespaceAnalyzer();
            Analyzer analyzer = CustomAnalyzer.builder()
                    .withTokenizer(WhitespaceTokenizerFactory.NAME)
                    .addTokenFilter("lowercase")
//                    .addTokenFilter(StopFilterFactory.NAME)
                    .build();
            QueryParser parser = new QueryParser("", analyzer);
            if (!fuzzy) {
                parser.setDefaultOperator(QueryParser.Operator.AND);
            }
            String searchArea = !area.equals("") ? " AND " + IndexFields.INDEX_FIELD_AREA + ":/" + area + "/" : "";
            String searchRepository = (!repository.equals("") ? " AND " + IndexFields.INDEX_FIELD_REPOSITORY + ":/" + repository + "/" : "");
            String searchProject = (!project.equals("") ? " AND " + IndexFields.INDEX_FIELD_PROJECT + ":/" + project + "/" : "");
            String searchExtension = (!extension.equals("") ? " AND " + IndexFields.INDEX_FIELD_EXTENSION + ":/" + extension + "/" : "");
            String searchKind = (!kind.equals("") ? " AND " + IndexFields.INDEX_FIELD_KIND + ":/" + kind + "/" : "");
            String searchFilename = (!filename.equals("") ? " AND " + IndexFields.INDEX_FIELD_FILENAME + ":/" + filename + "/" : "");

            Query query = parser.parse(IndexFields.INDEX_FIELD_CONTENTS + ":/" + keyWords.stream().collect(Collectors.joining(" ")) + "/" + searchArea + searchRepository + searchProject + searchExtension + searchKind + searchFilename);
            String queryContent = "";
            if(keyWords.size()>1) {
                for (String elem : keyWords) {
                    queryContent = queryContent + "+" + IndexFields.INDEX_FIELD_CONTENTS + ":" + elem + " ";
                }
                query = parser.parse(queryContent + searchArea + searchRepository + searchProject + searchExtension + searchKind + searchFilename);
            }

            System.out.println("query = " + query.toString());

            TopDocs docs = searcher.search(query, Integer.parseInt(limitResult));
            List<ScoreDoc> hitsList = Arrays.asList(docs.scoreDocs);
            fileNames = hitsList.stream().map(scoreDoc -> {
                try {
                    return searcher.doc(scoreDoc.doc).get(IndexFields.INDEX_FIELD_PATH);
                } catch (IOException e) {
                    //Return null if document is not present
                    return null;
                }
            }).filter(e -> e != null).collect(Collectors.toList());
        } catch (IOException | ParseException e) {
            logger.error("Unable to search the keywords : ", e);
            return Arrays.asList(APP_CONST_ERROR_CODE_8001);
        }
        return fileNames;
    }

    @Override
    public List<String> searchFilenames(String filename, String area, String repository, String kind, String limitResult) throws SearchException {
        List<String> fileNames = null;
        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser parser = new QueryParser("", analyzer);
            String searchArea = !area.equals("") ? " AND " + IndexFields.INDEX_FIELD_AREA + ":/" + area + "/" : "";
            String searchRepository = (!repository.equals("") ? " AND " + IndexFields.INDEX_FIELD_REPOSITORY + ":/" + repository + "/" : "");
            String searchKind = (!kind.equals("") ? " AND " + IndexFields.INDEX_FIELD_KIND + ":/" + kind + "/" : "");
            Query query = parser.parse(IndexFields.INDEX_FIELD_FILENAME + ":/" + filename + "/" + searchArea + searchRepository + searchKind);
            System.out.println("query = " + query);
            TopDocs docs = searcher.search(query, Integer.parseInt(limitResult));
            List<ScoreDoc> hitsList = Arrays.asList(docs.scoreDocs);
            fileNames = hitsList.stream().map(scoreDoc -> {
                try {
                    return searcher.doc(scoreDoc.doc).get(IndexFields.INDEX_FIELD_PATH);
                } catch (IOException e) {
                    //Return null if document is not present
                    return null;
                }
            }).filter(e -> e != null).collect(Collectors.toList());
        } catch (IOException | ParseException e) {
            logger.error("Unable to search the filename : ", e);
            return Arrays.asList(APP_CONST_ERROR_CODE_8001);
        }
        return fileNames;

    }
}
