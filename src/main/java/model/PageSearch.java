package model;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.IOException;
import java.util.Collection;

public class PageSearch {
    private StandardAnalyzer analyzer;
    private Directory index;

    public PageSearch(Collection<Page> pageCollection){

    }
    private void addDoc(IndexWriter iw, String title, String isbn) throws IOException {
            Document doc = new Document();
            doc.add(new TextField("title", title, Field.Store.YES));
            doc.add(new StringField("isbn", isbn, Field.Store.YES));
            iw.addDocument(doc);
    }
    public Collection<PageSearchResult> search(String querystr) throws ParseException, IOException {
//        String querystr = args.length > 0 ? args[0] : "lucene";
        //TODO: Add testing here, i.e. length of query etc ^^^
        Query q = new QueryParser("title", analyzer).parse(querystr);
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        reader.close();
        return (Collection<PageSearchResult>) docs;
    }
}
