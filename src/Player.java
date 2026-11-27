public class Player{
  private String season;
  private String name;
  private String position;
  private int rating

    public Player(String season, String name, String position, int rating) {
        this.season = season;
        this.name = name;
        this.position = position;
        this.rating = rating;
    }

    public String getSeason() {
        return season;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return season + " " + name + " " + position + " " + rating;
    }
}
