package com.ricardoteixeira.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "task_table")
data class Task(

    val name: String,

    val important: Boolean = false,

    val completed: Boolean = false,

    val createdTimestamp: Long = System.currentTimeMillis(),

    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 0
): Parcelable {

    val createdDateFormated: String
        get() = DateFormat.getDateTimeInstance().format(createdTimestamp)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (name != other.name) return false
        if (important != other.important) return false
        if (completed != other.completed) return false
        if (createdTimestamp != other.createdTimestamp) return false
        if (primaryKey != other.primaryKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + important.hashCode()
        result = 31 * result + completed.hashCode()
        result = 31 * result + createdTimestamp.hashCode()
        result = 31 * result + primaryKey
        return result
    }

}