package entity.error

sealed trait OwnerError
object OwnerError {
  case object OwnerAlreadyExists extends OwnerError
  // And other *domain* errors
}

