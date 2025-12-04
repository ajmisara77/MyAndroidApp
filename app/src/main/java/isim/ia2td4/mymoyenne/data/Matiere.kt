package isim.ia2td4.mymoyenne.data

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import isim.ia2td4.mymoyenne.R
data class Matiere(
    val id: Int,
    var nom: String,
    val notes: MutableList<Note> = mutableListOf()
)
