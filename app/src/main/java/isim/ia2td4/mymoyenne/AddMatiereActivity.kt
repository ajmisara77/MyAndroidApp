package isim.ia2td4.mymoyenne

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.databinding.ActivityAddMatiereBinding
import android.widget.Toast
import isim.ia2td4.mymoyenne.moyenne.DatabaseHelper


class AddMatiereActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMatiereBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatiereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        binding.btnValider.setOnClickListener {
            val nom = binding.editNomMatiere.text.toString().trim()

            if (nom.isBlank()) {
                binding.editNomMatiere.error = "Veuillez saisir un nom"
                return@setOnClickListener
            }

            db.ajouterMatiere(nom)
            Toast.makeText(this, "Matière ajoutée !", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
