package ru.astrainteractive.kapitalystik.exposed.api.factories

import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKCommonDBApiImpl
import ru.astrainteractive.klibs.kdi.Factory

class KapitalystiKCommonDBApiFactory : Factory<KapitalystiKCommonDBApi> {
    override fun create(): KapitalystiKCommonDBApi {
        return KapitalystiKCommonDBApiImpl()
    }
}
