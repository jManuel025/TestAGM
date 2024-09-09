package com.example.testagm.ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.testagm.R
import com.example.testagm.domain.model.Character


/**
 * Composable que representa un elemento de la lista de personajes.
 * Muestra los detalles del personaje y un icono para guardar el personaje en una lista de favoritos.
 * @param character El objeto `Character` que contiene la información a mostrar.
 * @param onSaveCharacter Callback que se ejecuta al hacer clic en el icono de guardar.
 */
@Composable
fun CharacterItem(character: Character, onSaveCharacter: () -> Unit) {
    // Determina el color basado en el estado del personaje
    val statusColor = when (character.status) {
        "Alive" -> Color(0xff007f5f)
        "Dead" -> Color(0xffd90429)
        else -> Color(0xff8d99ae)
    }
    // Determina el recurso drawable para el género del personaje
    val genderDrawable = when (character.gender) {
        "Male" -> R.drawable.ic_male
        "Female" -> R.drawable.ic_female
        else -> R.drawable.ic_agender
    }

    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .5f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Imagen del personaje y su ID
            Box {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 5.dp,
                                topEnd = 25.dp,
                                bottomStart = 25.dp,
                                bottomEnd = 5.dp
                            )
                        )
                        .height(150.dp)
                        .width(150.dp)
                )
                Text(
                    text = "#${character.id}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(statusColor)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark_add),
                    contentDescription = "",
                    tint = Color(0xffffb703),
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .padding(8.dp)
                        .align(Alignment.BottomEnd)
                        .clickable { onSaveCharacter() }
                )
            }
            // Detalles adicionales del personaje
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = character.name,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                    Icon(
                        painter = painterResource(id = genderDrawable),
                        contentDescription = character.gender,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Divider(color = MaterialTheme.colorScheme.inverseSurface)
                Text(text = "Species: ${character.species}", fontSize = 14.sp)
                if (character.type.isNotBlank()) {
                    Text(text = "Type: ${character.type}", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
@Preview
fun CharacterItemPreview() {
    val character = Character(
        id = 21,
        name = "Aqua Morty",
        status = "unknown",
        species = "Humanoid",
        type = "Fish-Person",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/21.jpeg"
    )
    CharacterItem(character) {}
}