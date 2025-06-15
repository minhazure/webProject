package Entity;

public class Song {
    private String title;
    private String artist;
    private String album;
    private int duration;

    public Song(String title, String artist, String album, int duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDuration() { return duration; }

    public String toString() {
        return "Title: " + title + "\nArtist: " + artist +
               "\nAlbum: " + album + "\nDuration: " + duration + " sec\n------------------\n";
    }
}