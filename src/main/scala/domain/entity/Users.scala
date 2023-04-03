package domain.entity

import java.time.LocalDate

sealed trait Users
case class RegularUser(id: Long, email: String, name: String, surname: String, birthday: LocalDate, password: String, gender: Char) extends Users
case class RegularUserWithId(email: String, name: String, surname: String, birthday: LocalDate, password: String, gender: Char) extends Users

case class Owner(email: String, name: String, surname: String, birthday: LocalDate, password: String, gender: Char) extends Users
case class OwnerWithId(id: Long, email: String, name: String, surname: String, birthday: LocalDate, password: String, gender: Char) extends Users
