package com.reiss.yedidya.ex1

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_message.*
import java.text.SimpleDateFormat
import java.util.*




interface MessageFragmentListener {
    fun messageFragmentOnMessageDeleted(fragment: MessageFragment, message: Message)
}

/**
 * A placeholder fragment containing a simple view.
 */
class MessageFragment : Fragment() {

    var message: Message? = null
    var listener: MessageFragmentListener? = null


    companion object {
        fun fragmentWith(listener: MessageFragmentListener, message: Message): MessageFragment {
            val fr = MessageFragment()
            fr.listener = listener
            fr.message = message
            return fr
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }




    private fun refresh() {

        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm")

        txtDate?.text = formatter.format(message!!.created)
        txtUser?.text = message!!.user.name
        txtMessage?.text = message!!.message
        btnDelete?.setOnClickListener { deleteMessage() }
    }

    private fun deleteMessage() {
        listener?.messageFragmentOnMessageDeleted(this,message!!)
        activity.onBackPressed()
    }
}
