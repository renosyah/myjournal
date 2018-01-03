package com.example.renosyahputra.myjournal.loginAndRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.storage.grpc.loginAndRegister.SendLoginRequest
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage

class Login : AppCompatActivity(),View.OnClickListener {

    lateinit var context : Context
    lateinit var username : TextView
    lateinit var password : TextView
    lateinit var login : Button
    lateinit var register : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        InitiationWidget()
    }

    internal fun InitiationWidget(){
        context = this@Login

        if (MyCatatanDataStorage(context).GetSessionLogin() != ""){

            val intent = Intent(context,SplashActivity::class.java)
            intent.putExtra("id_user",MyCatatanDataStorage(context).GetSessionLogin())
            context.startActivity(intent)
            (context as Activity).finish()
        }

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        login = findViewById(R.id.login_button)
        register = findViewById(R.id.register_button)

        register.setOnClickListener(this)
        login.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == login){
            SendLoginRequest(context,username.text.toString(),password.text.toString()).execute()
            username.text = ""
            password.text = ""

        }else if(p0 == register){

            val register = Intent(context,Register::class.java)
            context.startActivity(register)

        }
    }

}
