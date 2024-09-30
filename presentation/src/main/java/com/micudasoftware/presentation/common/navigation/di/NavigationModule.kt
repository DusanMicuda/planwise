package com.micudasoftware.presentation.common.navigation.di

import com.micudasoftware.presentation.common.navigation.DefaultNavigator
import com.micudasoftware.presentation.common.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigator(): Navigator = DefaultNavigator()
}