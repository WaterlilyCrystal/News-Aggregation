package crawlingdata;

public class Data {
    private String link;
    private String sourceWebsite;
    private String website;
    private String title;
    private String description;
    private String author;
    private String publishedDate;
    private String type;
    private String image;

    public Data() {

    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSourceWebsite() {
        return this.sourceWebsite;
    }

    public void setSourceWebsite(String sourceWebsite) {
        this.sourceWebsite = sourceWebsite;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Data(String link, String sourceWebsite, String website, String title, String description, String author, String publishedDate, String type, String image) {
        this.link = link;
        this.sourceWebsite = sourceWebsite;
        this.website = website;
        this.title = title;
        this.description = description;
        this.author = author;
        this.publishedDate = publishedDate;
        this.type = type;
        this.image = image;
    }

    public String toCsvFormat() {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n", link, sourceWebsite, website, title, description, author, publishedDate, type, image);
    }
}

