package ru.vad1mchk.math.montecarlo.app.gui

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.control.Tab
import javafx.scene.layout.GridPane
import javafx.util.StringConverter
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.app.util.Strings
import java.util.*

class SettingsTab : Tab() {
    init {
        textProperty().bind(Strings.createBinding("labelSettings"))
        val grid = GridPane().also {
            it.padding = Insets(20.0)
            it.hgap = 20.0
            it.vgap = 20.0
        }
        content = grid

        val choiceBoxOfLocales = ChoiceBox<Locale>()
        choiceBoxOfLocales.apply {
            converter = object : StringConverter<Locale>() {
                override fun toString(locale: Locale?): String {
                    return locale?.displayName ?: ""
                }

                override fun fromString(string: String?): Locale {
                    return Locale.ROOT
                }
            }
            items.addAll(Strings.supportedLocales)
            value = Strings.getLocale()
            selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                Strings.setLocale(newValue)
            }
            grid.add(this, 1, 0)
            grid.add(Label().also {
                it.labelFor = this
                it.textProperty().bind(Strings.createBinding("settingLanguage"))
            }, 0, 0)
        }

        val spinnerOfDotsToDrop = Spinner<Int>(
            Configuration.MIN_DOTS_TO_SHOW,
            Configuration.MAX_DOTS_TO_SHOW,
            Configuration.MAX_DOTS_TO_SHOW
        )
        spinnerOfDotsToDrop.apply {
            isEditable = true
            valueProperty().addListener { _, _, newValue ->
                Configuration.dotsToShowProperty.set(newValue)
            }
            focusedProperty().addListener { _, _, newValue ->
                if (!newValue) Configuration.dotsToShowProperty.set(value)
            }
            grid.add(this, 1, 1)
            grid.add(Label().also {
                it.labelFor = this
                it.textProperty().bind(Strings.createBinding("settingDotsToShow"))
            }, 0, 1)
        }
    }
}