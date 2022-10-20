package com.postmage.model.posts.add_posts


import com.postmage.util.DateUtil
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class AddPostModel(
    var photoName: String? = null,
    var photoBytes: ByteArray? = null,
    var description: String = "",
    var groupId: String = "",
    val creationTime: Long = DateUtil.getTimeNow()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddPostModel

        if (photoName != other.photoName) return false
        if (!photoBytes.contentEquals(other.photoBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photoName.hashCode()
        result = 31 * result + photoBytes.contentHashCode()
        return result
    }
}