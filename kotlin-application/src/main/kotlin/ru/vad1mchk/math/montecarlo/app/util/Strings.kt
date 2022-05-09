package ru.vad1mchk.math.montecarlo.app.util

import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ChangeListener
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.Callable

object Strings {
    const val bundleName = "strings"
    val supportedLocales = listOf<Locale>(Locale.US, Locale("ru", "RU"), Locale.CHINA)

    fun defaultLocale(): Locale {
        return if (Locale.getDefault() in supportedLocales) Locale.getDefault() else Locale.US
    }

    val localeProperty = SimpleObjectProperty<Locale>(defaultLocale()).also {
        it.addListener(ChangeListener { observable, oldValue, newValue ->
            Locale.setDefault(newValue)
        })
    }

    fun getLocale(): Locale {
        return localeProperty.get()
    }

    fun setLocale(locale: Locale) {
        localeProperty.set(locale)
        Locale.setDefault(locale)
    }

    operator fun get(key: String, vararg args: Any): String {
        return MessageFormat.format(ResourceBundle.getBundle(bundleName, getLocale()).getString(key), *args)
    }

    fun createBinding(key: String, vararg args: Any): StringBinding {
        return Bindings.createStringBinding({ get(key, *args) }, localeProperty)
    }

    fun createBinding(func: Callable<String>): StringBinding {
        return Bindings.createStringBinding(func, localeProperty)
    }
}