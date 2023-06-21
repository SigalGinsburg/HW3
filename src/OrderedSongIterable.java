/**
 * interface for setting scanning order of an iterable
 */
public interface OrderedSongIterable extends Iterable<Song>{
    public void setScanningOrder(ScanningOrder scanningOrder);

}
