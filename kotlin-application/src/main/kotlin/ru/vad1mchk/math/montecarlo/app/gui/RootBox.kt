package ru.vad1mchk.math.montecarlo.app.gui

import javafx.scene.layout.VBox

class RootBox : VBox() {
    init {
        children.addAll(
            CustomTabPane()
        )
    }
}