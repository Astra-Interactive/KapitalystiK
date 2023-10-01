package ru.astrainteractive.kapitalystik.features.failuremessenger

import ru.astrainteractive.kapitalystik.exposed.api.DBException
import ru.astrainteractive.kapitalystik.features.core.Translation
import ru.astrainteractive.kapitalystik.features.failuremessenger.di.FailureMessengerModule

/**
 * This interface will help to handle [DBException]
 */
interface FailureMessenger {
    /**
     * @return translation message based on [DBException]
     */
    fun asTranslationMessage(e: DBException): String

    /**
     * @return translation message based on [DBException]
     * or [Translation.unexcpectedException] if [e] is not [DBException]
     */
    fun asTranslationMessage(e: Throwable): String

    companion object {
        fun FailureMessenger(module: FailureMessengerModule): FailureMessenger = FailureMessengerComponent(module)
    }
}
