package ru.astrainteractive.kapitalystik.features.failuremessenger

import ru.astrainteractive.kapitalystik.api.exception.DBApiException
import ru.astrainteractive.kapitalystik.core.Translation
import ru.astrainteractive.kapitalystik.features.failuremessenger.di.FailureMessengerContainer

/**
 * This interface will help to handle [DBApiException]
 */
interface FailureMessenger {
    /**
     * @return translation message based on [DBApiException]
     */
    fun asTranslationMessage(e: DBApiException): String

    /**
     * @return translation message based on [DBApiException]
     * or [Translation.unexcpectedException] if [e] is not [DBApiException]
     */
    fun asTranslationMessage(e: Throwable): String

    companion object {
        fun FailureMessenger(module: FailureMessengerContainer): FailureMessenger = FailureMessengerComponent(module)
    }
}
