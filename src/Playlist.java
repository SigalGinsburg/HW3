import java.util.ArrayList;
import java.util.Iterator;

public class Playlist implements Cloneable, OrderedSongIterable,FilteredSongIterable {
 private ArrayList<Song> playlist;
 private int length= playlist.size();
 private String filterArtist;
 private Song.Genre filterGenre;
 private int filterDuration;

 public Playlist(){
  this.playlist=null;
  this.filterArtist=null;
  this.filterGenre=null;
  this.filterDuration=Integer.MAX_VALUE;
 }

 private void addSong (Song songToAdd){
  for(Song song:this.playlist){
    if (song.equals(songToAdd)){
     //needs to add exception
     break;
    }
  }
  this.playlist.add(songToAdd);
 }

 private boolean removeSong (Song songToRemove) {
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
    copy.playlist.set(i, playlist.get(i).clone());
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
  for (int i = 0; i < this.length - 1; i++) {
   playlistString.append(playlist.get(i).toString());
   playlistString.append(",");
  }
  playlistString.append(this.playlist.get(length - 1).toString());
  playlistString.append("]");
  String result= playlistString.toString();
  return result;
 }

 @Override
 public boolean equals(Object obj) {
   //missing

 }

 @Override
 public int hashCode() {
  //missing
 }

 @Override
 public Iterator<Song> iterator() {
  ArrayList<Song> newPlayList = (ArrayList<Song>) this.playlist.clone();
  this.filterArtistAux(newPlayList);
  this.filterGenreAux(newPlayList);
  this.filterDurationAux(newPlayList);
  Iterator<Song> newPlayListIterator= new PlaylistIterator(newPlayList.iterator());
  return newPlayListIterator;
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


 private void filterArtistAux(ArrayList<Song> filteredByArtistPlaylist){
   for (Song song: filteredByArtistPlaylist){
      if (!(song.getArtist().equals(this.filterArtist))){
         filteredByArtistPlaylist.remove(song);
    }
   }
 }

 private void filterGenreAux(ArrayList<Song> filteredByGenrePlaylist){
  for (Song song: filteredByGenrePlaylist){
   if (!(song.getGenre().equals(this.filterGenre))){
    filteredByGenrePlaylist.remove(song);
   }
  }
 }

 private void filterDurationAux(ArrayList<Song> filteredByDurationPlaylist){
  for (Song song: filteredByDurationPlaylist){
   if (song.getDurationInSec() > this.filterDuration){
     filteredByDurationPlaylist.remove(song);
   }
  }
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








