package sg.edu.nus.iss.vttp5a_day16wsA.model;

public class BoardGame {
    
    private String id;
    private String name;
    private String year;
    private String ranking;
    private String rating;
    private String url;
    
    
    public BoardGame() {
    }

    public BoardGame(String id, String name, String year, String ranking, String rating, String url) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.rating = rating;
        this.url = url;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getRanking() {
        return ranking;
    }
    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
}
