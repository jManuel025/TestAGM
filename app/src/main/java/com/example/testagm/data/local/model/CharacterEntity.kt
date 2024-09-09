package com.example.testagm.data.local.model

import com.example.testagm.domain.model.Character
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CharacterEntity : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var status: String = ""
    var species: String = ""
    var type: String = ""
    var gender: String = ""

    fun toDomain(): Character {
        return Character(
            id = this.id.timestamp.toLong(),
            name = this.name,
            status = this.status,
            species = this.species,
            type = this.type,
            gender = this.gender,
            image = ""
        )
    }
}


