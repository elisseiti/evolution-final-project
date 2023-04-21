package controller

import cats.effect.IO
import org.http4s.dsl.Http4sDsl
import service.MealService

final class MealController(mealService: MealService) extends Http4sDsl[IO] {

}
