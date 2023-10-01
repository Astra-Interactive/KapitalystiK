package ru.astrainteractive.kapitalystik.features.failuremessenger

import ru.astrainteractive.kapitalystik.api.exception.DBApiException
import ru.astrainteractive.kapitalystik.features.failuremessenger.di.FailureMessengerContainer

internal class FailureMessengerComponent(
    container: FailureMessengerContainer
) : FailureMessenger, FailureMessengerContainer by container {
    override fun asTranslationMessage(e: DBApiException): String = when (e) {
        is DBApiException.AlreadyInOrganization -> translation.alreadyInOrganization
        is DBApiException.AlreadyInvited -> translation.alreadyInvited
        is DBApiException.NotInvited -> translation.notInvited
        is DBApiException.NotOrganizationMember -> translation.notOrganizationMember
        is DBApiException.NotOrganizationOwner -> translation.notOrganizationOwner
        is DBApiException.UnexpectedException -> translation.unexcpectedException
        is DBApiException.NotEnoughMoney -> translation.notEnoughMoney(e.required.toInt())
        DBApiException.OrgAlreadyExists -> translation.wrongUsage // todo
    }

    override fun asTranslationMessage(e: Throwable): String {
        return (e as? DBApiException)?.let(::asTranslationMessage) ?: translation.unexcpectedException
    }
}
