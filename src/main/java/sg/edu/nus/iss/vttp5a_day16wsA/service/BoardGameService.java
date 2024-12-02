package sg.edu.nus.iss.vttp5a_day16wsA.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.vttp5a_day16wsA.repository.RedisValueRepo;

@Service
public class BoardGameService {
    
    @Autowired
    RedisValueRepo redisValueRepo;

    public void loadGamesIntoRedis() throws FileNotFoundException {
        
        // Read the game.json file in resources
        File file = ResourceUtils.getFile("classpath:game.json");
        JsonReader jReader = Json.createReader(new FileReader(file));

        // Read the JSON array of games
        JsonArray gamesArray = jReader.readArray();

        for (JsonValue gameRaw : gamesArray) {      // for each value in the array
            JsonObject game = (JsonObject) gameRaw; // cast to JsonObject

            Integer id = game.getInt("gid");
            String name = game.getString("name");
            Integer year = game.getInt("year");
            Integer ranking = game.getInt("ranking");
            Integer rating = game.getInt("users_rated");
            String url = game.getString("url");

            // create the Redis key for the game
            String gameKey = "boardgame:" + id;

            // Create a new JsonObject for the game with selected and renamed fields
            JsonObject boardGameJson = Json.createObjectBuilder()
                                        .add("id", id)
                                        .add("name", name)
                                        .add("year", year)
                                        .add("ranking", ranking)
                                        .add("rating", rating)
                                        .add("url", url)
                                        .build();
            
            // store the game in Redis
            redisValueRepo.createBoardGame(gameKey, boardGameJson.toString());

        }
    }
}
