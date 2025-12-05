package isim.ia2td4.mymoyenne

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import isim.ia2td4.mymoyenne.databinding.ActivityEditNoteBinding
import isim.ia2td4.mymoyenne.moyenne.DatabaseHelper

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        val noteId = intent.getIntExtra("noteId", -1)
        val valeur = intent.getDoubleExtra("noteValeur", 0.0)
        binding.editNote.setText(valeur.toString())

        binding.btnModifier.setOnClickListener {
            val text = binding.editNote.text.toString()
            val newValeur = text.toDoubleOrNull()

            if (newValeur == null || newValeur < 0 || newValeur > 20) {
                binding.editNote.error = "Note entre 0 et 20"
                return@setOnClickListener
            }

            db.updateNote(noteId, newValeur)
            Toast.makeText(this, "Note modifiée !", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnSupprimer.setOnClickListener {
            db.deleteNote(noteId)
            Toast.makeText(this, "Note supprimée !", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
