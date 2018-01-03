package main

import djurnal "github.com/renosyah/myjournal/synchronizationCatatan"
import register "github.com/renosyah/myjournal/loginAndRegister"
import "golang.org/x/net/context"
import "google.golang.org/grpc"
import (
	"google.golang.org/grpc/reflection"
	"net"
	"log"
	"fmt"
	"strconv"
	"time"
	"io/ioutil"
	"os"
)

const port  = ":9090"
const MyImageurl  = "http://192.168.23.1:8000/data/foto/"

func (j *journal_data) DataCatatan(ctx context.Context,in *djurnal.RequestDataCatatan) (*djurnal.ResponseDataCatatan,error)   {
	db := connect()
	defer db.Close()

	var data *djurnal.ServerMyJournalData

	var userId = &UserData{Id:in.IdUser,Nama:"",Email:"",NomorTelp:"",UrlImage:""}
	var journalQuerry = &MyJournalData{UserDataObj:userId,CatatanObjs:nil,AppSettingObj:nil}
	var result,err = journalQuerry.GetOneMyJournalData(db)
	if err != nil{
		fmt.Println(err)
		return &djurnal.ResponseDataCatatan{data},err
	}

	var userDataObj *djurnal.ServerUserData

	userDataObj = &djurnal.ServerUserData{
		Id:result.UserDataObj.Id,
		Nama:result.UserDataObj.Nama,
		Email:result.UserDataObj.Email,
		NomorTelp:result.UserDataObj.NomorTelp,
		UrlImage:result.UserDataObj.UrlImage,
	}
	var catatanObjs []*djurnal.ServerCatatanData
	for _,data := range result.CatatanObjs{
		var catatanObj *djurnal.ServerCatatanData

		var detailcatatanObjs []*djurnal.ServerDetailCatatanData
		for _,detaildata := range data.Detail{
			var detailcatatanObj *djurnal.ServerDetailCatatanData
			detailcatatanObj = &djurnal.ServerDetailCatatanData{
				KodeCatatan:detaildata.KodeCatatan,
				Type:detaildata.Type,
				Tanggal:detaildata.Tanggal,
				Catatan:detaildata.Catatan,
				JumlahTotal:detaildata.JumlahTotal,
			}
			detailcatatanObjs = append(detailcatatanObjs,detailcatatanObj)
		}
		catatanObj = &djurnal.ServerCatatanData{
			KodeBulan:data.KodeBulan,
			Tahun:data.Tahun,
			Bulan:data.Bulan,
			Detail:detailcatatanObjs,
		}
		catatanObjs = append(catatanObjs,catatanObj)
	}
	var appSettingObj *djurnal.ServerAppSettings
	appSettingObj = &djurnal.ServerAppSettings{
		Lang:result.AppSettingObj.Lang,
		StatusLoggin:result.AppSettingObj.StatusLoggin,
	}


	data = &djurnal.ServerMyJournalData{
		UserDataObj:userDataObj,
		CatatanObjs:catatanObjs,
		AppSettingObj:appSettingObj,
	}

	return &djurnal.ResponseDataCatatan{data},nil
}

