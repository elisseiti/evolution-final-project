package entity.error

sealed trait OrderError
object OrderError {
  case object OrderAlreadyExists extends OrderError
  // And other *domain* errors
}

