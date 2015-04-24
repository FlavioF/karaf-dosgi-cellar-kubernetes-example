/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.karaf.cellar.samples.dosgi.greeter.service;

import org.apache.karaf.cellar.samples.dosgi.greeter.api.Greet;
import org.apache.karaf.cellar.samples.dosgi.greeter.api.GreetResponse;
import org.apache.karaf.cellar.samples.dosgi.greeter.api.Greeter;
import org.apache.karaf.cellar.samples.dosgi.greeter.api.MyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the Greeter servicce.
 */
public class GreeterImpl implements Greeter {

  private static final transient Logger log = LoggerFactory.getLogger(GreeterImpl.class);
  
  private int counter = 0;
  private String id;

  public GreeterImpl(String id) {
    this.id = id;
  }

  @Override
  public GreetResponse greet(MyEvent myEvent, Greet greet) {

    log.info("Processing Greet..");

    String message = greet.getMessage();
    String response = message
        + "."
        + String.format("Hello from node %s count %s. ----------> %s", id,
            counter++, myEvent.shout());
    GreetResponse greetResponse = new GreetResponse(greet, response);

    if (message.equals("x")) {
      throw new RuntimeException(">>>>>>>>>>>>This is My Exception<<<<<<<<<<<<<<<<<<<");
    }

    
    log.info("Responding to greet with message <{}>", response);
    
    return greetResponse;
  }
}
