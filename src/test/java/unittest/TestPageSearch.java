package unittest;

import model.Page;
import model.PageSearch;
import model.PageSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestPageSearch {
    private PageSearch pageSearch;
    private Map<String, Page> availablePages;

    @BeforeEach
    public void setUp() throws IOException {
        // Setup some dummy pages
        availablePages = new HashMap<>();
        availablePages.put("Page 1", new Page("Page 1", "This is the content of page one. It has unique words.", false));
        availablePages.put("Page 2", new Page("Page 2", "Content of the second page. It also has unique terms.", false));
        pageSearch = new PageSearch(availablePages);
    }

    @Test
    @DisplayName("Test search results for existing page keyword")
    public void testSearchReturnsCorrectResults() throws IOException {
        Collection<PageSearchResult> results = pageSearch.search("unique");
        Assertions.assertEquals(2, results.size(), "Search should return two results for 'unique'");
    }

    @Test
    @DisplayName("Test search results for non-existing page keyword")
    public void testSearchWithNoMatches() throws IOException {
        Collection<PageSearchResult> results = pageSearch.search("nonexistent");
        Assertions.assertTrue(results.isEmpty(), "Search should return no results for 'nonexistent'");
    }

    @Test
    @DisplayName("Test search results for empty query")
    public void testSearchWithEmptyQuery() throws IOException {
        Collection<PageSearchResult> results = pageSearch.search("");
        Assertions.assertTrue(results.isEmpty(), "Search should return no results for an empty query");
    }

    @Test
    @DisplayName("Test initialization of PageSearch")
    public void testInitialization() {
        Assertions.assertNotNull(pageSearch, "PageSearch should be initialized");
    }

}
