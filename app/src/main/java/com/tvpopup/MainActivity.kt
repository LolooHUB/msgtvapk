package com.tvpopup

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.*

class MainActivity : FragmentActivity() {

    private lateinit var database: DatabaseReference
    private var ultimoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("mensaje")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val texto = snapshot.child("texto").getValue(String::class.java)
                val id = snapshot.child("id").getValue(Long::class.java) ?: -1

                if (id != ultimoId && texto != null) {
                    ultimoId = id
                    mostrarPopup(texto)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Opcional: manejar error
            }
        })
    }

    private fun mostrarPopup(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Mensaje de la Web")
            .setMessage(mensaje)
            .setPositiveButton("OK", null)
            .show()
    }
}
