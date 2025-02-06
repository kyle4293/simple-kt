package org.example.controller

import java.time.LocalDateTime

object OrderController {
    private var orderCounter = 1 // 주문번호 자동 증가 변수
    val waitList = mutableListOf<OrderData>()
    val completeList = mutableListOf<OrderData>()

    fun addOrder(cart: Map<String, Pair<Double, Int>>, message: String) {
        val totalPrice = cart.values.sumOf { it.first * it.second }
        val orderData = OrderData(orderCounter++, LocalDateTime.now(), message, cart, totalPrice)
        waitList.add(orderData)
    }

    fun completeOrder(orderId: Int) {
        val index = waitList.indexOfFirst { it.orderId == orderId }
        if (index != -1) {
            completeList.add(waitList.removeAt(index))
        }
    }
}

data class OrderData(
    val orderId: Int, // 고유 주문번호 추가
    val orderTime: LocalDateTime,
    val message: String,
    val cart: Map<String, Pair<Double, Int>>, // 상품명 -> (개별 가격, 수량)
    val totalPrice: Double
)
