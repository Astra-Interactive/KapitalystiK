package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.kapitalystic.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApiImpl

class KapitalystiKDBApiFactory(
    private val dbCommon: KapitalystiKCommonDBApi,
) : Factory<KapitalystiKDBApi>() {
    override fun initializer(): KapitalystiKDBApi {
        return KapitalystiKDBApiImpl(
            dbCommon = dbCommon
        )
    }
}
