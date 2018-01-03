package com.example.renosyahputra.myjournal.storage.grpc.loginAndRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.renosyahputra.myjournal.loginAndRegister.SplashActivity
import com.example.renosyahputra.myjournal.storage.grpc.UrlAndPort
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage
import io.grpc.ManagedChannelBuilder
import loginAndRegisterData.LoginAndRegister
import java.util.concurrent.TimeUnit

class SendLoginRequest : AsyncTask<Void,Void,LoginAndRegister.ResponseLogin>{

    var context : Context
    var username : String
    var password : String
    val channel = ManagedChannelBuilder.forAddress(UrlAndPort.url, UrlAndPort.port).usePlaintext(true).build()

    constructor(context: Context, username: String, password: String) : super() {
        this.context = context
        this.username = username
        this.password = password
    }


    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): LoginAndRegister.ResponseLogin? {
        var response : LoginAndRegister.ResponseLogin? = null
        try {
            val stub = loginAndRegisterData.loginAndRegisterDataServiceGrpc.newBlockingStub(channel)
            val request = LoginAndRegister.RequestLogin.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build()
            response = stub.login(request)

        }catch (e: Exception){
            e.printStackTrace()
        }

        return response
    }



    override fun onPostExecute(result: LoginAndRegister.ResponseLogin?) {
        super.onPostExecute(result)
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
        if (result != null){
            if (result.response){

                MyCatatanDataStorage(context).SaveSessionLogin(result.iduser)

                val intent = Intent(context,SplashActivity::class.java)
                intent.putExtra("id_user",result.iduser)
                context.startActivity(intent)
                (context as Activity).finish()
            }else{
                Toast.makeText(context,"login fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

}