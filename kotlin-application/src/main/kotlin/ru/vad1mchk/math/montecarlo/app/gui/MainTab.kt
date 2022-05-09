package ru.vad1mchk.math.montecarlo.app.gui

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import ru.vad1mchk.math.montecarlo.app.integrals.GeometricalMonteCarloIntegral
import ru.vad1mchk.math.montecarlo.app.integrals.MonteCarloIntegral
import ru.vad1mchk.math.montecarlo.app.integrals.OrdinaryMonteCarloIntegral
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.app.util.Strings
import ru.vad1mchk.math.montecarlo.core.exceptions.IntegralException
import ru.vad1mchk.math.montecarlo.core.exceptions.InvalidExpressionException
import ru.vad1mchk.math.montecarlo.core.util.ExpressionParser
import ru.vad1mchk.math.montecarlo.core.util.RangeExtensions.step
import java.lang.Double.max
import java.lang.Double.min

class MainTab : Tab() {
    private val leftBorderProperty = SimpleDoubleProperty()
    private val rightBorderProperty = SimpleDoubleProperty()
    private val integrandStringProperty = SimpleStringProperty()
    private val isGeometricalMethodProperty = SimpleBooleanProperty(false).also {
        it.addListener { _, _, newValue ->
            println(newValue)
        }
    }
    private val errorStringProperty = SimpleStringProperty()
    private val answerStringProperty = SimpleStringProperty()
    private val integralValueProperty = SimpleDoubleProperty(0.0)
    val xAxis = NumberAxis("x", 0.0, 10.0, 1.0)
    val yAxis = NumberAxis("y", -2.0, 2.0, 1.0)
    var chart = LineChart(xAxis, yAxis)
    val hBox = HBox()
    init {
        textProperty().bind(Strings.createBinding("labelMain"))
        content = hBox
        val grid = GridPane()
        grid.apply {
            padding = Insets(20.0)
            hgap = 20.0
            vgap = 20.0
            add(Label().also {
                it.textProperty().bind(Strings.createBinding("mainIntegrationBorders"))
                it.textAlignment = TextAlignment.CENTER
            }, 0, 0, 2, 1)
            val leftBorderField = NumberTextField().also {
                leftBorderProperty.bind(it.numberProperty())
            }
            add(Label().also {
                it.labelFor = leftBorderField
                it.textAlignment = TextAlignment.CENTER
                it.textProperty().bind(Strings.createBinding("mainLeft"))
            }, 0, 1)
            add(leftBorderField, 0, 2)
            val rightBorderField = NumberTextField().also {
                rightBorderProperty.bind(it.numberProperty())
            }
            add(Label().also {
                it.labelFor = rightBorderField
                it.textAlignment = TextAlignment.CENTER
                it.textProperty().bind(Strings.createBinding("mainRight"))
            }, 1, 1)
            add(rightBorderField, 1, 2)
            val integrandField = TextField().also {
                integrandStringProperty.bind(it.textProperty())
            }
            add(Label().also {
                it.labelFor = integrandField
                it.textProperty().bind(Strings.createBinding("mainIntegrand"))
                it.wrapTextProperty().set(true)
            }, 0, 3, 2, 1)
            add(integrandField, 0, 4, 2, 1)
            val errorLine = Label().also {
                it.alignment = Pos.BOTTOM_LEFT
                it.textAlignment = TextAlignment.JUSTIFY
                it.textProperty().bind(errorStringProperty)
                it.isVisible = false
                errorStringProperty.addListener { _, _, _ ->
                    it.isVisible = true
                }
            }
            val answerLine = Label().also {
                it.fontProperty().set(Font(20.0))
                it.textProperty().bind(answerStringProperty)
                it.isVisible = false
                integralValueProperty.addListener { _, _, newValue ->
                    it.isVisible = true
                }
            }
            add(Button().also {
                it.textProperty().bind(Strings.createBinding("mainIntegrate"))
                it.onMouseClicked = EventHandler {
                    answerLine.isVisible = false
                    errorLine.isVisible = false
                    integrateAndDrawGraph()
                }
            }, 1, 5, 1, 3)

            val toggleGroup = ToggleGroup()
            val radioButtonOrdinary = RadioButton().also {
                it.textProperty().bind(Strings.createBinding("mainOrdinary"))
                it.wrapTextProperty().set(true)
            }
            val radioButtonGeometrical = RadioButton().also {
                it.textProperty().bind(Strings.createBinding("mainGeometrical"))
                isGeometricalMethodProperty.bind(it.selectedProperty())
                it.wrapTextProperty().set(true)
            }
            toggleGroup.toggles.addAll(radioButtonOrdinary, radioButtonGeometrical)
            toggleGroup.selectToggle(radioButtonOrdinary)
            add(Label().also {
                it.textProperty().bind(Strings.createBinding("mainSelectMethod"))
                it.wrapTextProperty().set(true)
            }, 0, 5)
            add(radioButtonOrdinary, 0, 6)
            add(radioButtonGeometrical, 0, 7)
            add(errorLine, 0, 8, 2, 1)
            add(answerLine, 0, 9,2,1)
        }

        hBox.apply {
            padding = Insets(20.0)
            spacing = 20.0
            alignment = Pos.TOP_RIGHT
            children.addAll(
                chart,
                grid
            )
        }
    }

    private fun integrateAndDrawGraph() {
        Configuration.shownDotsProperty.get().clear()
        chart.data.clear()
        val integrand = try {
            ExpressionParser.parse(integrandStringProperty.get(), "x")
        } catch (e: InvalidExpressionException) {
            errorStringProperty.set("${e.message}")
            return
        }
        val leftBorder = min(leftBorderProperty.get(), rightBorderProperty.get())
        val rightBorder = max(leftBorderProperty.get(), rightBorderProperty.get())
        val integral: MonteCarloIntegral = if (isGeometricalMethodProperty.get()) {
            GeometricalMonteCarloIntegral(leftBorderProperty.get(), rightBorderProperty.get(), integrand)
        } else OrdinaryMonteCarloIntegral(leftBorderProperty.get(), rightBorderProperty.get(), integrand)
        integralValueProperty.set(try {
            integral.integrate()
        } catch (e: IntegralException) {
            errorStringProperty.set("${e.message}")
            return
        })
        answerStringProperty.bind(Strings.createBinding(
            "mainResult", integralValueProperty.get())
        )
        chart = LineChart(
            NumberAxis("x", leftBorder, rightBorder, (rightBorder - leftBorder) / 20),
            NumberAxis("y", integral.minValue, integral.maxValue,
                (integral.maxValue - integral.minValue) / 20)
        ).also {
            it.isLegendVisible = false
        }
        val series = XYChart.Series<Number, Number>().also {
            it.name = integrandStringProperty.get()
            for (variable in leftBorder..rightBorder step (rightBorder - leftBorder) / 1000) {
                it.data.add(XYChart.Data(variable, integrand.setVariable("x", variable).evaluate()))
            }
        }
        chart.data.add(series)
        for (dot in Configuration.shownDotsProperty.get()) {
            chart.data.add(XYChart.Series(FXCollections.observableArrayList(XYChart.Data(dot.first, dot.second))))
        }
        hBox.children.removeFirst()
        hBox.children.add(0,chart)
    }
}