package domain.entity

import domain.entity.OrderStatus.OrderStatus

import java.time.Instant

case class OrderHistory(id: Option[Long] = None, orderId: Long, status: OrderStatus, timestamp: Instant = Instant.now())
