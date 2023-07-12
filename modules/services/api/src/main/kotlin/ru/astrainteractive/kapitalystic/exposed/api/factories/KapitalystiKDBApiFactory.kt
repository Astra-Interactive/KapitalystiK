package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApiImpl

class KapitalystiKDBApiFactory(
    private val dbCommon: KapitalystiKCommonDBApi,
) : Factory<KapitalystiKDBApi> {
    override fun build(): KapitalystiKDBApi {
        return KapitalystiKDBApiImpl(
            dbCommon = dbCommon
        )
    }
}
