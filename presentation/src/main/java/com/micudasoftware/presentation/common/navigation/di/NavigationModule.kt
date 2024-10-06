package com.micudasoftware.presentation.common.navigation.di

import com.micudasoftware.presentation.common.navigation.DefaultNavigator
import com.micudasoftware.presentation.common.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = DefaultNavigator()
}