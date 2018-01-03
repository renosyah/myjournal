package main

type journal_data struct{}
type register_data struct{}


type MyJournalData struct{
	UserDataObj *UserData `bson:"UserDataObj"`;
	CatatanObjs []CatatanData `bson:"CatatanObjs"`;
	AppSettingObj *AppSettings `bson:"AppSettingObj"`;
}

type UserData struct{
	 Id string `bson:"Id"`
	 Nama string `bson:"Nama"`
	 Email string `bson:"Email"`
	 NomorTelp string `bson:"NomorTelp"`
	 UrlImage string `bson:"UrlImage"`
}

type CatatanData struct{
	 KodeBulan int32 `bson:"KodeBulan"`
	 Tahun int32 `bson:"Tahun"`
	 Bulan string `bson:"Bulan"`
	 DateByMoon string `bson:"DateByMoon"`
	 Detail []DetailCatatanData `bson:"Detail"`
}

type AppSettings struct{
	Lang string `bson:"Lang"`
	StatusLoggin string `bson:"StatusLoggin"`
}


type DetailCatatanData struct{
	KodeCatatan string `bson:"KodeCatatan"`
	Type string `bson:"Type"`
	Tanggal int32 `bson:"Tanggal"`
	Catatan string `bson:"Catatan"`
	JumlahTotal int32 `bson:"JumlahTotal"`

}

type UserDataAccount struct{
	Id string `bson:"Id"`
	Nama string `bson:"Nama"`
	Email string `bson:"Email"`
	NomorTelp string `bson:"NomorTelp"`
	Username string `bson:"Username"`
	Password string `bson:"Password"`
}

type RegisterData struct {
	UserDataObj *UserData
	UserDataAccountObj *UserDataAccount
}
