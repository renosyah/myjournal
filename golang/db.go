package main

import "fmt"
import "gopkg.in/mgo.v2"
import "gopkg.in/mgo.v2/bson"

const dbName  = "catatanKeuangan"
const MyJournalDataCollection  = "MyJournalDataCollection"
const MyData  = "MyUserDataCollection"

func connect() *mgo.Session {
	var session, err = mgo.Dial("localhost")
	if err != nil {
		fmt.Println("gagal terhubung ke database")
	}
	return session
}

func (dbjurnal *MyJournalData) GetAllMyJournalData(db *mgo.Session) ([]MyJournalData,error){
	var collection = db.DB(dbName).C(MyJournalDataCollection)
	var data []MyJournalData

	err := collection.Find(nil).All(&data)
	if err != nil {
		return data,err
	}
	return data,nil
}

func (dbjurnal *MyJournalData) GetOneMyJournalData(db *mgo.Session) (*MyJournalData,error){
	var collection = db.DB(dbName).C(MyJournalDataCollection)
	var data *MyJournalData
	var find = bson.M{"UserDataObj.Id":dbjurnal.UserDataObj.Id}

	err := collection.Find(find).One(&data)
	if err != nil {
		return data,err
	}
	return data,nil
}

func (dbjurnal *MyJournalData) UpdateMyJournalData(db *mgo.Session) (error){
	var collection = db.DB(dbName).C(MyJournalDataCollection)
	var find = bson.M{"UserDataObj.Id":dbjurnal.UserDataObj.Id}

	errDelete := collection.Remove(find)
	if errDelete != nil {
		return errDelete
	}

	err := collection.Insert(dbjurnal)
	if err != nil {
		return err
	}
	return nil
}

func (account *UserDataAccount) Validasi(db *mgo.Session)(*UserDataAccount,error){
	var id *UserDataAccount
	var collection = db.DB(dbName).C(MyData)
	var find = bson.M{"Username":account.Username,"Password":account.Password}

	err := collection.Find(find).One(&id)
	if err != nil {
		return id,err
	}
	return id,nil
}

func (register *RegisterData) RegisterNewAccount(db *mgo.Session)(string,error){

	var collection_account = db.DB(dbName).C(MyData)
	var collection = db.DB(dbName).C(MyJournalDataCollection)

	err := collection_account.Insert(register.UserDataAccountObj)
	if err!= nil {
		return "",err
	}

	err2 := collection.Insert(MyJournalData{UserDataObj:register.UserDataObj,CatatanObjs:nil,AppSettingObj:&AppSettings{StatusLoggin:"Login",Lang:"INDONESIA"}})
	if err2 != nil{
		return "",err
	}

	return register.UserDataAccountObj.Id,nil
}