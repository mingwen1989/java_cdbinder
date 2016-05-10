import java.util.List;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Album {
   private int id;
   private String title;
   private String artist;
   private String date_released;

   public Album(String title, String artist, String date_released) {
     this.title = title;
     this.artist = artist;
     this.date_released = date_released;
   }

   public String getTitle() {
     return title;
   }

   public String getArtist() {
     return artist;
   }

   public String getReleaseDate() {
     return date_released;
   }
   public int getId() {
     return id;
   }

   public static List<Album> allAlbums() {
     String sql = "SELECT id, title, artist, date_released FROM albums";
     try(Connection con = DB.sql2o.open()) {
       return con.createQuery(sql).executeAndFetch(Album.class);
     }
   }

   @Override
   public boolean equals(Object otherAlbum) {
     if(!(otherAlbum instanceof Album)) {
       return false;
     } else {
       Album newAlbum = (Album) otherAlbum;
       return this.getTitle().equals(newAlbum.getTitle()) &&
              this.getArtist().equals(newAlbum.getArtist()) &&
              this.getReleaseDate().equals(newAlbum.getReleaseDate());
     }
   }

  public static Album find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM albums WHERE id=:id";
      Album album = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Album.class);
      return album;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO albums(title, artist, date_released) VALUES (:title, :artist, :date_released)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("title", this.title)
          .addParameter("artist", this.artist)
          .addParameter("date_released", this.date_released)
          .executeUpdate()
          .getKey();
     }
   }

   public void update(String newTitle, String newArtist, String newReleaseDate) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "UPDATE albums SET title = :title, artist = :artist, date_released = :date_released WHERE id = :id";
       con.createQuery(sql)
       .addParameter("title", newTitle)
       .addParameter("artist", newArtist)
       .addParameter("date_released", newReleaseDate)
       .addParameter("id", this.id)
       .executeUpdate();
     }
   }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM albums WHERE id = :id";
      con.createQuery(deleteQuery)
        .addParameter("id", this.id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM albums_genres WHERE album_id = :album_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("album_id", this.getId())
          .executeUpdate();
    }
  }

  public void addGenre(Genre genre) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO albums_genres (album_id, genre_id) VALUES (:album_id, :genre_id)";
      con.createQuery(sql)
      .addParameter("album_id", this.getId())
      .addParameter("genre_id", genre.getId())
      .executeUpdate();
    }
  }

  public List<Genre> getGenres() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT genre_id FROM albums_genres WHERE album_id = :album_id";
      List<Integer> genreIds = con.createQuery(joinQuery)
        .addParameter("album_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Genre> genres = new ArrayList<Genre>();

      for (Integer genreId : genreIds) {
        String genreQuery = "SELECT * FROM genres WHERE id = :genreId";
        Genre genre = con.createQuery(genreQuery)
          .addParameter("genreId", genreId)
          .executeAndFetchFirst(Genre.class);
        genres.add(genre);
      }
      return genres;
    }
  }

}
