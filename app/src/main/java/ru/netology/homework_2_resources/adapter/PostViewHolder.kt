package ru.netology.homework_2_resources.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.homework_2_resources.R
import ru.netology.homework_2_resources.databinding.CardPostBinding
import ru.netology.homework_2_resources.dto.Post
import ru.netology.homework_2_resources.utils.StringsVisability

class PostViewHolder (
    private val binding: CardPostBinding,
    private val onLikeClicked: OnLikeClickListener,
    private val onShareClicked: OnShareClickListener,
        ): ViewHolder(binding.root) { /* ссылка на конкретную вьюшку, он ее хранит и ее можно из него забрать при необходимости */

    fun bind(post: Post) {
        with(binding) {
            /* все изменения визуализации (тексты, картинки...) переезжают сюда!
               а все изменения скрытых данных (счетчики и др.) будут в функциях viewModel */

            // какие объекты верстки доступны по ID можно проверить:
            // написав "binding." по всплывающей подсказке
            // или посмотреть класс в "\project_name\app\build\generated\data_binding_base_class_source_out\debug\out\ru\netology\homework_2_resources\databinding\ActivityMainBinding.java"
            author.text = post.author
            published.text = post.published
            content.text = post.content
            avatar.setImageResource(R.drawable.ic_launcher_foreground)
            likesText.text = StringsVisability.getCoolNumeralString(post.likes)
            sharesText.text = StringsVisability.getCoolNumeralString(post.shares)
            viewsText.text = StringsVisability.getCoolNumeralString(post.views)

            // ! binding самопроизвольно меняет id элементов из snake_case в camelCase !
            /*if (post.likedByMe) {
                likesIcon.setImageResource(R.drawable.ic_liked_24)
            } else {
                likesIcon.setImageResource(R.drawable.baseline_favorite_border_24)
            }*/
            // лучше всегда менять все поля, чтобы не получить по карусели предыдущую версию view, поэтому
            // лучше писать вот так:
            likesIcon.setImageResource(
                if (post.likedByMe) {R.drawable.ic_liked_24}
                else {R.drawable.baseline_favorite_border_24}
            )

            binding.likesIcon.setOnClickListener {
                //viewModel.likeById(post.id)
                onLikeClicked(post)
            }
            binding.sharesIcon.setOnClickListener {
                //viewModel.share(post.id)
                onShareClicked(post)
            }
        }

    }
}