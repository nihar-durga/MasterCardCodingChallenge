package com.example.solution.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Controller advice class to handle exceptions across the service
 * @author Durga Nihar Muddhuchetty
 * @author nihar.1882@gmail.com
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = CustomException.class)
    /** Handles exceptions of the type CustomException
     *
     */
    public ResponseEntity<Object> exception(RuntimeException exception) {
        return ResponseEntity.status(500).body("Sorry for the inconvenience . Request could not be processed due to the following error : "+exception.getMessage());
    }

}
