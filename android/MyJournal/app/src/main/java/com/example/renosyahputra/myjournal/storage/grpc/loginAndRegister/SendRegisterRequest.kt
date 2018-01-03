package com.example.renosyahputra.myjournal.storage.grpc.loginAndRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.renosyahputra.myjournal.loginAndRegister.SplashActivity
import com.example.renosyahputra.myjournal.res.objectData.UserData
import com.example.renosyahputra.myjournal.storage.grpc.UrlAndPort
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage
import com.google.protobuf.ByteString
import io.grpc.ManagedChannelBuilder
import loginAndRegisterData.LoginAndRegister
import java.util.concurrent.TimeUnit


class SendRegisterRequest : AsyncTask<Void,Void,LoginAndRegister.ResponseRegister>{

    var context : Context
    var data : UserData
    var image : ByteString
    val channel = ManagedChannelBuilder.forAddress(UrlAndPort.url, UrlAndPort.port).usePlaintext(true).build()
    lateinit var registerdata : LoginAndRegister.RegisterUserData
    var username : String
    var password : String

    constructor(context: Context, data: UserData, username : String, password : String, image: ByteString) : super() {
        this.context = context
        this.data = data
        this.image = image
        this.username = username
        this.password = password
    }

    override fun onPreExecute() {
        super.onPreExecute()
        registerdata = LoginAndRegister.RegisterUserData.newBuilder()
                .setId("kosong")
                .setNama(data.getNama())
                .setEmail(data.getEmail())
                .setUsername(username)
                .setPassword(password)
                .setNomorTelp(data.getNomorTelp())
                .setUrlImage(image).build()
    }

    override fun doInBackground(vararg p0: Void?): LoginAndRegister.ResponseRegister? {
        var response :LoginAndRegister.ResponseRegister? = null
        try{
            val stub = loginAndRegisterData.loginAndRegisterDataServiceGrpc.newBlockingStub(channel)
            val request = LoginAndRegister.RequestRegister.newBuilder().setData(registerdata).build()
            response = stub.register(request)

        }catch (e : Exception){
            e.printStackTrace()
        }

        return response
    }



    override fun onPostExecute(result: LoginAndRegister.ResponseRegister?) {
        super.onPostExecute(result)
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
        if (result != null){
            if (result.response){
                MyCatatanDataStorage(context).SaveSessionLogin(result.iduser)

                val intent = Intent(context, SplashActivity::class.java)
                intent.putExtra("id_user",result.iduser)
                context.startActivity(intent)
                (context as Activity).finish()
            }else{
                Toast.makeText(context,"register fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

