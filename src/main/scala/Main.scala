// Main.scala
import cats.effect._
import com.comcast.ip4s._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router

object Main extends IOApp {

  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }


  // Different HttpRoutes can be combined via combineK / <+>
  // val httpApp =
  //   userRoutes.routes.combineK(helloWorldService)

  // Or more conveniently via Router, as it allows us to further scope (prefix) these routes
  val httpApp =
    Router(
      "/" -> helloWorldService,
    ).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}


