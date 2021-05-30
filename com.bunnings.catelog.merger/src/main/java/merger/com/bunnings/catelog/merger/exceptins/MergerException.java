package merger.com.bunnings.catelog.merger.exceptins;

public class MergerException extends Exception {
    private static final long serialVersionUID = 1L;

    private String message;

    public MergerException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
