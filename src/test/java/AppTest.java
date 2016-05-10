import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("My Cd Binder");
  }

  @Test
  public void albumIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("View Albums"));
    fill("#albumTitle").with("Lemonade");
    assertThat(pageSource()).contains("Lemonade");
  }

  @Test
  public void genreIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("View Genres"));
    fill("#genreTitle").with("Pop");
    assertThat(pageSource()).contains("Pop");
  }
}
