package sg.edu.nus.iss.vttp5a_day16wsA.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}