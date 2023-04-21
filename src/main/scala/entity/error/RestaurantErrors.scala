package entity.error

sealed trait RestaurantErrors
object RestaurantErrors {
  case object RestaurantAlreadyExists extends RestaurantErrors
  // And other *domain* errors
}
