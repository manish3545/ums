package com.ums.exception;

import java.util.Date;

public class ResourceNotFound extends  RuntimeException{
    public ResourceNotFound(String message){
        super(message);
    }

}
