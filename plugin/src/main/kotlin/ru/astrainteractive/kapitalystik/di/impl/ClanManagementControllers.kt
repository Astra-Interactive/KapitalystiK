package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.kapitalystik.features.controller.AcceptInviteController
import ru.astrainteractive.kapitalystik.features.controller.CreateClanController
import ru.astrainteractive.kapitalystik.features.controller.DisbandController
import ru.astrainteractive.kapitalystik.features.controller.GetPagedOrgsController
import ru.astrainteractive.kapitalystik.features.controller.InviteController
import ru.astrainteractive.kapitalystik.features.controller.KickController
import ru.astrainteractive.kapitalystik.features.controller.MakeWarpPublicController
import ru.astrainteractive.kapitalystik.features.controller.RenameController
import ru.astrainteractive.kapitalystik.features.controller.SetDescriptionController
import ru.astrainteractive.kapitalystik.features.controller.SetStatusController
import ru.astrainteractive.kapitalystik.features.controller.SetWarpController
import ru.astrainteractive.kapitalystik.features.controller.TransferController
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer
import ru.astrainteractive.klibs.kdi.Factory

class ClanManagementControllers(module: ClanManagementControllerContainer) {
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
