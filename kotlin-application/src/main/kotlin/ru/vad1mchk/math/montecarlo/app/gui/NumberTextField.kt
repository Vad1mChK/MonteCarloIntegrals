package ru.vad1mchk.math.montecarlo.app.gui

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.TextField
import ru.vad1mchk.math.montecarlo.app.util.Strings
import java.text.NumberFormat
import java.text.ParseException

class NumberTextField : TextField() {
    val numberFormatProperty = SimpleObjectProperty<NumberFormat>().also {
        it.set(NumberFormat.getInstance(Strings.defaultLocale()))
        Strings.localeProperty.addListener { _, _, newValue ->
            val newFormat = NumberFormat.getInstance(newValue)
            text = try {
                val doubleValue = it.get().parse(text).toDouble()
                it.set(newFormat)
                it.get().format(doubleValue)
            } catch (e: ParseException) {
                it.set(newFormat)
                it.get().format(0.0)
            }
        }
    }
    private val numberProperty = SimpleDoubleProperty(0.0)

    init {
        setOnAction {
            parseAndFormatInput()
        }

        focusedProperty().addListener { _, _, newValue ->
            if (!newValue) {
                parseAndFormatInput()
            }
        }

        numberProperty.addListener { _, _, newValue ->
            text = numberFormatProperty.get().format(newValue)
        }
    }

    fun getNumber(): Double {
        return numberProperty.get()
    }

    fun setNumber(number: Double) {
        numberProperty.set(number)
    }

    fun numberProperty(): SimpleDoubleProperty {
        return numberProperty
    }

    private fun parseAndFormatInput() {
        text = try {
            if (text.isEmpty()) numberFormatProperty.get().format(0.0)
            val parsedNumber = numberFormatProperty.get().parse(text).toDouble()
            setNumber(parsedNumber)
            numberFormatProperty.get().format(parsedNumber)
        } catch (e: ParseException) {
            numberFormatProperty.get().format(getNumber())
        }
    }
}