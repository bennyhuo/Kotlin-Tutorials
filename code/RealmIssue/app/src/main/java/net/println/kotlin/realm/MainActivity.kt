package net.println.kotlin.realm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "KotlinRealm"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add.setOnClickListener {
            Realm.getDefaultInstance().use {
                it.beginTransaction()
                val d = it.createObject(User::class.java, it.where(User::class.java).count())
                d.name = "User ${d.id}"
                it.commitTransaction()
            }

        }

        query.setOnClickListener {
            Realm.getDefaultInstance().use {
                it.where(User::class.java).findAll().map {
                    Log.d(TAG, it.toString())
                }
            }
        }
    }
}
