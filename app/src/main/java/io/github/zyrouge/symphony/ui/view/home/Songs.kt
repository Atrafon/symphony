package io.github.zyrouge.symphony.ui.view.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.zyrouge.symphony.Symphony
import io.github.zyrouge.symphony.ui.components.SongCard
import io.github.zyrouge.symphony.ui.view.helpers.ViewContext

@Composable
fun SongsView(context: ViewContext) {
    val songs = Symphony.groove.song.cached.values.toList()
    LazyColumn(
        modifier = Modifier
            .padding(top = 4.dp)
    ) {
        items(songs.size) { i ->
            val song = songs[i]
            SongCard(context, song) {
                Symphony.player.addToQueue(
                    songs.subList(i, songs.size).toList()
                )
            }
        }
    }
}

