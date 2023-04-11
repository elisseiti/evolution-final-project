package domain.entity

case class Meal(id: Option[Long] = None, description: String, name: String, price: Double, restaurantId: Long)
