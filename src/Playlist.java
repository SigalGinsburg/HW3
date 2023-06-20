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

 public void addSong (Song songToAdd){
  for(Song song:this.playlist){
    if (song.equals(songToAdd)){
     throw new SongAlreadyExistsException();
    }
  }
  this.playlist.add(songToAdd);
 }

 public boolean removeSong (Song songToRemove) {
  for (Song song : this.playlist) {
   if (song.equals(songToRemove)) {
    this.playlist.remove(songToRemove);
    return true;
   }
  }

  return false;
 }
 @Override
 protected Playlist clone() {
  try {
   Playlist copy= (Playlist) super.clone();
   copy.playlist = new ArrayList<>(playlist.size());
   for(int i = 0; i < playlist.size(); i++){
    copy.playlist.add(playlist.get(i).clone());
   }
//   copy.playlist=(ArrayList<Song>) playlist.clone();
   return copy;
  } catch (CloneNotSupportedException e) {
   return null;
      }
  }


 @Override
 public String toString() {
  StringBuilder playlistString = new StringBuilder();
  playlistString.append("[");
  for (int i = 0; i < this.playlist.size() - 1; i++) {
   playlistString.append("(");
   playlistString.append(playlist.get(i).toString());
   playlistString.append(")");
   playlistString.append(", ");
  }
  playlistString.append("(");
  playlistString.append(this.playlist.get(this.playlist.size() - 1).toString());
  playlistString.append(")");
  playlistString.append("]");
  String result= playlistString.toString();
  return result;
 }

 @Override
 public boolean equals(Object other) {
  if (!(other instanceof Playlist)) {
   return false;
  }
  Playlist otherPlayList = (Playlist) other;
  if (this.playlist.size()  != otherPlayList.playlist.size()){return false;}
  ArrayList<Song> myOrderedList = (ArrayList<Song>) this.playlist.clone();
  ArrayList<Song> otherOrderedList = (ArrayList<Song>) otherPlayList.playlist.clone();
  setScanningOrderAux(otherOrderedList, ScanningOrder.NAME);
  setScanningOrderAux(myOrderedList, ScanningOrder.NAME);
  for(int i = 0; i < this.playlist.size(); i++){
   Song otherSong= otherOrderedList.get(i);
   Song mySong= myOrderedList.get(i);
   if (!otherSong.equals(mySong)){
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

 public ArrayList<Song> getPlaylist() {
  return playlist;
 }


 @Override
 public void filterArtist(String artist) {
   this.filterArtist=artist;
 }
 @Override
 public void filterGenre(Song.Genre genre) {
   this.filterGenre=genre;
 }
 @Override
 public void filterDuration(int duration) {
  this.filterDuration=duration;
 }

 @Override
 public void setScanningOrder(ScanningOrder order) {
  this.order=order;
 }


 private void filterArtistAux(ArrayList<Song> filteredByArtistPlaylist){
  if (this.filterArtist==null){ return;}
  for (Song song: this.playlist){
      if (!(song.getArtist().equals(this.filterArtist))){
         filteredByArtistPlaylist.remove(song);
    }
   }
 }

 private void filterGenreAux(ArrayList<Song> filteredByGenrePlaylist){
  if (this.filterGenre==null){ return;}
  for (Song song: this.playlist){
   if (!(song.getGenre().equals(this.filterGenre))){
    filteredByGenrePlaylist.remove(song);
   }
  }
 }

 private void setScanningOrderAux(ArrayList<Song> orderedPlaylist, ScanningOrder order){
  if(order==ScanningOrder.NAME){
   Comparator<Song> byName = Comparator.comparing(Song::getName);
   Collections.sort(orderedPlaylist, byName);
  }
  if (order==ScanningOrder.DURATION) {
   Comparator<Song> byAge = Comparator.comparingInt(Song::getDurationInSec);
   Collections.sort(orderedPlaylist, byAge);
  }
 }

 private void filterDurationAux(ArrayList<Song> filteredByDurationPlaylist){
  for (Song song: this.playlist){
   if (song.getDurationInSec() > this.filterDuration){
     filteredByDurationPlaylist.remove(song);
   }
  }
 }
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








