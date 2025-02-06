package org.example.view

import org.example.controller.MenuController
import javax.swing.*

class MenuView : JFrame("SHAKESHACK 관리프로그램") {
    private val menuListModel = DefaultListModel<String>()
    private val menuListView = JList(menuListModel)
    private val orderListModel = DefaultListModel<String>()
    private val orderListView = JList(orderListModel)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 400)
        layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        updateMenuList()
        setupUI()

        isVisible = true
    }

    private fun setupUI() {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        panel.add(JLabel("메뉴 목록"))
        panel.add(JScrollPane(menuListView))

        val addMenuButton = JButton("메뉴 추가")
        addMenuButton.addActionListener {
            val name = JOptionPane.showInputDialog("메뉴 이름 입력:")
            val desc = JOptionPane.showInputDialog("메뉴 설명 입력:")
            if (name != null && desc != null) {
                MenuController.addMenu(name, desc)
                updateMenuList()
            }
        }

        val deleteMenuButton = JButton("메뉴 삭제")
        deleteMenuButton.addActionListener {
            val selectedIndex = menuListView.selectedIndex
            if (selectedIndex != -1) {
                MenuController.deleteMenu(selectedIndex)
                updateMenuList()
            }
        }

        val orderButton = JButton("주문 관리")
        orderButton.addActionListener {
            OrderView()
        }

        panel.add(addMenuButton)
        panel.add(deleteMenuButton)
        panel.add(orderButton)

        add(panel)
    }

    private fun updateMenuList() {
        menuListModel.clear()
        MenuController.menuList.forEach { menuListModel.addElement("${it.name} - ${it.description}") }
    }
}
