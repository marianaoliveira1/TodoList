package com.example.todolist.View

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.UserData

class UserAdapter(val c:Context, val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var title:TextView
        var description:TextView
        var menu:ImageView

        init {
            title = v.findViewById<TextView>(R.id.titulo)
            description = v.findViewById<TextView>(R.id.subtitulo)
            menu = v.findViewById(R.id.menu)
            menu.setOnClickListener { popusMenus(it) }
        }

        private fun popusMenus(v:View) {
            val position = userList[adapterPosition]
            val popusMenus = PopupMenu(c,v)
            popusMenus.inflate(R.menu.show_menu)
            popusMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editar->{
                        val v = LayoutInflater.from(c).inflate(R.layout.adicionar_item,null)
                        val titulo = v.findViewById<EditText>(R.id.titulo_tarefa)
                        val descricao = v.findViewById<EditText>(R.id.titulo_descricao)
                            AlertDialog.Builder(c)
                                .setView(v)
                                .setPositiveButton("ok"){
                                    dialog,_->
                                    position.Title = titulo.text.toString()
                                    position.Description = descricao.text.toString()
                                    notifyDataSetChanged()
                                    Toast.makeText(c,"Informações editadas", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                                .setNegativeButton("cancel"){
                                        dialog,_->
                                    dialog.dismiss()
                                }
                                .create()
                                .show()

                        true
                    }
                    R.id.deletar->{
                        AlertDialog.Builder(c)
                            .setTitle("Deletar")
                            .setIcon(R.drawable.ic_delete)
                            .setMessage("Informações deletadas")
                            .setPositiveButton("sim") {
                                dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Detelada as informações", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("não"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else->true
                }
            }
            popusMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible= true
            val menus = popup.get(popusMenus)
            menus.javaClass.getDeclaredMethod(
                "setForceShowIcon", Boolean::class.java
            ).invoke(menu,true)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.activity_lista, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.title.text = newList.Title
        holder.description.text = newList.Description
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}