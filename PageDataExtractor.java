package crawlingdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class PageDataExtractor {
    protected String[] motherUrls;

    public abstract Data extractPageData(String url, Document document) throws IOException;

    public Set<Data> scrapePages() {
        Set<Data> dataset = new HashSet<>();

        try {
            String[] motherUrls = getMotherUrls();
            for (String motherUrl : motherUrls) {
                Document motherDoc = Jsoup.connect(motherUrl).get();
                Elements links = motherDoc.select("h3.elementor-post__title a");

                for (Element link : links) {
                    String childUrl = link.absUrl("href");
                    Document childDoc = Jsoup.connect(childUrl).get();
                    Data data = extractPageData(childUrl, childDoc);

                    if (data != null) {
                        dataset.add(data);
                        // Delay before scraping next page to avoid overloading server
                        TimeUnit.MILLISECONDS.sleep(1000); // 1 second delay
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public PageDataExtractor(String[] motherUrls) {
        this.motherUrls = motherUrls;
    }

    protected String[] getMotherUrls() {
        return motherUrls;
    }
}

