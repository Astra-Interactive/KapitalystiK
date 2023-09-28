package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApiImpl
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
