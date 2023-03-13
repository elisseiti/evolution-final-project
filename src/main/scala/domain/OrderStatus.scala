package domain

object OrderStatus extends Enumeration {
  type CardSuit = Value
  val PLACED, CANCELED, PROCESSING, IN_ROUTE, DELIVERED, RECEIVED = Value
}
