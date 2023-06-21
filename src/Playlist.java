import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Playlist implements Cloneable, OrderedSongIterable,FilteredSongIterable {
 private ArrayList<Song> playlist;
 private String filterArtist;
 private Song.Genre filterGenre;
 private int filterDuration;
 private ScanningOrder order;


 public Playlist(){
  this.playlist= new ArrayList<Song>();
  this.filterArtist=null;
  this.filterGenre=null;
  this.order= ScanningOrder.ADDING;
  this.filterDuration=Integer.MAX_VALUE;
 }

 /**
  * adding a song to the playlist
  * @param songToAdd the song that needs to be added.
  */
 public void addSong (Song songToAdd){
  for(Song song:this.playlist){
    if (song.equals(songToAdd)){ //checking if the song is already in the playlist
     throw new SongAlreadyExistsException(); //throws an exception
    }
  }
  this.playlist.add(songToAdd); //adding the song
 }

 /**
  * removes a song from the playlist
  * @param songToRemove the song that needs to be removed
  * @return
  */
 public boolean removeSong (Song songToRemove) {
  for (Song song : this.playlist) {
   if (song.equals(songToRemove)) { //if the song was in the playlist anthill now
    this.playlist.remove(songToRemove); //remove the song
    return true;
   }
  }
  return false;
 }

 /** deep copy of a playlist, as we learned to implement in the course **/
 @Override
 protected Playlist clone() {
  try {
   Playlist copy= (Playlist) super.clone();
   copy.playlist = new ArrayList<>(playlist.size());
   for(int i = 0; i < playlist.size(); i++){
    copy.playlist.add(playlist.get(i).clone());
   }
   return copy;
  } catch (CloneNotSupportedException e) {
   return null;
      }
  }

 /**
  * creates a string representing 'this' playlist
  * @return the string
  */
 @Override
 public String toString() {
  StringBuilder playlistString = new StringBuilder();
  playlistString.append("["); //beginning of the playlist
  for (int i = 0; i < this.playlist.size() - 1; i++) {
   playlistString.append("("); //beginning of a song
   playlistString.append(playlist.get(i).toString());
   playlistString.append(")");//end of the song
   playlistString.append(", ");
  }
  playlistString.append("("); //last song in the playlist
  playlistString.append(this.playlist.get(this.playlist.size() - 1).toString());
  playlistString.append(")");
  playlistString.append("]");// end of the playlist
  String result= playlistString.toString();
  return result;
 }

 /**
  * checks whether two playlist are equals i.e. containing the same song, not necessarily in the same order.
  * @param other the playlist that is compared to 'this' playlist.
  * @return boolean answer
  */
 @Override
 public boolean equals(Object other) {
  if (!(other instanceof Playlist)) {
   return false;
  }
  Playlist otherPlayList = (Playlist) other;
  //if playlists not in the same size
  if (this.playlist.size()  != otherPlayList.playlist.size()){return false;}
  // creating a copy of this song's list
  ArrayList<Song> myOrderedList = (ArrayList<Song>) this.playlist.clone();
  // creating a copy of the other song's list
  ArrayList<Song> otherOrderedList = (ArrayList<Song>) otherPlayList.playlist.clone();
// organize the copies of the playlists by the name of the songs.
  setScanningOrderAux(otherOrderedList, ScanningOrder.NAME);
  setScanningOrderAux(myOrderedList, ScanningOrder.NAME);

  for(int i = 0; i < this.playlist.size(); i++){
   Song otherSong= otherOrderedList.get(i);
   Song mySong= myOrderedList.get(i);
   if (!otherSong.equals(mySong)){ // comparing every song with the same spot in the other list
    return false;
   }
  }
  return true;
 }

 @Override
 public int hashCode() {
  ArrayList<Song> tmp = (ArrayList<Song>) playlist.clone();
  setScanningOrderAux(tmp, ScanningOrder.NAME);
  int result= tmp.hashCode();
  return result;
 }

 /** saving an artist's name for filter by artist as a property. **/
 @Override
 public void filterArtist(String artist) {
   this.filterArtist=artist;
 }
 /** saving a genre for filter by genre as a property. **/
 @Override
 public void filterGenre(Song.Genre genre) {
   this.filterGenre=genre;
 }
 /** saving a duration for filter by duration as a property. **/
 @Override
 public void filterDuration(int duration) {
  this.filterDuration=duration;
 }
 /** saving an order for organizing the playlist as a property. **/
 @Override
 public void setScanningOrder(ScanningOrder order) {
  this.order=order;
 }

 /**
  * implements artist filter on the playlist
  * i.e. removes all songs that aren't with the same artist as this.filterArtist.
  * @param filteredByArtistPlaylist the list of songs that we are editing
  */
 private void filterArtistAux(ArrayList<Song> filteredByArtistPlaylist){
  if (this.filterArtist==null){ return;} // if filer value is null the meaning is no filter
  for (Song song: this.playlist){ //checking each song in the list
   if (!(song.getArtist().equals(this.filterArtist))){ // if the artist of the song isn't the same as this.filterArtist
         filteredByArtistPlaylist.remove(song); // remove the song
    }
   }
 }
 /**
  * implements genre filter on the playlist
  * i.e. removes all songs that aren't with the same genre as this.filterGenre.
  * @param filteredByGenrePlaylist the list of songs that we are editing
  */

 private void filterGenreAux(ArrayList<Song> filteredByGenrePlaylist){
  if (this.filterGenre==null){ return;}// if filer value is null the meaning is no filter
  for (Song song: this.playlist){//checking each song in the list
   if (!(song.getGenre().equals(this.filterGenre))){// if the genre of the song isn't the same as this.filterGenre
    filteredByGenrePlaylist.remove(song);// remove the song
   }
  }
 }
 /**
  * implements duration filter on the playlist
  * i.e. removes all songs with a bigger duration then this.filterDuration.
  * @param  filteredByDurationPlaylist the list of songs that we are editing
  */
 private void filterDurationAux(ArrayList<Song> filteredByDurationPlaylist){
  for (Song song: this.playlist){ //checking each song in the list
   if (song.getDurationInSec() > this.filterDuration){ // if duration is bigger then this.filterDuration
    filteredByDurationPlaylist.remove(song);// remove the song
   }
  }
 }
 /**
  * organize the songs in the playlist
  * @param orderedPlaylist the list we are editing
  * @param order in what order arrange the list
  */
 private void setScanningOrderAux(ArrayList<Song> orderedPlaylist, ScanningOrder order){
  if(order==ScanningOrder.NAME){ // organize by the names of the songs
   Comparator<Song> byName = Comparator.comparing(Song::getName);
   Collections.sort(orderedPlaylist, byName);
  }
  if (order==ScanningOrder.DURATION) { // organize by duration of the songs
   Comparator<Song> byAge = Comparator.comparingInt(Song::getDurationInSec);
   Collections.sort(orderedPlaylist, byAge);
  }
 }
 //-> missing
 @Override
 public Iterator<Song> iterator() {
  if (this.playlist ==null) {
      return null;
  }
  ArrayList<Song> newPlayList = (ArrayList<Song>) this.playlist.clone();
  filterArtistAux(newPlayList);
  filterGenreAux(newPlayList);
  filterDurationAux(newPlayList);
  setScanningOrderAux(newPlayList, this.order);
  Iterator<Song> newPlayListIterator= new PlaylistIterator(newPlayList.iterator());
  return newPlayListIterator;
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








