package ru.astrainteractive.kapitalystik.api.di.factory

import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.impl.KapitalystiKCommonDBApiImpl
import ru.astrainteractive.klibs.kdi.Factory

class KapitalystiKCommonDBApiFactory : Factory<KapitalystiKCommonDBApi> {
    override fun create(): KapitalystiKCommonDBApi {
        return KapitalystiKCommonDBApiImpl()
    }
}
