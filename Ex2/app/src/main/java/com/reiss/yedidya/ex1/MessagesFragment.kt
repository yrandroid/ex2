package com.reiss.yedidya.ex1


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.message_item.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yedidya on 30/04/2018.
 */

class MessagesFragment : Fragment() {

    private var messages = ArrayList<Message>()
    private val user = User("Me - The only user here")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        btnSend.setOnClickListener { sendMessagePressed() }
        refresh()
    }


    private fun sendMessagePressed() {
        val t = txtMessage.text.trim().toString()
        if (t.isEmpty()) {
            //TODO: present an alert
            toast("Please insert a message before sending")
            return
        }

        toast(t)
        addMessage(t)
    }

    fun presentMessageDetails(msg: Message) {

        val tr = fragmentManager.beginTransaction()

        val fr = MessageFragment.fragmentWith(object:MessageFragmentListener{

            override fun messageFragmentOnMessageDeleted(fragment: MessageFragment, message: Message) {
                messages.remove(message)
                refresh()
            }

        }, msg)
        tr.add(activity.container.id ,fr,null)
        tr.addToBackStack(null)
        tr.commit()

    }

    private fun addMessage(text: String) {
        val msg = Message(text, user, Date())
        messages.add(msg)
        refresh()
    }


    private fun dataSource(): ArrayList<Message> {
        val empty = Message("No data yet", User(""), Date())
        val emptyList = ArrayList<Message>()
        emptyList.add(empty)
        return if (messages.isNotEmpty()) messages else emptyList
    }

    private fun refresh() {
        if (messages.count() > 1) {
            list.adapter.notifyDataSetChanged()
        } else {
            val adapter = ListAdapter(dataSource())
            adapter.onCellClick = fun (position:Int) { if (messages.count() > 0) presentMessageDetails(messages[position]) }
            list.adapter =  adapter
            list.invalidate()
        }
    }
}


class Message(val message: String, val user: User, val created: Date)
class User(val name: String)




class ListAdapter(val messages: ArrayList<Message>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var onCellLongClick: ((position: Int) -> Unit)? = null
    var onCellClick: ((position: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val m = messages[position]

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")

        holder?.itemView?.txtDate?.text = formatter.format(m.created)
        holder?.itemView?.txtMessage?.text = m.message
        holder?.itemView?.txtUser?.text = m.user.name

        holder?.itemView?.setOnClickListener { onCellClick?.invoke(position) }
//        holder?.itemView?.setOnLongClickListener { onCellLongClick?.invoke(position) ; return true }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.message_item, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}



