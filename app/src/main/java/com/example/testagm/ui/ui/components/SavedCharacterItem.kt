package com.example.testagm.ui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testagm.domain.model.Character

/**
 * Composable que muestra la información de un personaje guardado en una tarjeta.
 * @param character El personaje cuyo nombre y especie se mostrarán en la tarjeta.
 */
@Composable
fun SavedCharacterItem(character: Character) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            if (character.species.isNotBlank()) {
                Text(
                    text = character.species,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
@Preview
fun SavedCharacterItemPreview() {
    val character = Character(
        id = 21,
        name = "Aqua Morty",
        status = "unknown",
        species = "Humanoid",
        type = "Fish-Person",
        gender = "Male",
        image = ""
    )
    SavedCharacterItem(character = character)
}