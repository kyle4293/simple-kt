package org.example

import org.example.view.OrderView
import org.example.view.AdminView
import javax.swing.*

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("SHAKESHACK 시스템")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(400, 200)
        frame.layout = BoxLayout(frame.contentPane, BoxLayout.Y_AXIS)

        val label = JLabel("원하는 기능을 선택하세요")
        frame.add(label)

        val customerButton = JButton("고객 주문")
        customerButton.addActionListener {
            OrderView()
        }
        frame.add(customerButton)

        val adminButton = JButton("관리자 모드")
        adminButton.addActionListener {
            AdminView()
        }
        frame.add(adminButton)

        frame.isVisible = true
    }
}
