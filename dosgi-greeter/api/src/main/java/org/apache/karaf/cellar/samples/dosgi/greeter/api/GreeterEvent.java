package org.apache.karaf.cellar.samples.dosgi.greeter.api;

public class GreeterEvent implements MyEvent {

  @Override
  public String shout() {
    return "YOOOOOOOOOO!";
  }
}
