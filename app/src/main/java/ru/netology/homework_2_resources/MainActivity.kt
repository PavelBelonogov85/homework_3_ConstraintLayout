package ru.netology.homework_2_resources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.homework_2_resources.dto.Post
import ru.netology.homework_2_resources.databinding.ActivityMainBinding
import ru.netology.homework_2_resources.databinding.CardPostBinding
import ru.netology.homework_2_resources.utils.StringsVisability
import ru.netology.homework_2_resources.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("pvl_info", "Запустилось")

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //////
        // читаем данные поста из JSON:
        /*
        val fileJSON = File("./src/main/res/raw/posts.json") // почему не открывается файл??
        var jsonString:String
        FileReader(fileJSON).use {
            val chars = CharArray(fileJSON.length().toInt())
            it.read(chars)
            jsonString = String(chars)
        }
        val mapper = jacksonObjectMapper()
        */
        // вот на этой строке ошибка:
        // val postFromJson = mapper.readValue<Post>(jsonString) // ошибка "None of the following functions can be called with the arguments supplied."
        //////

        val viewModel: PostViewModel by viewModels() // функция «by viewModels()» означает, что сколько бы раз activity не пересоздавался, мы будем получать одну и ту же ссылку на одну и ту же модель (ViewModel)

        viewModel.data.observe(this) { posts -> // наблюдаем (observe) за изменением data. Если оно происходит - получаем на входе объект posts...
            val views = posts.map {post ->
                val binding = CardPostBinding.inflate(layoutInflater, activityMainBinding.root, false)
                with(binding) {
                    /* все изменения визуализации (тексты, картинки...) переезжают сюда!
                       а все изменения скрытых данных (счетчики и др.) будут в функциях viewModel */

                    // какие объекты верстки доступны по ID можно проверить:
                    // написав "binding." по всплывающей подсказке
                    // или посмотреть класс в "\project_name\app\build\generated\data_binding_base_class_source_out\debug\out\ru\netology\homework_2_resources\databinding\ActivityMainBinding.java"
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    avatar.setImageResource(R.drawable.ic_launcher_foreground) // но это не из sampledata, тут в общем случае должен быть
                    likesText.text = StringsVisability.getCoolNumeralString(post.likes)
                    sharesText.text = StringsVisability.getCoolNumeralString(post.shares)
                    viewsText.text = StringsVisability.getCoolNumeralString(post.views)

                    if (post.likedByMe) {
                        likesIcon.setImageResource(R.drawable.ic_liked_24) // ! binding самопроизвольно меняет id элементов из snake_case в camelCase !
                    } else {
                        likesIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                    }

                    binding.likesIcon.setOnClickListener {
                        viewModel.likeById(post.id)
                    }
                    binding.sharesIcon.setOnClickListener {
                        viewModel.share(post.id)
                    }
                }
                binding  // вот это зачем???? и что за конструкция языка вообще
            }

            views.forEach() {
                activityMainBinding.root.addView(it.root)
            }
        }


        // как "красиво" объединить однотипные setOnClickListener в общую функцию и передавать в нее только элемент как параметр - см. в записках

        /* << тестирование подписок на события (д/з 4.2) */
        /*binding.root.setOnClickListener {
            Log.i("pvl_info", "click on root")
        }
        binding.avatar.setOnClickListener {
            Log.i("pvl_info", "click on avatar")
        }*/
        /* тестирование подписок на события (д/з 4.2) >> */
    }


}