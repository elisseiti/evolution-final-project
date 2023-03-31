package domain.entity

object OrderStatus extends Enumeration {
  type OrderStatus = Value
  val PLACED, CANCELED, PROCESSING, IN_ROUTE, DELIVERED, RECEIVED = Value
}


