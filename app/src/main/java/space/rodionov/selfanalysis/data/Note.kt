package space.rodionov.selfanalysis.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

// part 2
@Entity(tableName = "note_table")
@Parcelize
data class Note(
    val situation: String,
    val emotions: String/*? = null*/,
    val feelings: String/*? = null*/,
    val inTheBody: String/*? = null*/,
    val wantedToDo: String? = null,
    val whatDoesTheFeelingMean: String? = null,
    val thoughts: String? = null,
    val fears: String? = null,
    val askingFromHP: String? = null,
    val innerCritic: String? = null,
    val lovingParent: String? = null,
    val date: String, /*= System.currentTimeMillis().toString(),*/ /*Long = System.currentTimeMillis(),*/
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    /*val dateFormatted: String
        get() = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(date)*/
}