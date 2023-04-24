package entity.error

sealed trait Errors
object Errors {
  case object MealAlreadyExists extends Errors
  case object RestaurantDoesNotExist extends Errors
  // And other *domain* errors
}
