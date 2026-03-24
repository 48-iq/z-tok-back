package dev.ztok.back.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

  @GetMapping("/api/hello-world")
  public String helloWorld() {
    return "<h1>Hello world!!!</h1>";
  }
}
