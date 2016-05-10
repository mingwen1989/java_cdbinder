import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class GenreTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Genre_Instantiates_true() {
    Genre myGenre = new Genre("Pop");
    assertEquals(true, myGenre instanceof Genre);
  }

  @Test
  public void getGenreInfo_GenreInstantiates_String() {
    Genre myGenre = new Genre("Pop");
    assertEquals("Pop", myGenre.getGenreType());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Genre.allGenres().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Genre firstGenre = new Genre("Pop");
    Genre secondGenre = new Genre("Pop");
    assertTrue(firstGenre.equals(secondGenre));
  }
  @Test
  public void save_savesIntoDatabase_true() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    assertTrue(Genre.allGenres().get(0).equals(myGenre));
  }

  @Test
  public void save_assignsIdToObject() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    Genre savedGenre = Genre.allGenres().get(0);
    assertEquals(myGenre.getId(), savedGenre.getId());
  }

  @Test
  public void find_findGenreInDatabase_true() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    Genre savedGenre = Genre.find(myGenre.getId());
    assertTrue(myGenre.equals(savedGenre));
  }

  @Test
  public void addAlbum_addsAlbumToGenre_true() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    myGenre.addAlbum(myAlbum);
    Album savedAlbum = myGenre.getAlbums().get(0);
    assertTrue(myAlbum.equals(savedAlbum));
  }

  @Test
  public void getAlbums_returnsAllAlbums_List() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myAlbum.save();
    myGenre.addAlbum(myAlbum);
    List savedAlbums = myGenre.getAlbums();
    assertEquals(1, savedAlbums.size());
  }

  @Test
  public void delete_deletesAllAlbumsAndGenresAssociations() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    Album myAlbum = new Album("Lemonade", "Beyonce", "2016");
    myGenre.addAlbum(myAlbum);
    myGenre.delete();
    assertEquals(0, myAlbum.getGenres().size());
  }

  @Test
  public void update_updatesGenre_true() {
    Genre myGenre = new Genre("Pop");
    myGenre.save();
    myGenre.update("Hardcore");
    assertEquals("Hardcore", Genre.find(myGenre.getId()).getGenreType());
  }
}
