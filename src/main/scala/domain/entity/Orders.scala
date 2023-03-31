package domain.entity

case class Orders(userId: Long, restaurantId: Long, mealIds: Set[Long], totalPrice: Double)
case class OrdersWithId(id: Long, userId: Long, restaurantId: Long, mealIds: Set[Long], totalPrice: Double)
