package ru.astrainteractive.kapitalystic.exposed.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.exposed.api.utils.ORMTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This class will test creation of organizations
 */
class MembershipTest : ORMTest() {


    @Test
    fun `Invite user to organization`(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        val newMember = randomUserDTO
        api.invite(newMember, creator).getOrThrow()
        val isInvited = commonApi.isUserInvited(newMember, orgDTO.id)
        assertEquals(true, isInvited)

        assertThrows<DBException> {
            api.invite(newMember, creator).getOrThrow()
        }
    }

    @Test
    fun `Accept invitation to organization`(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        val newMember = randomUserDTO
        api.invite(newMember, creator).getOrThrow()
        api.acceptInvitation(newMember, orgDTO.tag).getOrThrow()
        val membersAmount = api.fetchOrganization(orgDTO.tag).getOrThrow().members.size
        assertEquals(2, membersAmount)
    }

    @Test
    fun `Kick member from org`(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        val newMember = randomUserDTO
        api.invite(newMember, creator).getOrThrow()
        api.acceptInvitation(newMember, orgDTO.tag).getOrThrow()
        api.kickMember(newMember, creator).getOrThrow()
        val membersAmount = api.fetchOrganization(orgDTO.tag).getOrThrow().members.size
        assertEquals(1, membersAmount)
    }

    @Test
    fun `Transfer org ownership`(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        // To random user
        assertThrows<DBException> {
            api.transferOwnership(randomUserDTO, creator).getOrThrow()
        }
        val newMember = randomUserDTO
        api.invite(newMember, creator).getOrThrow()
        api.acceptInvitation(newMember, orgDTO.tag).getOrThrow()
        // To member
        api.transferOwnership(newMember, creator).getOrThrow()
        api.fetchOrganization(orgDTO.tag).getOrThrow().also {
            assertEquals(it.leader.minecraftUUID, newMember.minecraftUUID)
        }
    }
}