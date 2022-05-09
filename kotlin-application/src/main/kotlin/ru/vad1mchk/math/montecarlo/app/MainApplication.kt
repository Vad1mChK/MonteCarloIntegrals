package ru.vad1mchk.math.montecarlo.app

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import ru.vad1mchk.math.montecarlo.app.gui.RootBox
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.app.util.Strings

class MainApplication : Application() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MainApplication::class.java, *args)
        }
    }

    override fun start(primaryStage: Stage?) {
        primaryStage?.apply {
            scene = Scene(RootBox())
            icons.add(Configuration.ICON)
            titleProperty().bind(Strings.createBinding("title"))
            minWidth = Configuration.MIN_WIDTH
            Configuration.width.bind(widthProperty())
            minHeight = Configuration.MIN_HEIGHT
            Configuration.height.bind(heightProperty())
            show()
        }
    }
}