package ru.vad1mchk.math.montecarlo.app.util

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.scene.image.Image

/** Default configuration of certain application properties. */
object Configuration {
    const val MIN_WIDTH = 960.0
    const val MIN_HEIGHT = 540.0
    val width = SimpleDoubleProperty(MIN_WIDTH)
    val height = SimpleDoubleProperty(MIN_HEIGHT)
    const val MIN_DOTS_TO_SHOW = 0
    const val MAX_DOTS_TO_SHOW = 100
    const val MIN_DOTS_TO_DROP = 1
    const val MAX_DOTS_TO_DROP = 1_048_576
    val dotsToShowProperty = SimpleIntegerProperty(MAX_DOTS_TO_SHOW)
    val shownDotsProperty = SimpleListProperty<Pair<Double, Double>>(FXCollections.observableArrayList())
    val ICON = Image(javaClass.getResourceAsStream("/retro_sun.png"))
}