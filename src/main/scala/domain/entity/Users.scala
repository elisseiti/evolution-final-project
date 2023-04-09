package domain.entity

import java.time.LocalDate

sealed trait Users
case class RegularUser(id: Option[Long] = None, username: String, name: String, surname: String, birthday: LocalDate, email: String, password: String, gender: Char) extends Users

case class Owner(id: Option[Long] = None, user: String, email: String, name: String, surname: String, birthday: LocalDate, password: String, gender: Char) extends Users
