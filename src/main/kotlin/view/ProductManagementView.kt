package org.example.view

import org.example.controller.MenuController
import javax.swing.*

class ProductManagementView(private val menuIndex: Int) : JFrame("상품 관리") {
    private val productListModel = DefaultListModel<String>()
    private val productListView = JList(productListModel)

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(500, 400)
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        setupUI()
        updateProductList()

        isVisible = true
    }

    private fun setupUI() {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        panel.add(JLabel("상품 목록"))
        panel.add(JScrollPane(productListView))

        val addProductButton = JButton("상품 추가")
        addProductButton.addActionListener {
            addProduct()
        }

        val deleteProductButton = JButton("상품 삭제")
        deleteProductButton.addActionListener {
            deleteProduct()
        }

        panel.add(addProductButton)
        panel.add(deleteProductButton)

        add(panel)
    }

    private fun addProduct() {
        val name = JOptionPane.showInputDialog("상품 이름 입력:")
        val price = JOptionPane.showInputDialog("상품 가격 입력:")?.toDoubleOrNull()
        val description = JOptionPane.showInputDialog("상품 설명 입력:")
        if (name != null && price != null && description != null) {
            MenuController.addProduct(menuIndex, name, price, description)
            updateProductList()
        }
    }

    private fun deleteProduct() {
        val selectedIndex = productListView.selectedIndex
        if (selectedIndex != -1) {
            MenuController.deleteProduct(menuIndex, selectedIndex)
            updateProductList()
        }
    }

    private fun updateProductList() {
        productListModel.clear()
        MenuController.menuList[menuIndex].products.forEach {
            productListModel.addElement("${it.name} - W ${it.price} - ${it.description}")
        }
    }
}
