package service

import algebras.RestaurantRepositoryAlgebra
import algebras.validation.RestaurantValidationAlgebra
import cats.Monad
import cats.data._
import cats.syntax.all._
import entity.Restaurant
import entity.error.{RestaurantAlreadyExistsError, RestaurantDoesNotExist}

trait RestaurantServiceTrait[F[_]] {
  def create(restaurant: Restaurant): EitherT[F, RestaurantAlreadyExistsError, Restaurant]
  def getRestaurantById(id: Long): EitherT[F, RestaurantDoesNotExist.type , Restaurant]
  def delete(restaurantId: Long): EitherT[F, RestaurantDoesNotExist.type , Restaurant]
  def getRestaurantsByOwnerId(id: Long): F[List[Restaurant]]
  def getRestaurants(): F[List[Restaurant]]
  def update(restaurant: Restaurant): EitherT[F, RestaurantDoesNotExist.type, Restaurant]
}

class RestaurantService[F[_]: Monad](restaurantRepository: RestaurantRepositoryAlgebra[F],
                                      restaurantValidation: RestaurantValidationAlgebra[F]
                                     )
  extends RestaurantServiceTrait[F] {


  override def create(restaurant: Restaurant): EitherT[F, RestaurantAlreadyExistsError, Restaurant] =
    (for {
      _ <- EitherT(restaurantValidation.doesNotExist(restaurant.id))
      saved <- EitherT.liftF(restaurantRepository.create(restaurant))
    } yield saved)

  override def getRestaurantById(id: Long): EitherT[F, RestaurantDoesNotExist.type , Restaurant] = EitherT.fromOptionF(restaurantRepository.get(id), RestaurantDoesNotExist)

  override def delete(restaurantId: Long): EitherT[F, RestaurantDoesNotExist.type , Restaurant] = EitherT.fromOptionF(restaurantRepository.delete(restaurantId), RestaurantDoesNotExist)

  override def getRestaurantsByOwnerId(id: Long): F[List[Restaurant]] = restaurantRepository.getRestaurantsByOwnerId(id)

  override def getRestaurants(): F[List[Restaurant]] = restaurantRepository.getRestaurants()

  override def update(restaurant: Restaurant): EitherT[F, RestaurantDoesNotExist.type, Restaurant] =
    for {
    _ <- EitherT(restaurantValidation.exists(restaurant.id))
    saved <- EitherT.fromOptionF(restaurantRepository.update(restaurant), RestaurantDoesNotExist)
  } yield saved
}


object RestaurantService {
  object RestaurantService {
    def apply[F[_] : Monad](
                             restaurantRepository: RestaurantRepositoryAlgebra[F],
                             restaurantValidation: RestaurantValidationAlgebra[F],
                           ):
    RestaurantService[F] =
      new RestaurantService[F](restaurantRepository, restaurantValidation)
  }
}

