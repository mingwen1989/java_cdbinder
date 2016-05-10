import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model,layout);
    }, new VelocityTemplateEngine());

    get("/albums",(request, response) -> {
      HashMap<String, Object> model = new HashMap<String,Object>();
      model.put("albums", Album.allAlbums());
      model.put("template", "templates/albums.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/genres", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("genres", Genre.allGenres());
      model.put("template", "templates/genres.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/albums", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      String title = request.queryParams("albumTitle");
      String artist = request.queryParams("albumArtist");
      String releaseDate = request.queryParams("albumReleaseDate");
      Album newAlbum = new Album(title, artist, releaseDate);
      newAlbum.save();
      response.redirect("/albums");
      return null;
    });

    post("/genres", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String genreTitle = request.queryParams("genreTitle");
      Genre newGenre = new Genre(genreTitle);
      newGenre.save();
      response.redirect("/genres");
      return null;
    });

    get("/albums/:id", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Album album = Album.find(Integer.parseInt(request.params(":id")));
      model.put("albums", album);
      model.put("allGenres", Genre.allGenres());
      model.put("template", "templates/album.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_genre", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      int genreId = Integer.parseInt(request.queryParams("genre_id"));
      int albumId = Integer.parseInt(request.queryParams("album_id"));
      Album album = Album.find(albumId);
      Genre genre = Genre.find(genreId);
      album.addGenre(genre);
      response.redirect("/albums/" + albumId);
      return null;
    });
    post("/add_album", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      int genreId = Integer.parseInt(request.queryParams("genre_id"));
      int albumId = Integer.parseInt(request.queryParams("album_id"));
      Album album = Album.find(albumId);
      Genre genre = Genre.find(genreId);
      genre.addAlbum(album);
      response.redirect("/genres/" + genreId);
      return null;
    });

    post("/albums/:id/update", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Album album = Album.find(Integer.parseInt(request.params(":id")));
      String updatedAlbum = request.queryParams("updateAlbum");
      String updatedArtist = request.queryParams("updateArtist");
      String updatedReleaseDate = request.queryParams("updateReleaseDate");
      album.update(updatedAlbum, updatedArtist, updatedReleaseDate);
      model.put("album", album);
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("albums/:id/delete", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Album album = Album.find(Integer.parseInt(request.params(":id")));
      album.delete();
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("genres/:id", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Genre genre = Genre.find(Integer.parseInt(request.params(":id")));
      model.put("genres", genre);
      model.put("allAlbums", Album.allAlbums());
      model.put("template", "templates/genre.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/genres/:id/update", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Genre genre = Genre.find(Integer.parseInt(request.params(":id")));
      String updatedGenre = request.queryParams("updateGenre");
      genre.update(updatedGenre);
      model.put("genres", genre);
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("genres/:id/delete", (request, response) -> {
      HashMap<String,Object> model = new HashMap<String,Object>();
      Genre genre = Genre.find(Integer.parseInt(request.params(":id")));
      genre.delete();
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
