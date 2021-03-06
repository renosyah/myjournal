// Code generated by protoc-gen-go. DO NOT EDIT.
// source: loginAndRegister.proto

/*
Package loginAndRegisterData is a generated protocol buffer package.

It is generated from these files:
	loginAndRegister.proto

It has these top-level messages:
	RequestLogin
	ResponseLogin
	RequestRegister
	ResponseRegister
	RegisterUserData
*/
package loginAndRegisterData

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"

import (
	context "golang.org/x/net/context"
	grpc "google.golang.org/grpc"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

type RequestLogin struct {
	Username string `protobuf:"bytes,1,opt,name=Username" json:"Username,omitempty"`
	Password string `protobuf:"bytes,2,opt,name=Password" json:"Password,omitempty"`
}

func (m *RequestLogin) Reset()                    { *m = RequestLogin{} }
func (m *RequestLogin) String() string            { return proto.CompactTextString(m) }
func (*RequestLogin) ProtoMessage()               {}
func (*RequestLogin) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{0} }

func (m *RequestLogin) GetUsername() string {
	if m != nil {
		return m.Username
	}
	return ""
}

func (m *RequestLogin) GetPassword() string {
	if m != nil {
		return m.Password
	}
	return ""
}

type ResponseLogin struct {
	Iduser   string `protobuf:"bytes,1,opt,name=Iduser" json:"Iduser,omitempty"`
	Response bool   `protobuf:"varint,2,opt,name=Response" json:"Response,omitempty"`
}

func (m *ResponseLogin) Reset()                    { *m = ResponseLogin{} }
func (m *ResponseLogin) String() string            { return proto.CompactTextString(m) }
func (*ResponseLogin) ProtoMessage()               {}
func (*ResponseLogin) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{1} }

func (m *ResponseLogin) GetIduser() string {
	if m != nil {
		return m.Iduser
	}
	return ""
}

func (m *ResponseLogin) GetResponse() bool {
	if m != nil {
		return m.Response
	}
	return false
}

type RequestRegister struct {
	Data *RegisterUserData `protobuf:"bytes,1,opt,name=data" json:"data,omitempty"`
}

func (m *RequestRegister) Reset()                    { *m = RequestRegister{} }
func (m *RequestRegister) String() string            { return proto.CompactTextString(m) }
func (*RequestRegister) ProtoMessage()               {}
func (*RequestRegister) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{2} }

func (m *RequestRegister) GetData() *RegisterUserData {
	if m != nil {
		return m.Data
	}
	return nil
}

type ResponseRegister struct {
	Iduser   string `protobuf:"bytes,1,opt,name=Iduser" json:"Iduser,omitempty"`
	Response bool   `protobuf:"varint,2,opt,name=Response" json:"Response,omitempty"`
}

func (m *ResponseRegister) Reset()                    { *m = ResponseRegister{} }
func (m *ResponseRegister) String() string            { return proto.CompactTextString(m) }
func (*ResponseRegister) ProtoMessage()               {}
func (*ResponseRegister) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{3} }

func (m *ResponseRegister) GetIduser() string {
	if m != nil {
		return m.Iduser
	}
	return ""
}

func (m *ResponseRegister) GetResponse() bool {
	if m != nil {
		return m.Response
	}
	return false
}

type RegisterUserData struct {
	Id        string `protobuf:"bytes,1,opt,name=Id" json:"Id,omitempty"`
	Nama      string `protobuf:"bytes,2,opt,name=Nama" json:"Nama,omitempty"`
	Email     string `protobuf:"bytes,3,opt,name=Email" json:"Email,omitempty"`
	NomorTelp string `protobuf:"bytes,4,opt,name=NomorTelp" json:"NomorTelp,omitempty"`
	UrlImage  []byte `protobuf:"bytes,5,opt,name=UrlImage,proto3" json:"UrlImage,omitempty"`
	Username  string `protobuf:"bytes,6,opt,name=Username" json:"Username,omitempty"`
	Password  string `protobuf:"bytes,7,opt,name=Password" json:"Password,omitempty"`
}

func (m *RegisterUserData) Reset()                    { *m = RegisterUserData{} }
func (m *RegisterUserData) String() string            { return proto.CompactTextString(m) }
func (*RegisterUserData) ProtoMessage()               {}
func (*RegisterUserData) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{4} }

func (m *RegisterUserData) GetId() string {
	if m != nil {
		return m.Id
	}
	return ""
}

func (m *RegisterUserData) GetNama() string {
	if m != nil {
		return m.Nama
	}
	return ""
}

func (m *RegisterUserData) GetEmail() string {
	if m != nil {
		return m.Email
	}
	return ""
}

