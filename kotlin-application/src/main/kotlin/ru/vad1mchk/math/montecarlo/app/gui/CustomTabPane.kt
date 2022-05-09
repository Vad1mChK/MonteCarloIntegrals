package ru.vad1mchk.math.montecarlo.app.gui

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

class CustomTabPane : TabPane() {
    private val tabsToAdd = arrayOf<Tab>(MainTab(), SettingsTab(), AboutTab())

    init {
        for (tab in tabsToAdd) {
            tab.isClosable = false
            tabs.add(tab)
        }
    }
}