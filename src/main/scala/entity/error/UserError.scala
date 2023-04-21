package entity.error

sealed trait UserError
object UserError {
  case object UsernameAlreadyExists extends UserError
  // And other *domain* errors
}