func (m *RegisterUserData) GetNomorTelp() string {
	if m != nil {
		return m.NomorTelp
	}
	return ""
}

func (m *RegisterUserData) GetUrlImage() []byte {
	if m != nil {
		return m.UrlImage
	}
	return nil
}

func (m *RegisterUserData) GetUsername() string {
	if m != nil {
		return m.Username
	}
	return ""
}

func (m *RegisterUserData) GetPassword() string {
	if m != nil {
		return m.Password
	}
	return ""
}

func init() {
	proto.RegisterType((*RequestLogin)(nil), "loginAndRegisterData.RequestLogin")
	proto.RegisterType((*ResponseLogin)(nil), "loginAndRegisterData.ResponseLogin")
	proto.RegisterType((*RequestRegister)(nil), "loginAndRegisterData.RequestRegister")
	proto.RegisterType((*ResponseRegister)(nil), "loginAndRegisterData.ResponseRegister")
	proto.RegisterType((*RegisterUserData)(nil), "loginAndRegisterData.RegisterUserData")
}

// Reference imports to suppress errors if they are not otherwise used.
var _ context.Context
var _ grpc.ClientConn

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
const _ = grpc.SupportPackageIsVersion4

// Client API for LoginAndRegisterDataService service

type LoginAndRegisterDataServiceClient interface {
	Login(ctx context.Context, in *RequestLogin, opts ...grpc.CallOption) (*ResponseLogin, error)
	Register(ctx context.Context, in *RequestRegister, opts ...grpc.CallOption) (*ResponseRegister, error)
}

type loginAndRegisterDataServiceClient struct {
	cc *grpc.ClientConn
}

func NewLoginAndRegisterDataServiceClient(cc *grpc.ClientConn) LoginAndRegisterDataServiceClient {
	return &loginAndRegisterDataServiceClient{cc}
}

