package com.mx.beerairb.ui.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray
import com.mx.beerairb.ui.theme.GreenLight

data class ChatPreview(
    val id: String,
    val name: String,
    val avatar: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
    val type: ChatType
)

enum class ChatType { PERSON, BREWERY, SPOT }

val chatPreviews = listOf(
    ChatPreview("1", "Carlos Mendoza", "\uD83D\uDC68", "Claro, te espero el sábado en la cervecería", "5 min", 2, ChatType.PERSON),
    ChatPreview("2", "Cervecería Chapultepec", "\uD83C\uDF7A", "Tu reservación para el recorrido del viernes está confirmada", "1 h", 0, ChatType.BREWERY),
    ChatPreview("3", "Barrel & Hops Tasting", "\uD83C\uDF7B", "Nuevos estilos disponibles esta semana: IPA Tropical y Stout de Café", "3 h", 1, ChatType.BREWERY),
    ChatPreview("4", "Ana Torres", "\uD83D\uDC69", "¡La cata fue increíble! Recomiendo mucho la ruta", "1 d", 0, ChatType.PERSON),
    ChatPreview("5", "Hop Farm Valley", "\uD83C\uDF3E", "Próxima cosecha de lúpulo: 15 de agosto. ¡Reserva tu visita!", "2 d", 3, ChatType.SPOT),
    ChatPreview("6", "La Cerveza del Valle", "\uD83C\uDF7A", "Menú de maridaje actualizado para la temporada de lluvias", "3 d", 0, ChatType.SPOT)
)

@Composable
fun MessagesScreen(
    onChatClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
    ) {
        Text(
            text = "Mensajes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chatPreviews, key = { it.id }) { chat ->
                ChatItem(chat = chat, onClick = { onChatClick(chat.id) })
            }
        }
    }
}

@Composable
private fun ChatItem(chat: ChatPreview, onClick: () -> Unit) {
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
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = CircleShape,
                color = typeColor.copy(alpha = 0.3f)
            ) {
                Box(modifier = Modifier.size(52.dp), contentAlignment = Alignment.Center) {
                    Text(chat.avatar, style = MaterialTheme.typography.titleLarge)
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
                        chat.name, style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold, maxLines = 1,
                        overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f)
                    )
                    Text(chat.timestamp, style = MaterialTheme.typography.labelSmall, color = ClayGray)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        chat.lastMessage, style = MaterialTheme.typography.bodySmall,
                        color = ClayGray, maxLines = 1, overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    if (chat.type != ChatType.PERSON) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(shape = RoundedCornerShape(6.dp), color = typeColor.copy(alpha = 0.4f)) {
                            Text(
                                typeLabel, style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                if (chat.unreadCount > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(shape = CircleShape, color = AmberPrimary) {
                        Text(
                            chat.unreadCount.toString(), style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
