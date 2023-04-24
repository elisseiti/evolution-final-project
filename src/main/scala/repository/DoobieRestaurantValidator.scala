package repository

import algebras.{RestaurantRepositoryAlgebra, RestaurantValidationAlgebra}
import cats.Applicative
import cats.data.EitherT
import entity.error.Errors.RestaurantDoesNotExist
import cats.syntax.all._


class DoobieRestaurantValidator[F[_]: Applicative](repository: RestaurantRepositoryAlgebra[F])
  extends RestaurantValidationAlgebra[F] {
  override def exists(petId: Option[Long]): EitherT[F, RestaurantDoesNotExist.type, Unit] = EitherT {
    petId match {
      case Some(id) =>
        // Ensure is a little tough to follow, it says "make sure this condition is true, otherwise throw the error specified
        // In this example, we make sure that the option returned has a value, otherwise the pet was not found
        repository.get(id).map {
          case Some(_) => Right(())
          case _ => Left(RestaurantDoesNotExist)
        }
      case _ =>
        Either.left[RestaurantDoesNotExist.type, Unit](RestaurantDoesNotExist).pure[F]
    }
  }
}
