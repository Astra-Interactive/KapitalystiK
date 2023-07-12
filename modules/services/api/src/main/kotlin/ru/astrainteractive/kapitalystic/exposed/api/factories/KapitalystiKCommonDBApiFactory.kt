package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApiImpl

class KapitalystiKCommonDBApiFactory : Factory<KapitalystiKCommonDBApi> {
    override fun build(): KapitalystiKCommonDBApi {
        return KapitalystiKCommonDBApiImpl()
    }
}
