package ssmdp.instance;

public class FormatException extends Exception {

    private static final long serialVersionUID = -7103549756325747661L;

    public FormatException(String message) {
        super(message);
    }

    public FormatException(String message, Throwable cause) {
        super(message,cause);
    }

}
