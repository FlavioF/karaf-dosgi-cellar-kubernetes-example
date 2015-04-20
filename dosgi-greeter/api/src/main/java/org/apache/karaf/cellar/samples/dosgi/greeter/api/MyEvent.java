package org.apache.karaf.cellar.samples.dosgi.greeter.api;

import java.io.Serializable;

public interface MyEvent extends Serializable {

  String shout();
}
