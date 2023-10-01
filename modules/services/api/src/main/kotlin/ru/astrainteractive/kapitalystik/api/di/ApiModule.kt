package ru.astrainteractive.kapitalystik.api.di

import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKDBApiFactory
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface ApiModule {
    val kapitalystiKCommonDBApi: KapitalystiKCommonDBApi
    val kapitalystiKDBApi: KapitalystiKDBApi

    class Default : ApiModule {
        override val kapitalystiKCommonDBApi: KapitalystiKCommonDBApi by Provider {
            KapitalystiKCommonDBApiFactory().create()
        }
        override val kapitalystiKDBApi: KapitalystiKDBApi by Provider {
            KapitalystiKDBApiFactory(
                dbCommon = kapitalystiKCommonDBApi
            ).create()
        }
    }
}
