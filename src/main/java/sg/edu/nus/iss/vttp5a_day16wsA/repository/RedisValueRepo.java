package sg.edu.nus.iss.vttp5a_day16wsA.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttp5a_day16wsA.constant.Constants;
import sg.edu.nus.iss.vttp5a_day16wsA.exceptions.BadRequestException;
import sg.edu.nus.iss.vttp5a_day16wsA.exceptions.BoardGameNotFoundException;

@Repository
public class RedisValueRepo {
    
    @Autowired
    @Qualifier(Constants.REDISTEMPLATE)
    RedisTemplate<String, String> template;


    // load games into redis from file function
    public void loadGamesIntoRedis() throws FileNotFoundException{
        
        // Read the game.json file in resources
        File file = ResourceUtils.getFile("classpath:data/game.json");
        JsonReader jReader = Json.createReader(new FileReader(file));

        // Read the JSON array of games
        JsonArray gamesArray = jReader.readArray();

        for (JsonValue gameRaw : gamesArray) {
            JsonObject game = (JsonObject) gameRaw;

            // Extract the needed information
            Integer id = game.getInt("gid");
            String name = game.getString("name");
            Integer year = game.getInt("year");
            Integer ranking = game.getInt("ranking");
            Integer rating = game.getInt("users_rated");
            String url = game.getString("url");

            // Create the Redis key for the game
            String gameKey = "boardgame" + id;

            // Create boardGameJson object with necessary changes
            JsonObject boardGameJson = Json.createObjectBuilder()
                                        .add("id", id)
                                        .add("name", name)
                                        .add("year", year)
                                        .add("ranking", ranking)
                                        .add("rating", rating)
                                        .add("url", url)
                                        .build();

            // Store the game in redis
            template.opsForValue().set(gameKey, boardGameJson.toString());
        }

    }


    // create a board game function
    public String createBoardGame(String boardGameJson) {
        
        // parse the JSON string to extract the "gid"
        JsonReader jReader = Json.createReader(new StringReader(boardGameJson));
        JsonObject gameObject = jReader.readObject();

        // extract the "gid" to create redis key
        int id = gameObject.getInt("id");
        String gameKey = "boardgame:" + id;

        // save JSON object into Redis
        template.opsForValue().set(gameKey, boardGameJson);

        // build confirmation response
        JsonObject response = Json.createObjectBuilder()
                                .add("insert_count", 1)
                                .add("id", gameKey)
                                .build();
        
        return response.toString();
    }


    // retrieve a board game function
    public String retrieveBoardGame(String gameKey) {

        Optional<String> opt = Optional.ofNullable(
                                template.opsForValue().get(gameKey));

        if (opt.isPresent()) {
            String boardGameJson = opt.get();
            return boardGameJson;

        } else {
            throw new BoardGameNotFoundException(gameKey);
        }
        
    }


    // update a board game function
    public String updateBoardGame(String gameKey, String boardGameJson, Boolean upsert){
    
        String existingGame = template.opsForValue().get(gameKey);

        if (existingGame == null) {     // if game does not exist
            if (upsert) {               // if optional upsert = true
                template.opsForValue().set(gameKey, boardGameJson);
                JsonObject response = Json.createObjectBuilder()
                                        .add("insert_count", 1)
                                        .add("id", gameKey)
                                        .build();
                return response.toString();

            } else {        // if game does not exist and upsert = false
                throw new BadRequestException(gameKey);
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
