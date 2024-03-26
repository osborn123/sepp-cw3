package model;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;

import java.util.Collection;

public class PageSearch {
    private standardAnalyzer analyzer;
    private Directory index;

    public PageSearch(Collection<Page> pageCollection){

    }
    private void addDoc(IndexWriter iw, String str1, String str2){

    }
    public Collection<PageSearchResult> search(String str1){
        return null;
    }
}
