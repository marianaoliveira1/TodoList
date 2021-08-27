package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.View.UserAdapter
import com.example.todolist.model.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var add_btn:FloatingActionButton
    private lateinit var  recy:RecyclerView
    private lateinit var userList:ArrayList<UserData>
    private lateinit var userAdapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()

        add_btn = findViewById(R.id.addbtn)
        recy = findViewById(R.id.recyclerview)

        userAdapter = UserAdapter(this,userList)

        recy.layoutManager = LinearLayoutManager(this)
        recy.adapter = userAdapter
        add_btn.setOnClickListener {
            addInfo()
        }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.adicionar_item, null)
        val userTitle = v.findViewById<EditText>(R.id.titulo_tarefa)
        val userDescription = v.findViewById<EditText>(R.id.titulo_descricao)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val titulos = userTitle.text.toString()
            val descricao = userDescription.text.toString()
            userList.add(UserData("$titulos", "$descricao"))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Tarefa addicionada com sucesso", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancelado", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }
}
