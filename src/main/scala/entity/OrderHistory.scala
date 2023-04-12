package entity

import OrderStatus.OrderStatus

import java.time.Instant

case class OrderHistory(id: Option[Long] = None, orderId: Long, status: OrderStatus, orderedTime: Instant = Instant.now())
