package com.example.fulop_zoltan_simplexion.Modules

import com.example.fulop_zoltan_simplexion.Other.Utility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
object UtilityModule {

   @Provides
    fun provideUtility():Utility=Utility
}