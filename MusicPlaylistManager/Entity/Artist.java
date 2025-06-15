package Entity;

public class Artist {
    private String name;
    private String genre;

    public Artist(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }

    public String toString() {
        return "Artist: " + name + "\nGenre: " + genre;
    }
}