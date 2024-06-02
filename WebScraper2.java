package crawlingdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebScraper2 extends PageDataExtractor {
    public WebScraper2(String[] motherUrls) {
        super(motherUrls);
    }

    @Override
    public Set<Data> scrapePages() {
        Set<Data> articles = new HashSet<>();

        try {
            String[] motherUrls = getMotherUrls();
            for (String motherUrl : motherUrls) {
                Set<String> childUrls = new HashSet<>(); // Initialize for each mother page

                boolean hasNextPage = true;
                while (hasNextPage) {
                    Document motherDoc = Jsoup.connect(motherUrl)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                            .get();

                    Elements articleLinks = motherDoc.select("a[itemprop=url][data-test=title-link]");
                    for (Element link : articleLinks) {
                        String childUrl = link.attr("abs:href");
                        childUrls.add(childUrl);
                    }

                    Elements nextElements = motherDoc.select("a.c-pagination__link[rel=next]");
                    if (!nextElements.isEmpty()) {
                        Element nextElement = nextElements.first();
                        motherUrl = nextElement.attr("abs:href");
                    } else {
                        hasNextPage = false;
                    }
                }

                // Now iterate through the child URLs and scrape each page
                for (String childUrl : childUrls) {
                    Document childDoc = Jsoup.connect(childUrl).get();
                    Data article = extractPageData(childUrl, childDoc);

                    if (article != null) {
                        articles.add(article);
                        // Delay before scraping next page to avoid overloading server
                        TimeUnit.MILLISECONDS.sleep(1000); // 1 second delay
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return articles;
    }

    @Override
    public Data extractPageData(String url, Document document) throws IOException {
        String site = document.select("meta[name=citation_publisher]").attr("content");
        String title = document.select("meta[name=citation_title]").attr("content");
        final String webLink = "https://www.springeropen.com";
        String description = document.select("meta[property=og:description]").attr("content");
        String publicationDate = document.select("meta[name=citation_online_date]").attr("content");
        String author = document.select("meta[name=citation_author]").attr("content");
        String type = document.select("meta[name=citation_article_type]").attr("content");
        final String imageLink = "https://www.springeropen.com/static/images/springeropen/logo-springer-open-d04c3ea16c.svg";
        return new Data(url, site, webLink, title, description, author, publicationDate, type, imageLink);
    }

    public static void main(String[] args) {
        String[] motherUrls = {
                "https://www.springeropen.com/search?query=blockchain&searchType=publisherSearch"
        };

        // Instantiate WebScraper2
        WebScraper2 webScraper = new WebScraper2(motherUrls);

        // Scrape web pages
        Set<Data> webPages = webScraper.scrapePages();

        // Write data to CSV file
        CSVWriter csvWriter = new CSVWriter("blockchain.csv");
        csvWriter.writeData(webPages);
        System.out.println("Scraping done. Check blockchain.csv for the data.");
    }
}
