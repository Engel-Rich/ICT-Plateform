package egel_rich.dev.sa_backend;

import egel_rich.dev.sa_backend.dto.ErrorModel;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, ChangeSetPersister.NotFoundException.class})
    public @ResponseBody ErrorModel handlerException(Exception exception) {
        return new ErrorModel(exception.getMessage(), null);
    }

}
