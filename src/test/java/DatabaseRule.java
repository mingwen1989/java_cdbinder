import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/cd_binder_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteGenresQuery = "DELETE FROM genres *;";
      String deleteAlbumsQuery = "DELETE FROM albums *;";
      String deleteAlbumsGenresQuery = "DELETE FROM albums_genres *;";
      con.createQuery(deleteGenresQuery).executeUpdate();
      con.createQuery(deleteAlbumsQuery).executeUpdate();
      con.createQuery(deleteAlbumsGenresQuery).executeUpdate();
    }
  }
}
