package isim.ia2td4.mymoyenne

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import isim.ia2td4.mymoyenne.data.DataProvider
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.databinding.ActivityAddMatiereBinding

class AddMatiereActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMatiereBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatiereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnValider.setOnClickListener {
            val nom = binding.editNomMatiere.text.toString()
            DataProvider.matieres.add(Matiere(DataProvider.matieres.size, nom))
            finish()
        }
    }
}