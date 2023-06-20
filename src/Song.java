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

    public String getName(){
        return this.name;
    }
    public String getArtist() {
        return this.artist;
    }
    public Genre getGenre() {
        return this.genre;
    }
    public int getDurationInSec() {
        return this.durationInSec;
    }
    public void setDuration(int durationInSec) {
        this.durationInSec = durationInSec;
    }

    /** deep copy of a song, as we learned to implement in the course **/
    @Override
    protected Song clone()  {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * this function creates to 'this' song a string that represents it.
     * @return the string.
     */
    @Override
    public String toString() {
        StringBuilder songString = new StringBuilder();
        songString.append(this.name + ", " + this.artist + ", " + this.genre + ", "); //creating the string as directed
        songString.append(this.durationToString()); //help function that creates a string for the duration of the song
        String result= songString.toString();
        return result;
    }

    /** An helper function for 'toString' function. **/
    private String durationToString() {
        // changing the value to min:sec display
        int howManyMin = this.durationInSec / 60; // how many minutes
        int howManySec = this.durationInSec % 60; // how many seconds
        StringBuilder durationString = new StringBuilder();
        durationString.append(howManyMin);
        durationString.append(":");
        if (howManySec <= 9) {
            durationString.append("0"); //adding zero if necessary
            durationString.append(howManySec);

        } else {
            durationString.append(howManySec);
        }
        String result= durationString.toString();
        return result;
    }

    /**
     * checks whether two songs are equals i.e. same song's name and the same artist.
     * @param songToComper the song that is compared to 'this' song
     * @return boolean answer
     */
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
        int result= this.name.hashCode()+this.artist.hashCode();//+this.durationInSec;
        return result;
    }
    public enum Genre {
        POP, ROCK, HIP_HOP, COUNTRY, JAZZ, DISCO
    }


}

