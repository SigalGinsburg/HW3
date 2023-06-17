public class Song implements Cloneable {
    private final String name;
    private final String artist;
    private Genre genre;
    private int durationInSec;
    public Song(String name,String artist,Genre genre,int durationInSec){
        this.name=name;
        this.artist=artist;
        this.genre=genre;
        this.durationInSec=durationInSec;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

    }

    @Override
    public String toString() {
        System.out.print("(");
        System.out.print(this.name+","+this.artist+","+this.genre+",");
        this.durationToString();
        System.out.print("),");
    }

    private void durationToString(){
        int howManyMin= this.durationInSec/60;
        int howManySec= this.durationInSec % 60;
        System.out.print(howManyMin+":");
        if (howManySec<=9){
            System.out.print("0"+howManySec);
        }
        else {
            System.out.print(howManySec);
        }
    }
    private void printLast(){

    }
    @Override
    public boolean equals(Song songToComper) {
        if(this.name==songToComper.name&&this.artist==songToComper.artist){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public int hashCode() {
    }
    public enum Genre {
        POP, ROCK, HIP_HOP, COUNTRY, JAZZ, DISCO
    }


}

