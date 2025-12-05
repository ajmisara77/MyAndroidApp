package isim.ia2td4.mymoyenne.moyenne

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import isim.ia2td4.mymoyenne.data.Matiere
import isim.ia2td4.mymoyenne.data.Note

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "moyenne.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE matiere (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT NOT NULL
            )
        """)

        db.execSQL("""
            CREATE TABLE note (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                valeur REAL NOT NULL,
                matiere_id INTEGER NOT NULL,
                FOREIGN KEY(matiere_id) REFERENCES matiere(id)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS note")
        db.execSQL("DROP TABLE IF EXISTS matiere")
        onCreate(db)
    }

    // ---- MATIERE CRUD ----
    fun ajouterMatiere(nom: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("nom", nom)
        db.insert("matiere", null, cv)
    }

    fun getAllMatieres(): List<Matiere> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM matiere", null)
        val list = mutableListOf<Matiere>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"))
            list.add(Matiere(id, nom))
        }
        cursor.close()
        return list
    }

    // ---- NOTES CRUD ----
    fun ajouterNote(matiereId: Int, valeur: Double) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("valeur", valeur)
        cv.put("matiere_id", matiereId)
        db.insert("note", null, cv)
    }

    fun getNotesOf(matiereId: Int): List<Note> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM note WHERE matiere_id = ?",
            arrayOf(matiereId.toString())
        )
        val list = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val valeur = cursor.getDouble(cursor.getColumnIndexOrThrow("valeur"))
            list.add(Note(id, valeur))
        }
        cursor.close()
        return list
    }

    fun updateNote(id: Int, valeur: Double) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("valeur", valeur)
        db.update("note", cv, "id=?", arrayOf(id.toString()))
    }

    fun deleteNote(id: Int) {
        val db = writableDatabase
        db.delete("note", "id=?", arrayOf(id.toString()))
    }
}
