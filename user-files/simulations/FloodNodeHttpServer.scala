/*
 * Copyright 2011-2019 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fleet.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class FloodNodeHttpServerSimulation extends Simulation {

  val kibana_host = System.getProperty("kibana_host")

  val httpProtocol = http
    .baseUrl("https://" + kibana_host + ":5601") // Here is the root for all relative URLs

  val scn = scenario("Flood Node HTTP Server") // A scenario is a chain of requests and pauses
    .exec(
      http("request")
        .get("/")
        .check(status.is(session => 200))

    )

  setUp(scn.inject(rampUsers(50000) during (60 seconds)).protocols(httpProtocol))
}
