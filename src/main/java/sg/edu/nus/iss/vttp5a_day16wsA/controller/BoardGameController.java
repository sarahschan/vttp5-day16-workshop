package sg.edu.nus.iss.vttp5a_day16wsA.controller;

import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.vttp5a_day16wsA.service.BoardGameService;

@RestController
@RequestMapping("/api/boardgame")
public class BoardGameController {
    
    @Autowired
    BoardGameService boardGameService;

    // load board games from game.json file
    @PostMapping("/load")
    public ResponseEntity<String> allBoardGames() {
        try {
            boardGameService.loadGamesIntoRedis();
            return ResponseEntity.ok().body("Loaded all board games from game.json");
        
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to all games from game.json");
        }
    }


    // task 1 - Write a REST endpoint that will insert 1 document (in JSON) into the data store
    @PostMapping()
    public ResponseEntity<String> newBoardGame(@RequestBody String boardGameJson) {

        try {
            // call createBoardGame to create game in redis and generate response
            String response = boardGameService.createBoardGame(boardGameJson);
            return ResponseEntity.status(201).body(response);

            // catch any error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create board game in redis");
        }

    }


    // task 2 - write a REST endpoint that will retrieve a given board game
    @GetMapping("/{gameKey}")
    public ResponseEntity<String> getBoardGame(@PathVariable String gameKey) {
        
        String response = boardGameService.retrieveBoardGame(gameKey);

        return ResponseEntity.ok().body(response);

    }


    // task 3 - write a REST endpoint that will update a document (key)
    // /api/boardgame/{gameKey}?upsert=true
    @PutMapping("/{gameKey}")
    public ResponseEntity<String> updateBoardGame(@PathVariable String gameKey, @RequestBody String boardGameJson, @RequestParam(defaultValue = "false") Boolean upsert) {

        String response = boardGameService.updateBoardGame(gameKey, boardGameJson, upsert);

        return ResponseEntity.ok().body(response);
        
    }


    // bonus - REST endpoint that shows all board games in the redis database
    @GetMapping("/all/string")
    public ResponseEntity<Map<String,String>> getAllBoardGamesString() {
        
        Map<String, String> allBoardGames = boardGameService.getAllBoardGamesString();
        return ResponseEntity.ok().body(allBoardGames);

    }


    @GetMapping("/all/json")
    public ResponseEntity<Map<String, JsonObject>> getAllBoardGamesJsonMap() {
        
        // retrieve all board games using the service
        Map<String, JsonObject> allBoardGames = boardGameService.getAllBoardGamesJsonMap();

        return ResponseEntity.ok().body(allBoardGames);        
    }


}