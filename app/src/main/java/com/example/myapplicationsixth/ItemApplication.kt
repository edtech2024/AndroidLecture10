package com.example.myapplicationsixth

import android.app.Application
import com.example.myapplicationsixth.data.ItemDatabase
import com.example.myapplicationsixth.data.ItemRepository

class ItemApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ItemDatabase.getDatabase(this) }
    val repository by lazy { ItemRepository(database.itemDao()) }
}