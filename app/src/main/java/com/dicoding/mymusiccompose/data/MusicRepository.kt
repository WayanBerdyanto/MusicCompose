package com.dicoding.mymusiccompose.data

import com.dicoding.mymusiccompose.model.Music
import com.dicoding.mymusiccompose.model.MusicDataSource
import com.dicoding.mymusiccompose.model.MusicDataSource.music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MusicRepository {

    private val orderMusic = mutableListOf<Music>()

    init {
        if (orderMusic.isEmpty()) {
            MusicDataSource.music.forEach {
                orderMusic.add(Music(it.id, it.title, it.photoUrl, it.description, it.singing))
            }
        }
    }
    fun getAllMusic(): Flow<List<Music>> {
        return flowOf(music)
    }

    fun getAllMusicById(id: Long): Music{
        return music.first{
            it.id == id
        }
    }

    fun searchMusic(query: String): Flow<List<Music>>{
        return flowOf( MusicDataSource.music.filter {
            it.title.contains(query, ignoreCase = true)
        })
    }

    companion object{
        @Volatile
        private var instance: MusicRepository?= null

        fun getInstance(): MusicRepository =
            instance ?: synchronized(this){
                MusicRepository().apply { instance = this }
            }
    }
}