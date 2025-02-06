package org.example.view

import org.example.controller.MenuController
import javax.swing.*

class MenuManagementView : JFrame("메뉴 및 상품 관리") {
    private val menuListModel = DefaultListModel<String>()
    private val menuListView = JList(menuListModel)

    init {
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setSize(500, 400)
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        setupUI()
        updateMenuList()

        isVisible = true
    }

    private fun setupUI() {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        panel.add(JLabel("메뉴 목록"))
        panel.add(JScrollPane(menuListView))

        val addMenuButton = JButton("메뉴 추가")
        addMenuButton.addActionListener {
            addMenu()
        }

        val deleteMenuButton = JButton("메뉴 삭제")
        deleteMenuButton.addActionListener {
            deleteMenu()
        }

        val manageProductsButton = JButton("상품 관리")
        manageProductsButton.addActionListener {
            manageProducts()
        }

        panel.add(addMenuButton)
        panel.add(deleteMenuButton)
        panel.add(manageProductsButton)

        add(panel)
    }

    private fun addMenu() {
        val name = JOptionPane.showInputDialog("메뉴 이름 입력:")
        val description = JOptionPane.showInputDialog("메뉴 설명 입력:")
        if (name != null && description != null) {
            MenuController.addMenu(name, description)
            updateMenuList()
        }
    }

    private fun deleteMenu() {
        val selectedIndex = menuListView.selectedIndex
        if (selectedIndex != -1) {
            MenuController.deleteMenu(selectedIndex)
            updateMenuList()
        }
    }

    private fun manageProducts() {
        val selectedIndex = menuListView.selectedIndex
        if (selectedIndex != -1) {
            ProductManagementView(selectedIndex)
        }
    }

    private fun updateMenuList() {
        menuListModel.clear()
        MenuController.menuList.forEach { menuListModel.addElement("${it.name} - ${it.description}") }
    }
}
