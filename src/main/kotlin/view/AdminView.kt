package org.example.view

import org.example.controller.OrderController
import org.example.controller.OrderData
import javax.swing.*

class AdminView : JFrame("관리자 메뉴") {
    private val waitListModel = DefaultListModel<String>()
    private val waitListView = JList(waitListModel)

    private val completeListModel = DefaultListModel<String>()
    private val completeListView = JList(completeListModel)

    private val orderIdMap = mutableMapOf<Int, Int>() // 주문번호 매핑

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(700, 500)
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        setupUI()
        updateOrderLists()

        isVisible = true
    }

    private fun setupUI() {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        panel.add(JLabel("대기 주문 목록"))
        panel.add(JScrollPane(waitListView))

        val completeOrderButton = JButton("선택한 주문 완료")
        completeOrderButton.addActionListener {
            completeSelectedOrder()
        }
        panel.add(completeOrderButton)

        panel.add(JLabel("완료된 주문 목록"))
        panel.add(JScrollPane(completeListView))

        val refreshButton = JButton("새로고침")
        refreshButton.addActionListener {
            updateOrderLists()
        }
        panel.add(refreshButton)

        add(panel)
    }

    private fun completeSelectedOrder() {
        val selectedIndex = waitListView.selectedIndex
        if (selectedIndex != -1) {
            val orderId = orderIdMap[selectedIndex] // 선택한 주문의 ID 가져오기
            if (orderId != null) {
                OrderController.completeOrder(orderId)
                updateOrderLists()
            }
        } else {
            JOptionPane.showMessageDialog(this, "완료할 주문을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE)
        }
    }

    private fun updateOrderLists() {
        waitListModel.clear()
        orderIdMap.clear()
        OrderController.waitList.forEachIndexed { index, order ->
            orderIdMap[index] = order.orderId
            waitListModel.addElement(formatOrderDetails("주문번호 ${order.orderId}", order))
        }

        completeListModel.clear()
        OrderController.completeList.forEachIndexed { index, order ->
            completeListModel.addElement(formatOrderDetails("완료 주문번호 ${order.orderId}", order))
        }

        waitListView.setModel(waitListModel) // 최신화
        completeListView.setModel(completeListModel) // 최신화
    }

    private fun formatOrderDetails(prefix: String, order: OrderData): String {
        val productDetails = order.cart.entries.joinToString("<br>") {
            "&nbsp;&nbsp;- ${it.key} | W ${it.value.first} x ${it.value.second} = W ${it.value.first * it.value.second}"
        }

        return """
        <html>
        <b>$prefix</b><br>
        <b>요청사항:</b> ${order.message}<br>
        <b>상품 목록:</b><br>
        <ul>$productDetails</ul>
        <b>총 금액:</b> W ${order.totalPrice}
        </html>
    """.trimIndent()
    }

}
