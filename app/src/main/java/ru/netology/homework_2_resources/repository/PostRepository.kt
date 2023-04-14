package ru.netology.homework_2_resources.repository

import androidx.lifecycle.LiveData
import ru.netology.homework_2_resources.dto.Post

interface PostRepository {
    fun getData(): LiveData<Post>
    fun like()
    fun share()
}