package isim.ia2td4.mymoyenne

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import isim.ia2td4.mymoyenne.databinding.ActivityAddNoteBinding
import isim.ia2td4.mymoyenne.moyenne.DatabaseHelper

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        val matiereId = intent.getIntExtra("matiereId", -1)

        binding.btnValider.setOnClickListener {
            val noteText = binding.editNote.text.toString()

            if (noteText.isBlank()) {
                binding.editNote.error = "Veuillez saisir une note"
                return@setOnClickListener
            }

            val valeur = noteText.toDoubleOrNull()
            if (valeur == null || valeur < 0 || valeur > 20) {
                binding.editNote.error = "Note entre 0 et 20"
                return@setOnClickListener
            }

            db.ajouterNote(matiereId, valeur)
            Toast.makeText(this, "Note ajoutée !", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
