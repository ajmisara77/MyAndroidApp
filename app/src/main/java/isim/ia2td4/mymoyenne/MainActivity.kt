package isim.ia2td4.mymoyenne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import isim.ia2td4.mymoyenne.data.DataProvider
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisation de l'adapter
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataProvider.matieres.map { it.nom }
        )
        binding.listViewMatieres.adapter = adapter

        // Bouton pour ajouter une matière
        binding.btnAjouterMatiere.setOnClickListener {
            startActivity(Intent(this, AddMatiereActivity::class.java))
        }

        // Clic sur une matière pour voir les détails
        binding.listViewMatieres.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MatiereDetailActivity::class.java)
            intent.putExtra("matiereIndex", position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Mise à jour de la liste avec les moyennes par matière
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataProvider.matieres.map { matiere ->
                val moyenne = calculerMoyenneMatiere(matiere)
                if (matiere.notes.isNotEmpty()) {
                    "${matiere.nom} - Moyenne: %.2f".format(moyenne)
                } else {
                    matiere.nom
                }
            }
        )
        binding.listViewMatieres.adapter = adapter

        // Affichage de la moyenne générale
        val moyenneGenerale = calculerMoyenneGenerale()
        binding.textMoyenneGenerale.text = "Moyenne générale : %.2f".format(moyenneGenerale)
    }

    // Calcule la moyenne générale de toutes les matières
    private fun calculerMoyenneGenerale(): Double {
        val toutesNotes = DataProvider.matieres.flatMap { it.notes }
        if (toutesNotes.isEmpty()) return 0.0
        return toutesNotes.map { it.valeur }.average()
    }

    // Calcule la moyenne d'une matière spécifique
    private fun calculerMoyenneMatiere(matiere: Matiere): Double {
        if (matiere.notes.isEmpty()) return 0.0
        return matiere.notes.map { it.valeur }.average()
    }
}
