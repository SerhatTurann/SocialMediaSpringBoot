package com.example.social_media.Exception;

public class BaseException extends RuntimeException{

    public BaseException(){

    }

    public BaseException(ErrorMessage errorMessage){
        super(errorMessage.prepareErrorMessage());
    }
}