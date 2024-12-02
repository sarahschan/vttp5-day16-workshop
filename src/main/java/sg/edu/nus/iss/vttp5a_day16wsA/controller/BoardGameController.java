package sg.edu.nus.iss.vttp5a_day16wsA.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
