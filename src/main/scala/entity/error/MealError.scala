package entity.error

sealed trait MealError
object MealError {
  case object MealAlreadyExists extends MealError
  // And other *domain* errors
}
