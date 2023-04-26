package entity.error

import entity.{Meal, Restaurant}

sealed trait Errors
object Errors {
  case object MealDoesNotExist extends Errors
  case class MealAlreadyExistsError(meal: Meal) extends Errors
  case object RestaurantDoesNotExist extends Errors
  case class RestaurantAlreadyExistsError(restaurant: Restaurant) extends Errors

  // And other *domain* errors
}

