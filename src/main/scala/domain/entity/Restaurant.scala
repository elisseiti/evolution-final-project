package domain.entity

case class Restaurant(description: String, name: String, ownerId: Long)
case class RestaurantWithId(id: Long, description: String, name: String, ownerId: Long)
