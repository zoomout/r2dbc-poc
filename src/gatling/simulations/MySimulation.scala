import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class MySimulation extends Simulation {

  object MyWebClient {

    def random(): Long = Random.nextLong()

    val putData: ChainBuilder = exec(
      http("SC1 - PUT")
        .put(_ => s"/api/${random()}?name=testName-${random()}")
        .check(
          status is 200
        )
    )

    val getData: ChainBuilder = exec(
      http("SC2 - GET")
        .get("/api/1")
        .check(
          status is 200
        )
    )

  }

  private val httpProtocol = http.baseUrl("http://localhost:8088")
    .disableFollowRedirect

  private val scenario1 = scenario("Scenario1") exec (MyWebClient.putData)
  private val scenario2 = scenario("Scenario2") exec (MyWebClient.getData)

  setUp(
    scenario1.inject(constantUsersPerSec(100) during (10 seconds)),
    scenario2.inject(constantUsersPerSec(100) during (10 seconds))
  ).protocols(httpProtocol)

}
