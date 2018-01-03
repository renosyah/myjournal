package com.example.renosyahputra.myjournal.ui.mainActivityLangText


class MainActivityLangText {

    private lateinit var menuLaporanText: String
    private lateinit var  menuPendapatanText: String
    private lateinit var  menuPengeluaranText: String

    fun getMenuLaporanText(): String {
        return menuLaporanText
    }

    fun setMenuLaporanText(menuLaporanText: String) {
        this.menuLaporanText = menuLaporanText
    }

    fun getMenuPendapatanText(): String {
        return menuPendapatanText
    }

    fun setMenuPendapatanText(menuPendapatanText: String) {
        this.menuPendapatanText = menuPendapatanText
    }

    fun getMenuPengeluaranText(): String {
        return menuPengeluaranText
    }

    fun setMenuPengeluaranText(menuPengeluaranText: String) {
        this.menuPengeluaranText = menuPengeluaranText
    }

    fun MainMenuLang(menuLaporanText: String, menuPendapatanText: String, menuPengeluaranText: String) {
        this.menuLaporanText = menuLaporanText
        this.menuPendapatanText = menuPendapatanText
        this.menuPengeluaranText = menuPengeluaranText
    }
}