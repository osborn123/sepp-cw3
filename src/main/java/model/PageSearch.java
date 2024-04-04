package model;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Collections;


public class PageSearch {
    private StandardAnalyzer analyzer; // Analyzer for indexing and searching
    private Directory index; // Lucene Directory to store the index in memory
    private Collection<PageSearchResult> pageSearchResults; // Collection to store search results

    /**
     * Initializes the search index with available pages.
     *
     * @param availablePages A map of page titles to Page objects that should be indexed.
     * @throws IOException If there is a low-level IO error.
     */
    public PageSearch(Map<String, Page> availablePages) throws IOException {
        analyzer = new StandardAnalyzer();
        index = new ByteBuffersDirectory(); // In-memory directory for the index

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        try (IndexWriter w = new IndexWriter(index, config)) {
            for (Page page : availablePages.values()) {
                // Split each page into paragraphs for indexing
                String[] paragraphs = page.getContent().split("\n");
                for (String paragraph : paragraphs) {
                    addDoc(w, page.getTitle(), paragraph);
                }
            }
        } // Auto-closes IndexWriter
    }

    /**
     * Adds a document to the Lucene index. Each document represents a paragraph from a web page.
     *
     * @param w         The IndexWriter to use for adding the document.
     * @param title     The title of the page the paragraph belongs to.
     * @param paragraph The content of the paragraph to be indexed.
     * @throws IOException If there is a low-level IO error.
     */
    private void addDoc(IndexWriter w, String title, String paragraph) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES)); // Store title for retrieval
        doc.add(new TextField("paragraph", paragraph, Field.Store.YES)); // Store paragraph content
        w.addDocument(doc);
    }

    /**
     * Searches the index for paragraphs that match the query string.
     *
     * @param query The query string to search for.
     * @return A collection of PageSearchResult objects containing the search results.
     * @throws ParseException If there is an error parsing the query.
     * @throws IOException If there is a low-level IO error.
     */
    public Collection<PageSearchResult> search(String query) throws IOException {
        if (query == null || query.trim().isEmpty()) {
            // Return an empty collection immediately if the query is null or empty
            return Collections.emptyList();
        }

        pageSearchResults = new ArrayList<>();
        ArrayList<String> pagesTracker = new ArrayList<>(); // Tracks pages to ensure uniqueness

        try (DirectoryReader reader = DirectoryReader.open(index)) {
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("paragraph", analyzer);
            Query luceneQuery = null;
            try {
                luceneQuery = parser.parse(query);
            } catch (ParseException e) {
                // Handle ParseException
                return Collections.emptyList();
            }

            TopDocs topDocs = searcher.search(luceneQuery, Integer.MAX_VALUE);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                String title = doc.get("title");

                if (!pagesTracker.contains(title)) {
                    pagesTracker.add(title);
                    String paragraph = doc.get("paragraph");
                    PageSearchResult result = new PageSearchResult(title + ": " + paragraph);
                    pageSearchResults.add(result);

                    if (pageSearchResults.size() >= 4) {
                        break; // Limit results to the top 4 unique pages
                    }
                }
            }
        } // Auto-closes DirectoryReader

        return pageSearchResults;
    }


}
