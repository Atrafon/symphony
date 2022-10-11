package io.github.zyrouge.symphony.services.groove

import android.provider.MediaStore
import android.util.Log
import io.github.zyrouge.symphony.Symphony
import java.util.stream.Stream

// https://github.com/Chandigarh-University-students/Music-Player/blob/da38b1c9d18e5514c11e3a347b9ac0a2ba7bda24/app/src/main/java/com/projects/musicplayer/fragments/HomeFragment.kt
class SongRepository {
    lateinit var cached: MutableMap<Long, Song>;
    val onUpdate = Stream.builder<Int>()!!;

    fun init() {
        fetch()
    }

    fun fetch(): Int {
        var cursor = Symphony.context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Audio.Media.IS_MUSIC + " != 0",
            null,
            MediaStore.Audio.Media.TITLE + " ASC"
        );
        var songs = mutableMapOf<Long, Song>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val song = Song.fromCursor(cursor)
                songs[song.id] = song
            }
        }
        cached = songs
        Log.i("song", "total: ${cached.size} (${cursor?.count})")
        cached.forEach {
            Log.i("song", it.toString())
        }
        val total = songs.size
        onUpdate.add(total)
        return total
    }
}