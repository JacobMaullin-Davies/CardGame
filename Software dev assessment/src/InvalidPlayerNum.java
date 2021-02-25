/**
 * Invalid player number
 */
public class InvalidPlayerNum extends Throwable{
    /**
     * Constructs an instance of the exception with no message
     */
    public InvalidPlayerNum(){ }

    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message
     */
    public InvalidPlayerNum(String message){
        super(message);
    }
}
