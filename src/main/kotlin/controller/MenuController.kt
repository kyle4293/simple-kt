package org.example.controller

import org.example.model.Menu
import org.example.model.Product

object MenuController {
    val menuList = mutableListOf(
        Menu("Burgers", "앵거스 비프 통살을 다져 만든 버거", mutableListOf(
            Product("ShackBurger", 6900.0, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"),
            Product("SmokeShack", 8900.0, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거")
        )),
        Menu("Drinks", "음료 및 쉐이크", mutableListOf(
            Product("Fountain Soda", 3300.0, "코카콜라, 코카콜라 제로, 스프라이트"),
            Product("Shake", 5900.0, "바닐라, 초콜렛, 솔티드 카라멜, 스트로베리")
        ))
    )

    fun addMenu(name: String, description: String) {
        menuList.add(Menu(name, description, mutableListOf()))
    }

    fun deleteMenu(index: Int) {
        if (index in menuList.indices) {
            menuList.removeAt(index)
        }
    }

    fun addProduct(menuIndex: Int, name: String, price: Double, description: String) {
        if (menuIndex in menuList.indices) {
            menuList[menuIndex].products.add(Product(name, price, description))
        }
    }

    fun deleteProduct(menuIndex: Int, productIndex: Int) {
        if (menuIndex in menuList.indices && productIndex in menuList[menuIndex].products.indices) {
            menuList[menuIndex].products.removeAt(productIndex)
        }
    }
}
