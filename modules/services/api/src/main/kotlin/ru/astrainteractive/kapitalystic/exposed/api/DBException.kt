package ru.astrainteractive.kapitalystic.exposed.api

sealed class DBException : Throwable() {
    /**
     * User is not a member of organization
     */
    object NotOrganizationMember : DBException()

    /**
     * User already in organization
     */
    object AlreadyInOrganization : DBException()

    /**
     * User is not organization owner
     */
    object NotOrganizationOwner : DBException()

    /**
     * User already invited to current organization
     */
    object AlreadyInvited : DBException()

    /**
     * User not invited to organization of which he is trying to accept invitation
     */
    object NotInvited : DBException()

    /**
     * Unresolved exception has happened
     */
    object UnexpectedException : DBException()
}
