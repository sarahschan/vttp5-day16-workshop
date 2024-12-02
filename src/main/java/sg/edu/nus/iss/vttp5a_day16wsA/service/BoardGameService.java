package sg.edu.nus.iss.vttp5a_day16wsA.service;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.vttp5a_day16wsA.repository.RedisValueRepo;

@Service
public class BoardGameService {
    
    @Autowired
    RedisValueRepo redisValueRepo;

    public void loadGamesIntoRedis() throws FileNotFoundException {
        redisValueRepo.loadGamesIntoRedis();
    }

    public String createBoardGame(String boardGameJson) {
        return redisValueRepo.createBoardGame(boardGameJson);
    }

    public String retrieveBoardGame(String gameKey) {
        return redisValueRepo.retrieveBoardGame(gameKey);
    }
}