func (c *loginAndRegisterDataServiceClient) Login(ctx context.Context, in *RequestLogin, opts ...grpc.CallOption) (*ResponseLogin, error) {
	out := new(ResponseLogin)
	err := grpc.Invoke(ctx, "/loginAndRegisterData.loginAndRegisterDataService/Login", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

func (c *loginAndRegisterDataServiceClient) Register(ctx context.Context, in *RequestRegister, opts ...grpc.CallOption) (*ResponseRegister, error) {
	out := new(ResponseRegister)
	err := grpc.Invoke(ctx, "/loginAndRegisterData.loginAndRegisterDataService/Register", in, out, c.cc, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// Server API for LoginAndRegisterDataService service

type LoginAndRegisterDataServiceServer interface {
	Login(context.Context, *RequestLogin) (*ResponseLogin, error)
	Register(context.Context, *RequestRegister) (*ResponseRegister, error)
}

func RegisterLoginAndRegisterDataServiceServer(s *grpc.Server, srv LoginAndRegisterDataServiceServer) {
	s.RegisterService(&_LoginAndRegisterDataService_serviceDesc, srv)
}

func _LoginAndRegisterDataService_Login_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(RequestLogin)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(LoginAndRegisterDataServiceServer).Login(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/loginAndRegisterData.loginAndRegisterDataService/Login",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(LoginAndRegisterDataServiceServer).Login(ctx, req.(*RequestLogin))
	}
	return interceptor(ctx, in, info, handler)
}

func _LoginAndRegisterDataService_Register_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc.UnaryServerInterceptor) (interface{}, error) {
	in := new(RequestRegister)
	if err := dec(in); err != nil {
		return nil, err
	}
	if interceptor == nil {
		return srv.(LoginAndRegisterDataServiceServer).Register(ctx, in)
	}
	info := &grpc.UnaryServerInfo{
		Server:     srv,
		FullMethod: "/loginAndRegisterData.loginAndRegisterDataService/Register",
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		return srv.(LoginAndRegisterDataServiceServer).Register(ctx, req.(*RequestRegister))
	}
	return interceptor(ctx, in, info, handler)
}

var _LoginAndRegisterDataService_serviceDesc = grpc.ServiceDesc{
	ServiceName: "loginAndRegisterData.loginAndRegisterDataService",
	HandlerType: (*LoginAndRegisterDataServiceServer)(nil),
	Methods: []grpc.MethodDesc{
		{
			MethodName: "Login",
			Handler:    _LoginAndRegisterDataService_Login_Handler,
		},
		{
			MethodName: "Register",
			Handler:    _LoginAndRegisterDataService_Register_Handler,
		},
	},
	Streams:  []grpc.StreamDesc{},
	Metadata: "loginAndRegister.proto",
}

func init() { proto.RegisterFile("loginAndRegister.proto", fileDescriptor0) }

var fileDescriptor0 = []byte{
	// 328 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x9c, 0x92, 0x4f, 0x4f, 0xc2, 0x30,
	0x18, 0xc6, 0x33, 0x04, 0x84, 0x57, 0xfc, 0x93, 0x86, 0x90, 0x05, 0x3d, 0x90, 0x19, 0x09, 0x27,
	0x0e, 0x78, 0xf3, 0x66, 0x54, 0x92, 0x25, 0x4a, 0x48, 0xd5, 0x83, 0xc7, 0x6a, 0xdf, 0x90, 0x25,
	0xdb, 0x3a, 0xdb, 0xa1, 0xdf, 0xce, 0x0f, 0xe0, 0xa7, 0x32, 0xed, 0xda, 0xe1, 0x0c, 0xec, 0xe0,
	0x6d, 0xcf, 0xf3, 0x76, 0xbf, 0xbd, 0x7b, 0x9e, 0xc2, 0x20, 0x16, 0xab, 0x28, 0xbd, 0x4e, 0x39,
	0xc5, 0x55, 0xa4, 0x72, 0x94, 0xd3, 0x4c, 0x8a, 0x5c, 0x90, 0xfe, 0x5f, 0xff, 0x96, 0xe5, 0x2c,
	0x98, 0x43, 0x8f, 0xe2, 0xfb, 0x1a, 0x55, 0x7e, 0xaf, 0xc7, 0x64, 0x08, 0x9d, 0x67, 0x85, 0x32,
	0x65, 0x09, 0xfa, 0xde, 0xc8, 0x9b, 0x74, 0x69, 0xa9, 0xf5, 0x6c, 0xc9, 0x94, 0xfa, 0x14, 0x92,
	0xfb, 0x8d, 0x62, 0xe6, 0x74, 0x70, 0x03, 0x87, 0x14, 0x55, 0x26, 0x52, 0x85, 0x05, 0x68, 0x00,
	0xed, 0x90, 0xaf, 0x15, 0x4a, 0x8b, 0xb1, 0x4a, 0x43, 0xdc, 0x41, 0x03, 0xe9, 0xd0, 0x52, 0x07,
	0x0f, 0x70, 0x6c, 0x97, 0x71, 0x3b, 0x92, 0x2b, 0x68, 0x72, 0x96, 0x33, 0x03, 0x39, 0x98, 0x8d,
	0xa7, 0xdb, 0x7e, 0x62, 0xea, 0x84, 0xde, 0x54, 0x1b, 0xd4, 0xbc, 0x13, 0xcc, 0xe1, 0xc4, 0xa1,
	0x4b, 0xde, 0x7f, 0xd6, 0xfa, 0xf2, 0x34, 0xa8, 0xfa, 0x09, 0x72, 0x04, 0x8d, 0x90, 0x5b, 0x48,
	0x23, 0xe4, 0x84, 0x40, 0x73, 0xc1, 0x12, 0x66, 0x83, 0x31, 0xcf, 0xa4, 0x0f, 0xad, 0xbb, 0x84,
	0x45, 0xb1, 0xbf, 0x67, 0xcc, 0x42, 0x90, 0x33, 0xe8, 0x2e, 0x44, 0x22, 0xe4, 0x13, 0xc6, 0x99,
	0xdf, 0x34, 0x93, 0x8d, 0x61, 0x0a, 0x90, 0x71, 0x98, 0xb0, 0x15, 0xfa, 0xad, 0x91, 0x37, 0xe9,
	0xd1, 0x52, 0x57, 0xca, 0x69, 0xd7, 0x94, 0xb3, 0x5f, 0x2d, 0x67, 0xf6, 0xed, 0xc1, 0xe9, 0xb6,
	0xe0, 0x1e, 0x51, 0x7e, 0x44, 0x6f, 0x48, 0x96, 0xd0, 0x2a, 0x4a, 0x0b, 0x76, 0xe5, 0xbb, 0xb9,
	0x21, 0xc3, 0xf3, 0x5d, 0x67, 0x7e, 0xb7, 0xff, 0xa2, 0xe3, 0xb4, 0x91, 0x5f, 0xd4, 0x42, 0x9d,
	0x37, 0x1c, 0xd7, 0x73, 0x9d, 0xf9, 0xda, 0x36, 0xd7, 0xf9, 0xf2, 0x27, 0x00, 0x00, 0xff, 0xff,
	0xad, 0x23, 0x0f, 0x5f, 0xe8, 0x02, 0x00, 0x00,
}
