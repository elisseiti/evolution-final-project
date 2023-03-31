package domain.entity

case class Meal(description: String, name: String, price: Double, restaurantId: Long)
case class MealWithId(id: Long, description: String, name: String, price: Double, restaurantId: Long)
