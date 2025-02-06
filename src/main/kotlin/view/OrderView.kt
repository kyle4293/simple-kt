package org.example.view

import org.example.controller.OrderController
import org.example.model.MenuType
import javax.swing.*

class OrderView : JFrame("고객 주문") {
    private val cart = mutableMapOf<String, Pair<Double, Int>>() // 상품명 -> (가격, 수량)
    private val cartListModel = DefaultListModel<String>()
    private val cartListView = JList(cartListModel)
    private val productPanel = JPanel() // 상품 목록 패널

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(600, 500)
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        setupUI()
        isVisible = true
    }

    private fun setupUI() {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        panel.add(JLabel("장바구니"))
        panel.add(JScrollPane(cartListView))

        val menuPanel = JPanel()
        val menuListModel = DefaultListModel<String>()
        val menuListView = JList(menuListModel)

        MenuType.getAllMenus().forEach { menuListModel.addElement(it.displayName) }
        menuListView.selectionMode = ListSelectionModel.SINGLE_SELECTION

        menuListView.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                val selectedMenu = MenuType.getAllMenus().find { menu -> menu.displayName == menuListView.selectedValue }
                selectedMenu?.let { displayProductList(it) }
            }
        }

        panel.add(JLabel("메뉴 목록"))
        panel.add(JScrollPane(menuListView))

        val orderButton = JButton("주문하기")
        orderButton.addActionListener { placeOrder() }

        panel.add(orderButton)
        add(panel)
        add(productPanel) // 상품 목록 패널 추가
    }

    private fun displayProductList(menu: MenuType) {
        // 기존 상품 목록 제거 후 새로 추가
        productPanel.removeAll()
        productPanel.layout = BoxLayout(productPanel, BoxLayout.Y_AXIS)

        val productListModel = DefaultListModel<String>()
        val productListView = JList(productListModel)

        menu.products.forEach { productListModel.addElement("${it.first} | W ${it.second}") }
        productListView.selectionMode = ListSelectionModel.SINGLE_SELECTION

        productListView.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                val selectedProduct = menu.products.find { p -> p.first == productListView.selectedValue.split(" | ")[0] }
                selectedProduct?.let {
                    val quantity = JOptionPane.showInputDialog("수량을 입력하세요 (최소 1)").toIntOrNull() ?: 1
                    addToCart(it.first, it.second, quantity)
                }
            }
        }

        productPanel.add(JLabel("${menu.displayName} 상품 목록"))
        productPanel.add(JScrollPane(productListView))

        productPanel.revalidate()
        productPanel.repaint()
    }

    private fun addToCart(productName: String, price: Double, quantity: Int) {
        cart[productName] = price to (cart[productName]?.second?.plus(quantity) ?: quantity)
        updateCartDisplay()
    }

    private fun updateCartDisplay() {
        cartListModel.clear()
        var totalPrice = 0.0
        cart.forEach { (name, pair) ->
            val (price, quantity) = pair
            totalPrice += price * quantity
            cartListModel.addElement("$name | W $price x $quantity = W ${price * quantity}")
        }
        cartListModel.addElement("총 주문 금액: W $totalPrice")
    }

    private fun placeOrder() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "장바구니가 비어 있습니다!", "경고", JOptionPane.WARNING_MESSAGE)
            return
        }

        val message = JOptionPane.showInputDialog("요청사항을 입력하세요 (최대 20자)") ?: ""
        OrderController.addOrder(cart, message)
        cart.clear()
        cartListModel.clear()
        JOptionPane.showMessageDialog(this, "주문이 접수되었습니다!", "알림", JOptionPane.INFORMATION_MESSAGE)
    }
}
