package data_classes

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long = 0,
    var likedByMe: Boolean = false,
    var shares: Long = 0,
    var views: Long = 0
)