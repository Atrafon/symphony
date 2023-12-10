package io.github.zyrouge.symphony.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import io.github.zyrouge.symphony.ui.helpers.ViewContext

class ModifierApplyMultiple(var modifier: Modifier) {
    fun applySingle(fn: Modifier.() -> Modifier) {
        modifier = fn(modifier)
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    fun build() = modifier
}

fun Modifier.applyMultiple(fn: ModifierApplyMultiple.() -> Unit): Modifier {
    val builder = ModifierApplyMultiple(this)
    fn.invoke(builder)
    return builder.build()
}

fun <T> contextWrapped(fn: (ViewContext) -> T) = fn

fun <T> SnapshotStateList<T>.asList() = this as List<T>

class RapidMutableStateList<T>(
    val list: SnapshotStateList<T>,
    val interval: Long = 1000L,
) {
    val pending = mutableListOf<T>()
    var lastUpdated = 0L

    fun add(vararg value: T) {
        pending.addAll(value)
        val time = System.currentTimeMillis()
        if (time - lastUpdated > interval) {
            sync()
        }
    }

    fun sync() {
        if (pending.isEmpty()) return
        lastUpdated = System.currentTimeMillis()
        val values = pending.toList()
        pending.clear()
        list.addAll(values)
    }
}
