package crawlingdata;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class CSVWriter {
    private String filePath;

    public CSVWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeData(Set<Data> webPageData) {
        try (FileWriter writer = new FileWriter(filePath,true)) {
            writer.write("\"Link\",\"Source website\",\"Website link\",\"Title\",\"Description\",\"Author\",\"Published date\",\"Type\",\"Image\"\n");
            for (Data data : webPageData) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        data.getLink(), data.getSourceWebsite(), data.getWebsite(), data.getTitle(), data.getDescription(),
                        data.getAuthor(), data.getPublishedDate(), data.getType(), data.getImage()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