func (j *journal_data) UpdateDataCatatan(ctx context.Context,in *djurnal.RequestUpdateDataCatatan) (*djurnal.ResponseUpdateDataCatatan,error)   {
	db := connect()
	defer db.Close()

	var userDataObj = &UserData{
		Id:in.Data.UserDataObj.Id,
		Nama:in.Data.UserDataObj.Nama,
		Email:in.Data.UserDataObj.Email,
		NomorTelp:in.Data.UserDataObj.NomorTelp,
		UrlImage:in.Data.UserDataObj.UrlImage,
	}
	var catatanObjs []CatatanData
	for _,data := range in.Data.CatatanObjs{

		var detailcatatandataObjs []DetailCatatanData
		for _,datadetail := range data.Detail{
			var detailcatatandataObj = DetailCatatanData{
				KodeCatatan:datadetail.KodeCatatan,
				Type:datadetail.Type,
				Tanggal:datadetail.Tanggal,
				Catatan:datadetail.Catatan,
				JumlahTotal:datadetail.JumlahTotal,

			}
			detailcatatandataObjs = append(detailcatatandataObjs,detailcatatandataObj)
		}
		var catatanObj = CatatanData{
			KodeBulan:data.KodeBulan,
			Bulan:data.Bulan,
			Tahun:data.Tahun,
			Detail:detailcatatandataObjs,
			DateByMoon:fmt.Sprint(data.KodeBulan)+"-"+fmt.Sprint(data.Tahun),
		}
		catatanObjs = append(catatanObjs,catatanObj)
	}

	var appSettingObj = &AppSettings{
		Lang:in.Data.AppSettingObj.Lang,
		StatusLoggin:in.Data.AppSettingObj.StatusLoggin,
	}

	var newData = &MyJournalData{
		UserDataObj:userDataObj,
		CatatanObjs:catatanObjs,
		AppSettingObj:appSettingObj,
	}

	var execution = &MyJournalData{
		UserDataObj:newData.UserDataObj,
		CatatanObjs:newData.CatatanObjs,
		AppSettingObj:newData.AppSettingObj,
	}
	err := execution.UpdateMyJournalData(db)
	if err != nil{
		fmt.Println(err)
	}

	return &djurnal.ResponseUpdateDataCatatan{err == nil},nil
}

func (r *register_data) Login(ctx context.Context,in *register.RequestLogin) (*register.ResponseLogin,error)   {
	db := connect()
	defer db.Close()

	u := &UserDataAccount{
		Username:in.Username,
		Password:in.Password,
		Id:"",
		Nama:"",
		Email:"",
		NomorTelp:"",
	}
	id,err := u.Validasi(db)
	if err != nil {
		fmt.Println(err)
		return &register.ResponseLogin{Iduser:"",Response:false},err
	}

	return &register.ResponseLogin{Iduser:id.Id,Response:true},nil
}

func (r *register_data) Register(ctx context.Context,in *register.RequestRegister) (*register.ResponseRegister,error)   {
	db := connect()
	defer db.Close()

	var id = Randid(20)
	filename  := Randid(10) + strconv.Itoa(time.Now().Nanosecond())
	errcreate := ioutil.WriteFile("data/foto/" + filename + ".jpg",in.Data.UrlImage,os.FileMode(0700))
	if errcreate != nil {
		fmt.Println(errcreate)
		return &register.ResponseRegister{Iduser:"",Response:false},errcreate
	}


	var registerData *RegisterData

	var userDataObj *UserData
	userDataObj = &UserData{
		Id:id,
		Nama:in.Data.Nama,
		Email:in.Data.Email,
		NomorTelp:in.Data.NomorTelp,
		UrlImage:MyImageurl+"data/foto/" + filename + ".jpg",
	}
	var userDataAccountObj *UserDataAccount
	userDataAccountObj = &UserDataAccount{
		Id:id,
		Nama:in.Data.Nama,
		Email:in.Data.Email,
		NomorTelp:in.Data.NomorTelp,
		Username:in.Data.Username,
		Password:in.Data.Password,
	}


	registerData = &RegisterData{
		UserDataObj:userDataObj,
		UserDataAccountObj:userDataAccountObj,
	}

	_,err := registerData.RegisterNewAccount(db)
	if err != nil{
		fmt.Println(err)
		return &register.ResponseRegister{Iduser:"",Response:false},err
	}

	return &register.ResponseRegister{Iduser:id,Response:true},nil
}

func main(){
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	djurnal.RegisterSynchronizationCatatanServiceServer(s,&journal_data{})
	register.RegisterLoginAndRegisterDataServiceServer(s,&register_data{})

	reflection.Register(s)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}