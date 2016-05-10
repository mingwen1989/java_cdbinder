import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Genre {
  private int id;
  private String genre_type;

  public Genre(String genre_type) {
    this.genre_type = genre_type;
  }

  public String getGenreType() {
    return genre_type;
  }

  public int getId() {
    return id;
  }

  public static List<Genre> allGenres() {
    String sql = "SELECT id, genre_type FROM genres";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Genre.class);
    }
  }

  @Override
  public boolean equals(Object otherGenre) {
    if (!(otherGenre instanceof Genre)) {
      return false;
    } else {
      Genre newGenre = (Genre) otherGenre;
      return this.getGenreType().equals(newGenre.getGenreType()) &&
             this.getId() == newGenre.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO genres(genre_type) VALUES (:genre_type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("genre_type", this.genre_type)
        .executeUpdate()
        .getKey();
    }
  }

  public static Genre find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM genres where id=:id";
      Genre genre = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Genre.class);
      return genre;
    }
  }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM genres WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM albums_genres WHERE genre_id = :genre_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("genre_id", this.getId())
        .executeUpdate();
    }
  }

  public void update(String newGenre) {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE genres SET genre_type = :genre_type WHERE id = :id";
      con.createQuery(sql)
      .addParameter("genre_type", newGenre)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void addAlbum(Album album) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO albums_genres (album_id, genre_id) VALUES (:album_id, :genre_id)";
      con.createQuery(sql)
      .addParameter("genre_id", this.getId())
      .addParameter("album_id", album.getId())
      .executeUpdate();
    }
  }

  public List<Album> getAlbums() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT album_id FROM albums_genres WHERE genre_id = :genre_id";
      List<Integer> albumIds = con.createQuery(joinQuery)
        .addParameter("genre_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Album> albums = new ArrayList<Album>();

      for (Integer albumId : albumIds) {
        String albumQuery = "SELECT * FROM albums WHERE id = :albumId";
        Album album = con.createQuery(albumQuery)
          .addParameter("albumId", albumId)
          .executeAndFetchFirst(Album.class);
        albums.add(album);
      }
      return albums;
    }
  }
}
