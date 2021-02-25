public class InvalidPackLoad extends Throwable{
    /**
     * Constructs an instance of the exception with no message
     */
    public InvalidPackLoad(){ }

    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message
     */
    public InvalidPackLoad(String message){
        super(message);
    }
}

