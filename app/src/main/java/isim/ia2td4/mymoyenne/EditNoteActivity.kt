package isim.ia2td4.mymoyenne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import isim.ia2td4.mymoyenne.data.DataProvider
import isim.ia2td4.mymoyenne.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matiereIndex = intent.getIntExtra("matiereIndex", 0)
        val noteIndex = intent.getIntExtra("noteIndex", 0)

        val matiere = DataProvider.matieres[matiereIndex]
        val note = matiere.notes[noteIndex]

        binding.editNote.setText(note.valeur.toString())

        binding.btnModifier.setOnClickListener {
            note.valeur = binding.editNote.text.toString().toDouble()
            finish()
        }

        binding.btnSupprimer.setOnClickListener {
            matiere.notes.removeAt(noteIndex)
            finish()
        }
    }
}