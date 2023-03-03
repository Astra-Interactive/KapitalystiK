import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.kapitalystik.commands.reload
import ru.astrainteractive.kapitalystik.plugin.Translation

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager(
    translationModule: Dependency<Translation>
) {
    private val translation by translationModule

    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        reload(
            translationModule = translationModule
        )
    }
}
