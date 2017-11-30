package com.egco428.firebasedatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dataReference: DatabaseReference
    lateinit var msgList: MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataReference = FirebaseDatabase.getInstance().getReference("data")
        msgList = mutableListOf()

        saveBtn.setOnClickListener {
            SaveData()
        }

        dataReference.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                if(dataSnapshot!!.exists()){
                    msgList.clear()
                    for(i in dataSnapshot.children){
                        val message = i.getValue(Message::class.java)
                        msgList.add(message!!)
                    }
                val adapter = MessageAdapter(applicationContext, R.layout.messages, msgList)
                    listView.adapter = adapter
                }
            }
        })
    }
    private fun SaveData(){
        val msg = editText.text.toString()
        if(msg.isEmpty()){
            editText.error = "Please Enter a message"
            return
        }

        val messageId = dataReference.push().key
        val messageData = Message(messageId, msg, ratingBar.rating.toInt())
        dataReference.child(messageId).setValue(messageData).addOnCompleteListener {
            Toast.makeText(applicationContext,"Message save successfully",Toast.LENGTH_SHORT).show()
        }
    }
}
