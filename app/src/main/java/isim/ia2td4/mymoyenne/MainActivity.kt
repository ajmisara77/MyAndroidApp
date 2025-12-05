package isim.ia2td4.mymoyenne

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.data.Note
import isim.ia2td4.mymoyenne.databinding.ActivityMainBinding
import isim.ia2td4.mymoyenne.moyenne.DatabaseHelper
import isim.ia2td4.mymoyenne.AddMatiereActivity
import isim.ia2td4.mymoyenne.MatiereDetailActivity




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        // Initialisation de l'adapter
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            db.getAllMatieres().map { it.nom }
        )
        binding.listViewMatieres.adapter = adapter

        // Bouton pour ajouter une matière
        binding.btnAjouterMatiere.setOnClickListener {
            startActivity(Intent(this, AddMatiereActivity::class.java))
        }

        // Clic sur une matière pour voir les détails
        binding.listViewMatieres.setOnItemClickListener { _, _, position, _ ->
            val matiere = db.getAllMatieres()[position]
            val intent = Intent(this, MatiereDetailActivity::class.java)
            intent.putExtra("matiereId", matiere.id)  // <-- use matiereId
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Mise à jour de la liste avec les moyennes par matière
        val matieres = db.getAllMatieres()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            matieres.map { matiere ->
                val moyenne = calculerMoyenneMatiere(matiere)
                val notes = db.getNotesOf(matiere.id)
                if (notes.isNotEmpty()) {
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
        val toutesNotes: List<Note> = db.getAllMatieres().flatMap { db.getNotesOf(it.id) }
        if (toutesNotes.isEmpty()) return 0.0
        return toutesNotes.map { it.valeur }.average()
    }

    // Calcule la moyenne d'une matière spécifique
    private fun calculerMoyenneMatiere(matiere: Matiere): Double {
        val notes = db.getNotesOf(matiere.id)
        if (notes.isEmpty()) return 0.0
        return notes.map { it.valeur }.average()
    }
}
