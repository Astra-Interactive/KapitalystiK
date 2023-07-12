package ru.astrainteractive.kapitalystic.features.failuremessenger

import ru.astrainteractive.kapitalystic.exposed.api.DBException
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystic.features.failuremessenger.di.FailureMessengerModule

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
