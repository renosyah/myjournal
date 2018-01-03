package main

import (
	"strconv"
	"time"
	"math/rand"
)

const alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

func Randid(n int) string {
	b := make([]byte, n)
	for i := range b {
		b[i] = alfabet[rand.Intn(len(alfabet))]
	}
	return strconv.Itoa(time.Now().Nanosecond() +120) + string(b) + strconv.Itoa(time.Now().Nanosecond())
}

