package isim.ia2td4.mymoyenne

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import isim.ia2td4.mymoyenne.data.DataProvider
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.databinding.ActivityMatiereDetailBinding

class MatiereDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatiereDetailBinding
    private lateinit var matiere: Matiere
    private lateinit var adapter: ArrayAdapter<Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatiereDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matiereIndex = intent.getIntExtra("matiereIndex", 0)
        matiere = DataProvider.matieres[matiereIndex]

        binding.textMatiereName.text = matiere.nom

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            matiere.notes.map { it.valeur }
        )
        binding.listViewNotes.adapter = adapter

        binding.btnAjouterNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("matiereIndex", matiereIndex)
            startActivity(intent)
        }

        binding.listViewNotes.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("matiereIndex", matiereIndex)
            intent.putExtra("noteIndex", position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            matiere.notes.map { it.valeur }
        )
        binding.listViewNotes.adapter = adapter
    }
}
