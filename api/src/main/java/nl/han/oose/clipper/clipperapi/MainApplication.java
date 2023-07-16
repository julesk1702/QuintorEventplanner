package nl.han.oose.clipper.clipperapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @GetMapping("/")
  public String getHome() {
    return "Gebruik <b>/api/v1/live</b> om te kijken of <i>'Hello World!'</i> werkt.";
  }
}
