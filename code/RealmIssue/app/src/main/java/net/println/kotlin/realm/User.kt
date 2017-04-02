package net.println.kotlin.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@PoKo
data class User(@PrimaryKey var id: Int, var name: String) : RealmObject()