package sg.edu.nus.iss.vttp5a_day16wsA.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String gameKey) {
        super("BAD REQUEST - Board game with ID " + gameKey + " not found and (optional) upsert Boolean set to FALSE");
    }
}
