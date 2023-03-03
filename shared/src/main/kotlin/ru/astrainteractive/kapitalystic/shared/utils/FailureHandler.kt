package ru.astrainteractive.kapitalystic.shared.utils

import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation

class FailureHandler(
    translation: Dependency<SharedTranslation>
) {
    private val translation by translation

    /**
     * @return translation message based on [DBException]
     */
    fun asTranslationMessage(e: DBException): String = when (e) {
        is DBException.AlreadyInOrganization -> translation.alreadyInOrganization
        is DBException.AlreadyInvited -> translation.alreadyInvited
        is DBException.NotInvited -> translation.notInvited
        is DBException.NotOrganizationMember -> translation.notOrganizationMember
        is DBException.NotOrganizationOwner -> translation.notOrganizationOwner
        is DBException.UnexpectedException -> translation.unexcpectedException
    }

    /**
     * @return translation message based on [DBException]
     * or [SharedTranslation.unexcpectedException] if [e] is not [DBException]
     */
    fun asTranslationMessage(e: Throwable): String {
        return (e as? DBException)?.let(::asTranslationMessage) ?: translation.unexcpectedException
    }
}
