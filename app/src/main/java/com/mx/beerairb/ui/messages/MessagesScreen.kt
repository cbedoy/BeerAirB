package com.mx.beerairb.ui.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray
import com.mx.beerairb.ui.theme.GreenLight

private data class ChatPreview(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
    val type: ChatType
)

private enum class ChatType { PERSON, BREWERY, SPOT }

private val chats = listOf(
    ChatPreview(
        id = "1",
        name = "Carlos Mendoza",
        avatar = "\uD83D\uDC68",
        lastMessage = "Claro, te espero el sábado en la cervecería",
        timestamp = "5 min",
        unreadCount = 2,
        type = ChatType.PERSON
    ),
    ChatPreview(
        id = "2",
        name = "Cervecería Chapultepec",
        avatar = "\uD83C\uDF7A",
        lastMessage = "Tu reservación para el recorrido del viernes está confirmada",
        timestamp = "1 h",
        unreadCount = 0,
        type = ChatType.BREWERY
    ),
    ChatPreview(
        id = "3",
        name = "Barrel & Hops Tasting",
        avatar = "\uD83C\uDF7B",
        lastMessage = "Nuevos estilos disponibles esta semana: IPA Tropical y Stout de Café",
        timestamp = "3 h",
        unreadCount = 1,
        type = ChatType.BREWERY
    ),
    ChatPreview(
        id = "4",
        name = "Ana Torres",
        avatar = "\uD83D\uDC69",
        lastMessage = "¡La cata fue increíble! Recomiendo mucho la ruta",
        timestamp = "1 d",
        unreadCount = 0,
        type = ChatType.PERSON
    ),
    ChatPreview(
        id = "5",
        name = "Hop Farm Valley",
        avatar = "\uD83C\uDF3E",
        lastMessage = "Próxima cosecha de lúpulo: 15 de agosto. ¡Reserva tu visita!",
        timestamp = "2 d",
        unreadCount = 3,
        type = ChatType.SPOT
    ),
    ChatPreview(
        id = "6",
        name = "La Cerveza del Valle",
        avatar = "\uD83C\uDF7A",
        lastMessage = "Menú de maridaje actualizado para la temporada de lluvias",
        timestamp = "3 d",
        unreadCount = 0,
        type = ChatType.SPOT
    )
)

@Composable
fun MessagesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Mensajes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chats, key = { it.id }) { chat ->
                ChatItem(chat = chat)
            }
        }
    }
}

@Composable
private fun ChatItem(chat: ChatPreview) {
    val typeLabel = when (chat.type) {
        ChatType.PERSON -> "Viajero"
        ChatType.BREWERY -> "Cervecería"
        ChatType.SPOT -> "Spot"
    }
    val typeColor = when (chat.type) {
        ChatType.PERSON -> AmberPrimary
        ChatType.BREWERY -> GreenLight
        ChatType.SPOT -> Color(0xFFFEF9E7)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = CircleShape,
                color = typeColor.copy(alpha = 0.3f)
            ) {
                Box(
                    modifier = Modifier.size(52.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.avatar,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = chat.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = ClayGray
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.lastMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = ClayGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    if (chat.type != ChatType.PERSON) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = typeColor.copy(alpha = 0.4f)
                        ) {
                            Text(
                                text = typeLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                if (chat.unreadCount > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = CircleShape,
                        color = AmberPrimary
                    ) {
                        Text(
                            text = chat.unreadCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
