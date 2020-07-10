package com.example.solution.Exception;

/** Customized exception class to deal to user defined exceptions
 * @author Durga Nihar Muddhuchetty
 * @author nihar.1882@gmail.com
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
