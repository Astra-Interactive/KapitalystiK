package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.utils.Permission

sealed class Permissions(override val value: String) : Permission {
    /**
     * Reload plugin
     */
    data object Reload : Permissions("kapitalystic.reload")

    /**
     * List avaliable orgs
     */
    data object List : Permissions("kapitalystic.orgs.list")

    /**
     * Show org info
     */
    data object OrgInfo : Permissions("kapitalystic.orgs.info")
    sealed class Management(value: String) : Permissions(value) {
        /**
         * Create your org
         */
        data object Create : Management("kapitalystic.create")

        /**
         * Disband your org
         */
        data object Disband : Management("kapitalystic.disband")

        /**
         * Rename your org
         */
        data object Rename : Management("kapitalystic.rename")

        /**
         * Set org BIO
         */
        data object Bio : Management("kapitalystic.bio")
        sealed class Membership(value: String) : Management(value) {
            /**
             * Invite player into your org
             */
            data object Invite : Membership("kapitalystic.rename")

            /**
             * Accept invite into org
             */
            data object AcceptInvite : Membership("kapitalystic.accept")

            /**
             * Kick org member
             */
            data object KickMember : Membership("kapitalystic.kick")

            /**
             * Transfer org ownership
             */
            data object TransferOwnership : Membership("kapitalystic.transfer")
        }

        sealed class Rules(value: String) : Management(value) {
            /**
             * List org rule
             */
            data object List : Rules("kapitalystic.rules.list")

            /**
             * Remove org rule
             */
            data object Remove : Rules("kapitalystic.rules.remove")

            /**
             * Add org rule
             */
            data object Add : Rules("kapitalystic.rules.add")
        }
    }

    sealed class Warp(value: String) : Permissions(value) {
        /**
         * Set org spawn
         */
        data object Set : Warp("kapitalystic.warp.set")

        /**
         * Teleport to org spawn
         */
        data object Teleport : Warp("kapitalystic.warp.tp")

        /**
         * Set org spawn visibility
         */
        data object Visibility : Warp("kapitalystic.warp.visibility")
    }
}
