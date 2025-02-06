package org.example.model

data class Menu(val name: String, val description: String, val products: MutableList<Product> = mutableListOf())
