package org.example.model

import java.time.LocalDateTime

data class Order(
    val orderTime: LocalDateTime = LocalDateTime.now(),
    var message: String = "",
    val cart: MutableList<Product> = mutableListOf()
)