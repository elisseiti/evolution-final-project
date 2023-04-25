package repository

import algebras.{RestaurantRepositoryAlgebra, RestaurantValidationAlgebra}
import cats.Applicative
import cats.syntax.all._
import entity.error.Errors.{RestaurantAlreadyExistsError, RestaurantDoesNotExist}


class DoobieRestaurantValidator[F[_]: Applicative](repository: RestaurantRepositoryAlgebra[F])
  extends RestaurantValidationAlgebra[F] {

  override def exists(restaurantId: Option[Long]): F[Either[RestaurantDoesNotExist.type, Unit]] =
    restaurantId match {
      case Some(id) =>
        // Ensure is a little tough to follow, it says "make sure this condition is true, otherwise throw the error specified
        // In this example, we make sure that the option returned has a value, otherwise the Restaurant was not found
        repository.get(id).map {
          case Some(_) => Right(())
          case _ => Left(RestaurantDoesNotExist)
        }
      case _ =>
        Either.left[RestaurantDoesNotExist.type, Unit](RestaurantDoesNotExist).pure[F]
    }


  override def doesNotExist(restaurantId: Option[Long]): F[Either[RestaurantAlreadyExistsError.type, Unit]] = {
    restaurantId match {
      case Some(id) =>
        // Ensure is a little tough to follow, it says "make sure this condition is true, otherwise throw the error specified
        // In this example, we make sure that the option returned has a value, otherwise the Restaurant was not found
        repository.get(id).map {
          case Some(_) => Left(RestaurantAlreadyExistsError)
          case _ => Right(())
        }
      case _ =>
        Either.left[RestaurantAlreadyExistsError.type, Unit](RestaurantAlreadyExistsError).pure[F]
    }
  }
}


object DoobieRestaurantValidator {
  def apply[F[_]: Applicative](repository: RestaurantRepositoryAlgebra[F]) =
    new DoobieRestaurantValidator[F](repository)
}



