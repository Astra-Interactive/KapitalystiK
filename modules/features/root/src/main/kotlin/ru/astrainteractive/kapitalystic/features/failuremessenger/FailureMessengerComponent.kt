package ru.astrainteractive.kapitalystic.features.failuremessenger

import ru.astrainteractive.kapitalystic.exposed.api.DBException
import ru.astrainteractive.kapitalystic.features.failuremessenger.di.FailureMessengerModule

internal class FailureMessengerComponent(
    module: FailureMessengerModule
) : FailureMessenger, FailureMessengerModule by module {
    override fun asTranslationMessage(e: DBException): String = when (e) {
        is DBException.AlreadyInOrganization -> translation.alreadyInOrganization
        is DBException.AlreadyInvited -> translation.alreadyInvited
        is DBException.NotInvited -> translation.notInvited
        is DBException.NotOrganizationMember -> translation.notOrganizationMember
        is DBException.NotOrganizationOwner -> translation.notOrganizationOwner
        is DBException.UnexpectedException -> translation.unexcpectedException
        is DBException.NotEnoughMoney -> translation.notEnoughMoney(e.required.toInt())
    }

    override fun asTranslationMessage(e: Throwable): String {
        return (e as? DBException)?.let(::asTranslationMessage) ?: translation.unexcpectedException
    }
}
