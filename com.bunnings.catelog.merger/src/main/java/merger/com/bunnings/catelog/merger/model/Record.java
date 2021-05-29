package merger.com.bunnings.catelog.merger.model;

import java.util.List;

public class Record {

    private String[] headers;
    private List<String[]> rows;

    public Record(String[] headers, List<String[]> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public String[] getHeaders() {
        return headers;
    }

    public List<String[]> getRows() {
        return rows;
    }
    
    

}
