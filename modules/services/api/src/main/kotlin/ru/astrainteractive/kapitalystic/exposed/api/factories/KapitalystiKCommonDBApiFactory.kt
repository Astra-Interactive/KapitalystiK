package ru.astrainteractive.kapitalystic.exposed.api.factories

import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApiImpl
import ru.astrainteractive.klibs.kdi.Factory

class KapitalystiKCommonDBApiFactory : Factory<KapitalystiKCommonDBApi> {
    override fun create(): KapitalystiKCommonDBApi {
        return KapitalystiKCommonDBApiImpl()
    }
}
