package repository.validation

import algebras.validation.MealValidationAlgebra
import algebras.MealRepositoryAlgebra
import cats.Applicative
import cats.syntax.all._
import entity.error.Errors.{MealAlreadyExistsError, MealDoesNotExist}


class DoobieMealValidator[F[_]: Applicative](repository: MealRepositoryAlgebra[F])
  extends MealValidationAlgebra[F] {

  override def exists(mealId: Option[Long]): F[Either[MealDoesNotExist.type, Unit]] =
    mealId match {
      case Some(id) =>
        // Ensure is a little tough to follow, it says "make sure this condition is true, otherwise throw the error specified
        // In this example, we make sure that the option returned has a value, otherwise the Restaurant was not found
        repository.get(id).map {
          case Some(_) => Right(())
          case _ => Left(MealDoesNotExist)
        }
      case _ =>
        Either.left[MealDoesNotExist.type, Unit](MealDoesNotExist).pure[F]
    }


  override def doesNotExist(mealId: Option[Long]): F[Either[MealAlreadyExistsError.type, Unit]] = {
    mealId match {
      case Some(id) =>
        // Ensure is a little tough to follow, it says "make sure this condition is true, otherwise throw the error specified
        // In this example, we make sure that the option returned has a value, otherwise the Restaurant was not found
        repository.get(id).map {
          case Some(_) => Left(MealAlreadyExistsError)
          case _ => Right(())
        }


      case _ =>
        Either.left[MealAlreadyExistsError.type, Unit](MealAlreadyExistsError).pure[F]
    }
  }
}


object DoobieMealValidator {
  def apply[F[_]: Applicative](repository: MealRepositoryAlgebra[F]) =
    new DoobieMealValidator[F](repository)
}



