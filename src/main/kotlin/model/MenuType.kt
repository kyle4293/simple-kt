package org.example.model

enum class MenuType(val displayName: String, val description: String, val products: List<Pair<String, Double>>) {
    BURGERS("Burgers", "앵거스 비프 통살을 다져 만든 버거", listOf(
        "ShackBurger" to 6900.0,
        "SmokeShack" to 8900.0,
        "Shroom Burger" to 9400.0,
        "Cheeseburger" to 6900.0
    )),
    DRINKS("Drinks", "매장에서 직접 만드는 음료", listOf(
        "Fountain Soda" to 3300.0,
        "Shake" to 5900.0,
        "Lemonade" to 4500.0
    )),
    DESSERTS("Desserts", "달콤한 디저트", listOf(
        "Ice Cream" to 5000.0,
        "Brownie" to 4500.0,
        "Cookie" to 3500.0
    ));

    companion object {
        fun getAllMenus(): List<MenuType> = values().toList()
    }
}
