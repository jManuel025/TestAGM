package com.example.testagm.data.local.di

import com.example.testagm.data.local.model.CharacterEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesRealm(): Realm {
        val config = RealmConfiguration.create(schema = setOf(CharacterEntity::class))
        return Realm.open(config)
    }
}