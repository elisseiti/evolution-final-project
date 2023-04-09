package domain.entity

case class Restaurant(id: Option[Long] = None, description: String, name: String, ownerId: Long)
