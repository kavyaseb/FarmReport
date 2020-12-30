package application;
/**
 * Class that handles exceptions 
 */
@SuppressWarnings("serial")
public class MyException extends Exception {

    public MyException() { }

   /**
    * method that allows a message to be passed
    * @param msg
    */
    public MyException(String msg) { 
        super(msg);
    }
}


