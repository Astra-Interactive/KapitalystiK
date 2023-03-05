package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.kapitalystic.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApiImpl

class KapitalystiKCommonDBApiFactory : Factory<KapitalystiKCommonDBApi>() {
    override fun initializer(): KapitalystiKCommonDBApi {
        return KapitalystiKCommonDBApiImpl()
    }
}
