package entity

import java.time.LocalDate
sealed trait Users
case class RegularUser(id: Option[Long] = None, username: String, name: String, surname: String, birthday: Option[LocalDate], email: String, password: String, gender: String) extends Users

case class Owner(id: Option[Long] = None, username: String, name: String, surname: String,  birthday: Option[LocalDate], email: String, password: String, gender: String) extends Users
