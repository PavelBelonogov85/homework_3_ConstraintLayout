package ru.netology.homework_2_resources.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.homework_2_resources.dto.Post
import ru.netology.homework_2_resources.repository.PostRepository
import ru.netology.homework_2_resources.repository.PostRepositoryInMemory

class PostViewModel: ViewModel() {
    /* Тут создаем класс, наследующий от ViewModel() который будет "ловить" события изменения
    в данных */
    private val repository: PostRepository = PostRepositoryInMemory()

    val data: LiveData<Post> = repository.getData()
    fun like() = repository.like()
    fun share() = repository.share()
}