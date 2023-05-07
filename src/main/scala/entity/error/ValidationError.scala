package entity.error

sealed trait ValidationError{
  def errorMessage: String
}


case object MealDoesNotExist extends ValidationError {
  override def errorMessage: String = ???
}
case class MealAlreadyExistsError(id: Long) extends ValidationError {
  override def errorMessage: String = ???
}
case object RestaurantDoesNotExist extends ValidationError {
  override def errorMessage: String = ???
}
case class RestaurantAlreadyExistsError(id: Long) extends ValidationError {
  override def errorMessage: String = ???
}

  // And other *domain* errors




