package entity.error

import entity.Restaurant

sealed trait Errors
object Errors {
  case object MealAlreadyExists extends Errors
  case object RestaurantDoesNotExist extends Errors
  case class RestaurantAlreadyExistsError(restaurant: Restaurant) extends Errors

  // And other *domain* errors
}

