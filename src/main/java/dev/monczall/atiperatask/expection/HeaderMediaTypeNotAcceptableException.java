package dev.monczall.atiperatask.expection;

public class HeaderMediaTypeNotAcceptableException extends RuntimeException {
    public HeaderMediaTypeNotAcceptableException(String message){
        super(message);
    }
}
