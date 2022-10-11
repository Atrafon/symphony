package io.github.zyrouge.symphony.services.groove

import android.database.Cursor
import android.graphics.Bitmap
import android.provider.MediaStore.Audio.AlbumColumns
import android.provider.MediaStore.Audio.AudioColumns
import androidx.core.database.getStringOrNull
import io.github.zyrouge.symphony.Symphony
import io.github.zyrouge.symphony.utils.getColumnValue
import io.github.zyrouge.symphony.utils.getColumnValueNullable

data class Album(
    val albumId: Long,
    val albumName: String,
    val artistId: Long,
    val artistName: String?
) {
    fun getArtwork(): Bitmap {
        return Symphony.groove.album.fetchAlbumArtwork(albumId)
    }

    companion object {
        fun fromCursor(cursor: Cursor): Album {
            return Album(
                albumId = cursor.getColumnValue(AlbumColumns.ALBUM_ID) {
                    cursor.getLong(it)
                },
                albumName = cursor.getColumnValue(AlbumColumns.ALBUM) {
                    cursor.getString(it)
                },
                artistId = cursor.getColumnValue(AlbumColumns.ARTIST_ID) {
                    cursor.getLong(it)
                },
                artistName = cursor.getColumnValueNullable(AudioColumns.ARTIST) {
                    cursor.getStringOrNull(it)
                }
            )
        }
    }
}