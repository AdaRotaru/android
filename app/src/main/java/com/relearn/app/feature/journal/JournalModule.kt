package com.relearn.app.feature.journal

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class JournalModule {
    @Binds
    abstract fun bindJournalRepository(
        journalRepository: JournalRepository
    ): JournalInterface
}

@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {

   @Provides
    @Named("OpenAiApiKey")
   fun provideOpenAiApiKey(): String = "cheie"
}