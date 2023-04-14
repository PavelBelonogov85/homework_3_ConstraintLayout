package ru.netology.homework_2_resources.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.homework_2_resources.dto.Post

class PostRepositoryInMemory : PostRepository {
    /* внутри объекта PostRepositoryInMemory будем хранить неизменяемую переменную класса Post,
    которую будем при срабатывании каждой функции изменения данных копировать и перезаписывать в
    новую переменную.
    Кроме того в нем будет обертка data MutableLiveData(post) над этой переменной. Она будет
    сообщать нам, когда в Post что-то изменится. Сообщать будет своим подписчикам. Подписчиками
    будут активити. */
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,
        likes = 999,
        shares = 10_999_997,
        views = 1_256_000_000
    )
    private val data = MutableLiveData(post) // обертка над другим объектом, которая оповещает, если что-то изменилось внутри переданного в нее объекта

    override fun getData(): LiveData<Post> = data

    override fun like() {
        //Log.i("pvl_info", "click on like") // как вывести в лог из другого класса?
        post = post.copy(
            likes = if (post.likedByMe) post.likes-1 else post.likes+1,
            likedByMe = !post.likedByMe
        )
        data.value = post // обновляем то, что внутри LiveData
    }

    override fun share() {
        //Log.i("pvl_info", "click on share")
        post = post.copy(
            shares = post.shares + 1
        )
        data.value = post
    }

}