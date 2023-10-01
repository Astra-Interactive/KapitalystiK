package ru.astrainteractive.kapitalystik.api.di.factory

import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.api.impl.KapitalystiKDBApiImpl
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
