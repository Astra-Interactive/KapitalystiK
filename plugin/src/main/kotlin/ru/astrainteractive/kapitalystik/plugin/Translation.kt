package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.SpigotFileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation

/**
 * All translation stored here
 */
class Translation : BaseTranslation(), SharedTranslation {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: SpigotFileManager = SpigotFileManager("translations.yml")
    override val prefix = "#18dbd1[KapitalystiK]".HEX()

    private val positiveColor = "#42f596"
    private val negativeColor = "#db2c18"

    override val wrongUsage: String = translationValue(
        path = "general.wrong_usage",
        default = "${negativeColor}Неверное использование"
    )

    override val reload = translationValue(
        path = "general.reload",
        default = "${positiveColor}Перезагрузка плагина"
    )
    override val reloadComplete = translationValue(
        path = "general.reload_complete",
        default = "${positiveColor}Перезагрузка успешно завершена"
    )
    override val noPermission = translationValue(
        path = "general.no_permission",
        default = "${negativeColor}У вас нет прав!"
    )
    override val disbanded: String = translationValue(
        path = "org.disbanded",
        default = "${negativeColor}Организация распущена!"
    )
    override val bioChanged: String = translationValue(
        path = "org.bio_changed",
        default = "${positiveColor}BIO изменено!"
    )
    override val ruleAdded: String = translationValue(
        path = "org.rules.added",
        default = "${positiveColor}Правило добавлено!"
    )
    override val spawnSet: String = translationValue(
        path = "org.spawn_set",
        default = "${positiveColor}Спавн установлен!"
    )
    override val alreadyInOrganization: String = translationValue(
        path = "org.error.already_in_org",
        default = "${negativeColor}Ошибка: Уже в организации!"
    )
    override val alreadyInvited: String = translationValue(
        path = "org.error.already_invited",
        default = "${negativeColor}Ошибка: Уже приглашен!"
    )
    override val notInvited: String = translationValue(
        path = "org.error.not_invited",
        default = "${negativeColor}Ошибка: Не приглашен!"
    )
    override val notPlayer: String = translationValue(
        path = "org.error.not_player",
        default = "${negativeColor}Ошибка: Не игрок!"
    )
    override val notOrganizationMember: String = translationValue(
        path = "org.error.not_player",
        default = "${negativeColor}Ошибка: Не в организации!"
    )
    override val notOrganizationOwner: String = translationValue(
        path = "org.error.not_owner",
        default = "${negativeColor}Ошибка: Не владелец организации!"
    )
    override val unexcpectedException: String = translationValue(
        path = "org.error.unexpected",
        default = "${negativeColor}Ошибка: Неизвестная ошибка!"
    )

    private val clanCreated: String = translationValue(
        path = "org.command.created",
        default = "${positiveColor}Клан %name% %tag% был создан!"
    )

    override fun clanCreated(name: String, tag: String): String = clanCreated
        .replace("%name%", name)
        .replace("%tag%", tag)

    private val notEnoughMoney: String = translationValue(
        path = "org.error.not_enough_money",
        default = "${negativeColor}Недостаточно средств. Нужно %amount%"
    )

    override fun notEnoughMoney(needAmount: Int): String = notEnoughMoney.replace(
        oldValue = "%amount%",
        newValue = "$needAmount"
    )

    private val clanRenamed: String = translationValue(
        path = "org.command.renamed",
        default = "${positiveColor}Новое название: %name%"
    )

    override fun clanRenamed(newName: String): String = clanRenamed.replace(
        oldValue = "%name%",
        newValue = newName
    )

    private val userInvited: String = translationValue(
        path = "org.command.invited",
        default = "${positiveColor}Пользователь %name% приглашен"
    )

    override fun userInvited(userDTO: UserDTO): String = userInvited.replace(
        oldValue = "%name%",
        newValue = userDTO.minecraftName
    )

    private val userKicked: String = translationValue(
        path = "org.command.kicked",
        default = "${negativeColor}Пользователь %name% исключен"
    )

    override fun userKicked(userDTO: UserDTO): String = userKicked.replace(
        oldValue = "%name%",
        newValue = userDTO.minecraftName
    )

    private val ownershipTransferred: String = translationValue(
        path = "org.command.transfered",
        default = "${negativeColor}Пользователь %name% новый владелец вашей организации"
    )

    override fun ownershipTransferred(newOwner: UserDTO): String = ownershipTransferred.replace(
        oldValue = "%name%",
        newValue = newOwner.minecraftName
    )

    private val joinedToClan: String = translationValue(
        path = "org.command.joined",
        default = "${negativeColor}Вы присоединились к клану %tag%"
    )

    override fun joinedToClan(clanTAG: String): String = joinedToClan.replace(
        oldValue = "%tag%",
        newValue = clanTAG
    )

    private val spawnPublic: String = translationValue(
        path = "org.command.spawnpublic",
        default = "${negativeColor}Спавн вашей организации теперь публичный: %value%"
    )

    override fun spawnPublic(value: Boolean): String = spawnPublic.replace(
        oldValue = "%value%",
        newValue = "$value"
    )
}
