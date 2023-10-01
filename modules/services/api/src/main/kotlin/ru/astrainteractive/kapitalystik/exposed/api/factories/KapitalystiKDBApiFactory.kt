package ru.astrainteractive.kapitalystik.exposed.api.factories

import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKDBApiImpl
import ru.astrainteractive.klibs.kdi.Factory

class KapitalystiKDBApiFactory(
    private val dbCommon: KapitalystiKCommonDBApi,
) : Factory<KapitalystiKDBApi> {
    override fun create(): KapitalystiKDBApi {
        return KapitalystiKDBApiImpl(
            dbCommon = dbCommon
        )
    }
}
