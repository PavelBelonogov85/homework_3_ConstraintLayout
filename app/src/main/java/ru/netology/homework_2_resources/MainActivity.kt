package ru.netology.homework_2_resources

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import data_classes.Post
import ru.netology.homework_2_resources.databinding.ActivityMainBinding
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("pvl_info", "Запустилось")

        val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // читаем данные поста из JSON:
        // val fileJSON = File("/app/sampledata/posts.json") // почему не открывается файл??
        var jsonString:String
        //FileReader(fileJSON).use {
        //    val chars = CharArray(fileJSON.length().toInt())
        //    it.read(chars)
        //    jsonString = String(chars)
        //}
        val mapper = jacksonObjectMapper()
        // вот на этой строке ошибка:
        // val postFromJson = mapper.readValue<Post>(jsonString) // ошибка "None of the following functions can be called with the arguments supplied."
        // приходится вот так:
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 999,
            shares = 999_999,
            views = 1_256_000_000
        )

        with(binding) {
            // какие объекты верстки доступны по ID можно проверить:
            // написав "binding." по всплывающей подсказке
            // или посмотреть класс в "\project_name\app\build\generated\data_binding_base_class_source_out\debug\out\ru\netology\homework_2_resources\databinding\ActivityMainBinding.java"

            author.text = post.author
            published.text = post.published
            content.text = post.content
            //avatar?.setImageResource(R.sampledata.posts_avatar_drawable) // как подставить картинку из sampledata??
            likesText?.text = getCoolNumeralString(post.likes)
            sharesText?.text = getCoolNumeralString(post.shares)
            viewsText?.text = getCoolNumeralString(post.views)

            if (post.likedByMe) {
                likesIcon?.setImageResource(R.drawable.ic_liked_24) // ! binding самопроизвольно меняет id элементов из snake_case в camelCase !
            }

            likesIcon?.setOnClickListener {
                Log.i("pvl_info", "like")
                post.likedByMe = !post.likedByMe
                likesIcon.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesText?.text = getCoolNumeralString(post.likes)
            }

            sharesIcon?.setOnClickListener {
                Log.i("pvl_info", "share")
                post.shares++
                sharesText?.text = getCoolNumeralString(post.shares)
            }

            // как "красиво" объединить однотипные setOnClickListener в общую функцию и передавать в нее только элемент как параметр? 
        }
    }

    private fun getCoolNumeralString(num:Long):String = when(num) {
        in 0..999 -> num.toString()
        in 1_000..999_999 -> {"${num/1000} ${if(num%1000>0){".${num%1000/100}"}else{""}} K"}
        in 1_000_000..999_999_999 -> {"${num/1000000} ${if(num%1000000>0){".${num%1000000/100000}"}else{""}} M"}
        else -> {"${num/1000_000_000} ${if(num%1000_000_000>0){".${num%1000_000_000/100_000_000}"}else{""}} B"}
    }
}