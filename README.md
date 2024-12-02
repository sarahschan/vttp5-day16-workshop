# DAY 16 WORKSHOP - 

- game.json dataset is located in src/main/resources/data
- Program has been configured to use redis database 1
    - This can be changed by editing application.properties
    - If no redis database is declared in application.properties, program will default to database 0

<br>

#### Load games from game.json into Redis
- On Postman:
    - POST request: http://localhost:8080/api/boardgame/load


#### Create new game entry
- On Postman:
    - POST request: http://localhost:8080/api/boardgame/load
    - Send the game as a JSON object with the body in this format:

        {
        "id": 100,
        "name": "Catan",
        "year": 1995,
        "ranking": 5,
        "rating": 500000,
        "url": "https://www.boardgamegeek.com/boardgame/100/catan"
        }

<br>

#### Useful Redis commands for this project
- Command to change to database 1 in redis: SELECT 1 (make sure redis-cli is already connected)
- Command to view JSON formatted version directly in terminal (in another non-redis-cli tab): redis-cli -n 1 GET boardgame:100 | jq
- Command to delete all keys in selected database: FLUSHDB