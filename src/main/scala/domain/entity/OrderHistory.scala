package domain.entity

import domain.entity.OrderStatus.OrderStatus

import java.time.Instant

  case class OrderHistory(id: Long, orderId: Long, status: OrderStatus, timestamp: Instant = Instant.now())
  case class OrderHistoryWithId(orderId: Long, status: OrderStatus, timestamp: Instant = Instant.now())
