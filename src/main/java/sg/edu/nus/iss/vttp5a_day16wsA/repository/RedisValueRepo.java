package sg.edu.nus.iss.vttp5a_day16wsA.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.vttp5a_day16wsA.constant.Constants;
import sg.edu.nus.iss.vttp5a_day16wsA.exceptions.BoardGameNotFoundException;

@Repository
public class RedisValueRepo {
    
    @Autowired
    @Qualifier(Constants.REDISTEMPLATE)
    RedisTemplate<String, String> template;

    // create a board game function
    public String createBoardGame(String id, String boardGameJson) {
        
        String gameKey = "boardgame:" + id;
        template.opsForValue().set(gameKey, boardGameJson);

        JsonObject response = Json.createObjectBuilder()
                                .add("insert_count", 1)
                                .add("id", gameKey)
                                .build();
        
        return response.toString();
    }


    // retrieve a board game function
    public String retrieveBoardGame(String id) {

        String gameKey = "boardgame:" + id;

        Optional<String> opt = Optional.ofNullable(
                                template.opsForValue().get(gameKey));

        if (opt.isPresent()) {
            String boardGameJson = opt.get();
            return boardGameJson;

        } else {
            throw new BoardGameNotFoundException(id);
        }
        
    }


    // update a board game function
    public String updateBoardGame(String id, String boardGameJson, Boolean upsert){
    
        String gameKey = "boardgame:" + id;

        // Check if game exists
        String existingGame = template.opsForValue().get(gameKey);

        if (existingGame == null) {     // if game does not exist
            if (upsert) {   // if optional upsert = true
                template.opsForValue().set(gameKey, boardGameJson);
                JsonObject response = Json.createObjectBuilder()
                                        .add("insert_count", 1)
                                        .add("id", gameKey)
                                        .build();
                return response.toString();

            } else {        // if game does not exist and upsert = false
                throw new BoardGameNotFoundException(id);
            }
        }

        // if game exists, update
        template.opsForValue().set(gameKey, boardGameJson);
        JsonObject response = Json.createObjectBuilder()
                                .add("update_count", 1)
                                .add("id", gameKey)
                                .build();
        return response.toString();

    }
    


}
