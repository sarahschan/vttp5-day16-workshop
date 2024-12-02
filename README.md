# DAY 16 WORKSHOP - 

- game.json dataset is located in src/main/resources/data
- Program has been configured to use redis database 1
    - This can be changed by editing application.properties
    - If no redis database is declared in application.properties, program will default to database 0


<br>

#### Load games from game.json into Redis
- On Postman:
    - POST request: http://localhost:8080/api/boardgame/load