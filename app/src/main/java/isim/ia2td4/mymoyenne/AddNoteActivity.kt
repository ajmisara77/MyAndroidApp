package isim.ia2td4.mymoyenne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import isim.ia2td4.mymoyenne.data.DataProvider
import isim.ia2td4.mymoyenne.data.Note
import isim.ia2td4.mymoyenne.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matiereIndex = intent.getIntExtra("matiereIndex", 0)
        val matiere = DataProvider.matieres[matiereIndex]

        binding.btnValider.setOnClickListener {
            val valeur = binding.editNote.text.toString().toDouble()
            matiere.notes.add(Note(valeur))
            finish()
        }
    }
}