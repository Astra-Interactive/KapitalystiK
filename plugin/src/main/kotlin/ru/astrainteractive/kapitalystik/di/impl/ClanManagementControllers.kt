package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.kapitalystic.features.controllers.AcceptInviteController
import ru.astrainteractive.kapitalystic.features.controllers.CreateClanController
import ru.astrainteractive.kapitalystic.features.controllers.DisbandController
import ru.astrainteractive.kapitalystic.features.controllers.GetPagedOrgsController
import ru.astrainteractive.kapitalystic.features.controllers.InviteController
import ru.astrainteractive.kapitalystic.features.controllers.KickController
import ru.astrainteractive.kapitalystic.features.controllers.MakeWarpPublicController
import ru.astrainteractive.kapitalystic.features.controllers.RenameController
import ru.astrainteractive.kapitalystic.features.controllers.SetDescriptionController
import ru.astrainteractive.kapitalystic.features.controllers.SetStatusController
import ru.astrainteractive.kapitalystic.features.controllers.SetWarpController
import ru.astrainteractive.kapitalystic.features.controllers.TransferController
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule
import ru.astrainteractive.klibs.kdi.Factory

class ClanManagementControllers(module: ClanManagementControllerModule) {
    val acceptInviteControllerFactory = Factory {
        AcceptInviteController(module)
    }

    val createClanController = Factory {
        CreateClanController(module)
    }
    val disbandController = Factory {
        DisbandController(module)
    }
    val getPagedOrgsController = Factory {
        GetPagedOrgsController(module)
    }
    val inviteController = Factory {
        InviteController(module)
    }
    val kickController = Factory {
        KickController(module)
    }
    val makeWarpPublicController = Factory {
        MakeWarpPublicController(module)
    }
    val renameController = Factory {
        RenameController(module)
    }
    val setDescriptionController = Factory {
        SetDescriptionController(module)
    }
    val setStatusController = Factory {
        SetStatusController(module)
    }
    val setWarpController = Factory {
        SetWarpController(module)
    }
    val transferController = Factory {
        TransferController(module)
    }
}
