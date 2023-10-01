package ru.astrainteractive.kapitalystik.api.exception

sealed class DBApiException : Throwable() {
    data object OrgAlreadyExists : DBApiException()

    /**
     * User is not a member of organization
     */
    data object NotOrganizationMember : DBApiException()

    /**
     * User already in organization
     */
    data object AlreadyInOrganization : DBApiException()

    /**
     * User is not organization owner
     */
    data object NotOrganizationOwner : DBApiException()

    /**
     * User already invited to current organization
     */
    data object AlreadyInvited : DBApiException()

    /**
     * User not invited to organization of which he is trying to accept invitation
     */
    data object NotInvited : DBApiException()

    /**
     * Unresolved exception has happened
     */
    data object UnexpectedException : DBApiException()

    class NotEnoughMoney(val required: Number) : DBApiException()
}
