package controller

import cats.effect._
import entity.RegularUser
import entity.error.UserError
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import service.UserService

// DI of UserService, useful to stub/mock routes in tests
final class UserController(userService: UserService) extends Http4sDsl[IO] {

  implicit val codec: Codec[RegularUser] = deriveCodec

  final val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case request @ POST -> Root / "users" =>
      // `as` uses Codec & import org.http4s.circe.CirceEntityCodec._ to derive EntityDecoder of http4s
      // Equivalent to one below, just more condensed
      // request.as[User]
      //   .flatMap(userService.create)
      //   .flatMap {
      //     case Right(user) => Ok(user.asJson)
      //     case Left(UserError.UsernameAlreadyExists) => Conflict()
      //   }

      for {
        req       <- request.as[RegularUser]
        maybeUser <- userService.create(req)
        // Notice how Service errors coming from a domain are mapped here into corresponding HTTP constructs according to REST
        response  <- maybeUser match {
          case Right(user) => Ok(user)
          case Left(UserError.UsernameAlreadyExists) => Conflict()
        }
      } yield response

    case GET -> Root / "users" / username =>
      userService.get(username).flatMap(_.fold(NotFound())(Ok(_)))
  }
}
