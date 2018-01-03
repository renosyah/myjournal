package com.example.renosyahputra.myjournal.res.objectData


class ImageDataObj{
    internal lateinit var name : String
    internal lateinit var url : String

    constructor(name: String, url: String) {
        this.name = name
        this.url = url
    }

    fun Getname() : String{
        return name
    }

    fun Geturl() : String{
        return url
    }

    fun Setname(name : String){
        this.name = name
    }

    fun Seturl(url : String){
        this.url = url
    }
}