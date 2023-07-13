package ru.astrainteractive.kapitalystic.exposed.api

sealed class DBException : Throwable() {
    /**
     * User is not a member of organization
     */
    data object NotOrganizationMember : DBException()

    /**
     * User already in organization
     */
    data object AlreadyInOrganization : DBException()

    /**
     * User is not organization owner
     */
    data object NotOrganizationOwner : DBException()

    /**
     * User already invited to current organization
     */
    data object AlreadyInvited : DBException()

    /**
     * User not invited to organization of which he is trying to accept invitation
     */
    data object NotInvited : DBException()

    /**
     * Unresolved exception has happened
     */
    data object UnexpectedException : DBException()
    class NotEnoughMoney(val required: Number) : DBException()
}
