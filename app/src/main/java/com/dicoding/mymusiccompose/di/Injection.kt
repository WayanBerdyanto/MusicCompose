package com.dicoding.mymusiccompose.di

import com.dicoding.mymusiccompose.data.MusicRepository

object Injection {
    fun provideRepository(): MusicRepository {
        return MusicRepository.getInstance()
    }
}