package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.utils.Permission

sealed class Permissions(override val value: String) : Permission {
    /**
     * Reload plugin
     */
    object Reload : Permissions("kapitalystic.reload")

    /**
     * List avaliable orgs
     */
    object List : Permissions("kapitalystic.orgs.list")

    /**
     * Show org info
     */
    object OrgInfo : Permissions("kapitalystic.orgs.info")
    sealed class Management(value: String) : Permissions(value) {
        /**
         * Create your org
         */
        object Create : Management("kapitalystic.create")

        /**
         * Disband your org
         */
        object Disband : Management("kapitalystic.disband")

        /**
         * Rename your org
         */
        object Rename : Management("kapitalystic.rename")

        /**
         * Set org BIO
         */
        object Bio : Management("kapitalystic.bio")
        sealed class Membership(value: String) : Management(value) {
            /**
             * Invite player into your org
             */
            object Invite : Membership("kapitalystic.rename")

            /**
             * Accept invite into org
             */
            object AcceptInvite : Membership("kapitalystic.accept")

            /**
             * Kick org member
             */
            object KickMember : Membership("kapitalystic.kick")

            /**
             * Transfer org ownership
             */
            object TransferOwnership : Membership("kapitalystic.transfer")
        }

        sealed class Rules(value: String) : Management(value) {
            /**
             * List org rule
             */
            object List : Rules("kapitalystic.rules.list")

            /**
             * Remove org rule
             */
            object Remove : Rules("kapitalystic.rules.remove")

            /**
             * Add org rule
             */
            object Add : Rules("kapitalystic.rules.add")
        }
    }

    sealed class Spawn(value: String) : Permissions(value) {
        /**
         * Set org spawn
         */
        object Set : Spawn("kapitalystic.spawn.set")

        /**
         * Teleport to org spawn
         */
        object Teleport : Spawn("kapitalystic.spawn.tp")

        /**
         * Set org spawn visibility
         */
        object Visibility : Spawn("kapitalystic.spawn.visibility")
    }
}
