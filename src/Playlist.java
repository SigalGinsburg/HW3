import java.util.ArrayList;
import java.util.Iterator;

public class Playlist implements Cloneable, OrderedSongIterable {
 private ArrayList<Song> playList;
 private String filterArtistAux;
 public Playlist(){
  this.playList=null;
 }


 private void addSong (Song songToAdd){
  for(Song song:this.playList){
    if (song.equals(songToAdd)){
     //needs to add exception
     break;
    }
  }
  this.playList.add(songToAdd);
 }

 private boolean removeSong (Song songToRemove) {
  for (Song song : this.playList) {
   if (song.equals(songToRemove)) {
    this.playList.remove(songToRemove);
    return true;
   }
  }

  return false;
 }
 @Override
 protected Object clone() throws CloneNotSupportedException {

 }
 private Boolean isLast(){

 }
 @Override
 public String toString() {
   System.out.print("[");
  for (Song song: playList){
   if(playList.isLast()){
    song.printLast();
   }
  else {
   song.toString();
   }
  }
  System.out.println("]");

 }
 @Override
 public boolean equals(Object obj) {
 }

 @Override
 public int hashCode() {
 }

 @Override
 public Iterator<Song> iterator() {
  ArrayList<Song> arr = (ArrayList<Song>) this.playList.clone();
  arr = this.filterArtistAux(arr);
  arr = this.filterFurationAux(arr);
  arr = this.filterArtistAux(arr);
  Iterator<Song> arrIterator= new PlaylistIterator(arr.iterator());
  return arrIterator;
 }

 public class PlaylistIterator implements Iterator<Song>{
  private Iterator<Song> iterator;
  public PlaylistIterator(Iterator iterator){
   this.iterator = iterator;
  }
  @Override
 public boolean hasNext() {
 return iterator.hasNext();
  }
 @Override
 public Song next() {
 return iterator.next();
 }

 }


}








