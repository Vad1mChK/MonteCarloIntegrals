package ru.vad1mchk.math.montecarlo.app.gui

import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Tab
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import ru.vad1mchk.math.montecarlo.app.util.Configuration
import ru.vad1mchk.math.montecarlo.app.util.Strings
import java.awt.Desktop
import java.net.URI

class AboutTab: Tab() {
    init {
        textProperty().bind(Strings.createBinding("labelAbout"))
        val vBox = VBox()
        content = vBox
        vBox.apply {
            alignment = Pos.CENTER
            padding = Insets(20.0,80.0, 20.0, 80.0)
            spacing = 20.0
            children.add(Text().also {
                it.textProperty().bind(Strings.createBinding("name"));
                it.textAlignment = TextAlignment.CENTER
                it.wrappingWidthProperty().bind(Configuration.width.add(-padding.left-padding.right))
            })
            children.add(ImageView(Configuration.ICON).also {
                it.fitHeight=80.0
                it.fitWidth=80.0
            })
            children.add(Text().also {
                it.textProperty().bind(Strings.createBinding("creators"))
                it.textAlignment = TextAlignment.JUSTIFY
                it.wrappingWidthProperty().bind(Configuration.width.add(-padding.left-padding.right))
            })
            children.add(Button().also {
                it.textProperty().bind(Strings.createBinding("linkGitHub"))
                it.onMouseClicked = EventHandler {
                    Desktop.getDesktop().browse(URI.create("https://github.com/Vad1mChK/MonteCarloIntegrals"))
                }
            })
        }
    }
}