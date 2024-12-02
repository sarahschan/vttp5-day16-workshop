# DAY 16 WORKSHOP - 

- game.json dataset is located in src/main/resources/data
- Program has been configured to use redis database 1
    - This can be changed by editing application.properties
    - If no redis database is declared in application.properties, program will default to database 0
- Note that redis keys are boardgame:&lt;id&gt;
    - e.g. A board game with ID 100, will have the key boardgame:100

<br>

### Task 0: Load games from game.json into Redis
- On Postman:
    - POST request: http://localhost:8080/api/boardgame/load
- Program will load dataset in game.json to connected redis database and return a 200 OK status
- If unable to locate or load game.json, program will return 500 INTERNAL SERVER ERROR


### Task 1: Create new game entry
- On Postman:
    - POST request: http://localhost:8080/api/boardgame
    - Send the game as a JSON object with the body in this format:

        {
        "id": 100,
        "name": "Catan",
        "year": 1995,
        "ranking": 5,
        "rating": 500000,
        "url": "https://www.boardgamegeek.com/boardgame/100/catan"
        }
    
    - Note that id, year, ranking, and rating are Integers while id and url are Strings

- Program will attempt to create JSON data in connected redis database and return a 200 OK
- If unable to create, program will return 400 BAD REQUEST


### Task 2: Retrieve a game from redis database
- On Postman:
    - GET request: http://localhost:8080/api/boardgame/&lt;gameKey&gt;
- Program will attempt to retrieve JSON data in connected redis database and return a 200 OK
- If unable to retrieve, program will return 404 NOT FOUND


### Task 3: Update a game in redis database
- On Postman:
    - PUT request: http://localhost:8080/api/boardgame/&lt;gameKey&gt;
    - Accepts an optional upsert parameter
        - By default, upsert is false
        - PUT request: http://localhost:8080/api/boardgame/&lt;gameKey&gt;?upsert=true
- If the gameKey exists, program will attempt to update the data and return a 200 OK
- If the gameKey does not exist,
    - If upsert=false, program will return a 400 BAD REQUEST
    - If upsert=true, program will attempt to update the data and return a 200 OK

<br>

#### Useful Redis commands for this project
- Command to change to database 1 in redis: SELECT 1 (make sure redis-cli is already connected)
- Command to view JSON formatted version directly in terminal (in another non-redis-cli tab): redis-cli -n 1 GET boardgame:100 | jq
- Command to delete all keys in selected database: FLUSHDB