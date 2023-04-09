package domain.entity

case class Orders(id: Option[Long] = None, userId: Long, restaurantId: Long, mealIds: Set[Long], totalPrice: Double)
