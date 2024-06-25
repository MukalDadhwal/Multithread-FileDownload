package multifiledownloader.src.filehandling;

// wrapper class around Exception
public class HttpException extends Exception{
    public HttpException(String msg){
        super(msg);
    }
}
