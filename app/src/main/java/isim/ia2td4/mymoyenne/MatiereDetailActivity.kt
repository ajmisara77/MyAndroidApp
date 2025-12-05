package isim.ia2td4.mymoyenne


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import isim.ia2td4.mymoyenne.AddNoteActivity
import isim.ia2td4.mymoyenne.EditNoteActivity
import isim.ia2td4.mymoyenne.data.Note
import isim.ia2td4.mymoyenne.databinding.ActivityMatiereDetailBinding
import isim.ia2td4.mymoyenne.moyenne.DatabaseHelper



class MatiereDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatiereDetailBinding
    private lateinit var db: DatabaseHelper
    private var matiereId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatiereDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)

        matiereId = intent.getIntExtra("matiereId", -1)
        val matiere = db.getAllMatieres().firstOrNull { it.id == matiereId }
        if (matiere != null) {
            binding.textMatiereName.text = matiere.nom
        }

        loadNotes()

        binding.btnAjouterNote.setOnClickListener {
            val i = Intent(this, AddNoteActivity::class.java)
            i.putExtra("matiereId", matiereId)
            startActivity(i)
        }

        binding.listViewNotes.setOnItemClickListener { _, _, position, _ ->
            val note = db.getNotesOf(matiereId)[position]
            val i = Intent(this, EditNoteActivity::class.java)
            i.putExtra("noteId", note.id)
            i.putExtra("noteValeur", note.valeur)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        val notes: List<Note> = db.getNotesOf(matiereId)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes.map { it.valeur })
        binding.listViewNotes.adapter = adapter
    }
}
