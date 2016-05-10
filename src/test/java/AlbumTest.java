import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class AlbumTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Album_Instantiates_true() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    assertEquals(true, myAlbum instanceof Album);
  }

  @Test
  public void getAlbumInfo_albumInstantiates_String() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    assertEquals("Lemonade", myAlbum.getTitle());
    assertEquals("Beyonce", myAlbum.getArtist());
    assertEquals("2016", myAlbum.getReleaseDate());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Album.allAlbums().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Album firstAlbum = new Album("Lemonade", "Beyonce", "2016");
    Album secondAlbum = new Album("Lemonade", "Beyonce", "2016");
    assertTrue(firstAlbum.equals(secondAlbum));
  }
  @Test
  public void save_savesIntoDatabase_true() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    assertTrue(Album.allAlbums().get(0).equals(myAlbum));
  }

  @Test
  public void save_assignsIdToObject() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    Album savedAlbum = Album.allAlbums().get(0);
    assertEquals(myAlbum.getId(), savedAlbum.getId());
  }

  @Test
  public void find_findAlbumInDatabase_true() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    Album savedAlbum = Album.find(myAlbum.getId());
    assertTrue(myAlbum.equals(savedAlbum));
  }
  @Test
  public void addGenre_addsGenreToAlbum_true() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    myAlbum.addGenre(myGenre);
    Genre savedGenre = myAlbum.getGenres().get(0);
    assertTrue(myGenre.equals(savedGenre));
  }
  @Test
  public void getGenres_returnsAllGenres_List() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    myAlbum.addGenre(myGenre);
    List savedGenres = myAlbum.getGenres();
    assertEquals(1, savedGenres.size());
  }

  @Test
  public void delete_deletesAllGenresAndAlbumsAssociations() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    Genre myGenre = new Genre("Pop");
    myAlbum.addGenre(myGenre);
    myAlbum.delete();
    assertEquals(0, myGenre.getAlbums().size());
  }

  @Test
  public void update_updatesTitle_true() {
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    myAlbum.update("ANTI", "Rihanna", "2015");
    assertEquals("ANTI", Album.find(myAlbum.getId()).getTitle());
    assertEquals("Rihanna", Album.find(myAlbum.getId()).getArtist());
    assertEquals("2015", Album.find(myAlbum.getId()).getReleaseDate());
  }
  // @Test
  // public void update_updatesArtist_true() {
  //   Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
  //   myAlbum.save();
  //   myAlbum.updateArtist("Rihanna");
  //   assertEquals("Rihanna", Album.find(myAlbum.getId()).getArtist());
  // }
  //
  // @Test
  // public void update_updatesReleaseDate_true() {
  //   Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
  //   myAlbum.save();
  //   myAlbum.updateReleaseDate("2015");
  //   assertEquals("2015", Album.find(myAlbum.getId()).getReleaseDate());
  // }
}
