public class Song implements Cloneable {
    private final String name;
    private final String artist;
    private Genre genre;
    private int durationInSec;

    public Song(String name, String artist, Genre genre, int durationInSec) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.durationInSec = durationInSec;
    }

    public String getArtist() {
        return artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDurationInSec() {
        return durationInSec;
    }

    @Override
    protected Song clone()  {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder songString = new StringBuilder();
        songString.append("(");
        songString.append(this.name + "," + this.artist + "," + this.genre + ",");
        songString.append(this.durationToString());
        System.out.print(")");
        String result= songString.toString();
        return result;
    }

    private String durationToString() {
        int howManyMin = this.durationInSec / 60;
        int howManySec = this.durationInSec % 60;
        StringBuilder durationString = new StringBuilder();
        durationString.append(howManyMin);
        durationString.append(":");
        if (howManySec <= 9) {
            durationString.append("0");
            durationString.append("howManySec");

        } else {
            durationString.append(howManySec);
        }
        String result= durationString.toString();
        return result;
    }

    @Override
    public boolean equals(Object songToComper) {
        if (songToComper instanceof Song) {
            if (this.name.equals(((Song) songToComper).name) &&
                    this.artist.equals(((Song) songToComper).artist)) {
                return true;
            }
        }
    return false;
    }
    @Override
    public int hashCode() {
        //missing
    }
    public enum Genre {
        POP, ROCK, HIP_HOP, COUNTRY, JAZZ, DISCO
    }


}

