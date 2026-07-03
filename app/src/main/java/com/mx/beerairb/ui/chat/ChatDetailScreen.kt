package com.mx.beerairb.ui.chat

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mx.beerairb.ui.messages.ChatType
import com.mx.beerairb.ui.messages.chatPreviews
import com.mx.beerairb.ui.theme.AmberPrimary
import com.mx.beerairb.ui.theme.ClayGray
import com.mx.beerairb.ui.theme.CreamBg
import com.mx.beerairb.ui.theme.GreenLight

private data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean
)

private val mockMessages = listOf(
    ChatMessage("m1", "¡Hola! ¿Tienen disponibilidad para el tour de esta semana?", "10:30", true),
    ChatMessage("m2", "¡Claro! Tenemos espacios el viernes y sábado. ¿Qué día prefieres?", "10:32", false),
    ChatMessage("m3", "El sábado me queda perfecto. ¿A qué hora es?", "10:33", true),
    ChatMessage("m4", "Tenemos dos horarios: 11:00 am y 3:00 pm. Ambos incluyen degustación completa.", "10:34", false),
    ChatMessage("m5", "La de las 11 am suena bien. ¿Cuánto dura el recorrido?", "10:35", true),
    ChatMessage("m6", "Aproximadamente 2.5 horas. Incluye visita a la fábrica, sala de fermentación y cata de 5 estilos.", "10:36", false),
    ChatMessage("m7", "Perfecto, aparta el de las 11 am para 2 personas por favor.", "10:37", true),
    ChatMessage("m8", "¡Hecho! Te confirmo la reservación en cuanto procese el pago. Te enviaré los detalles por aquí.", "10:38", false),
    ChatMessage("m9", "Genial, muchas gracias. Allí estaré el sábado.", "10:39", true),
    ChatMessage("m10", "¡Excelente! Te esperamos. Si tienes alguna otra duda, aquí estoy.", "10:40", false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    chatId: String,
    onBackClick: () -> Unit
) {
    val chat = chatPreviews.find { it.id == chatId }
    val chatName = chat?.name ?: "Chat"
    val chatAvatar = chat?.avatar ?: "\uD83D\uDCAC"
    val typeColor = when (chat?.type) {
        ChatType.PERSON -> AmberPrimary
        ChatType.BREWERY -> GreenLight
        ChatType.SPOT -> AmberPrimary.copy(alpha = 0.5f)
        null -> AmberPrimary
    }
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            color = typeColor.copy(alpha = 0.3f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(chatAvatar, style = MaterialTheme.typography.labelLarge)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(chatName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            chat?.let {
                                Text(
                                    when (it.type) {
                                        ChatType.PERSON -> "Viajero cervecero"
                                        ChatType.BREWERY -> "Cervecería"
                                        ChatType.SPOT -> "Spot cervecero"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ClayGray
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBg)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            reverseLayout = false
        ) {
            items(mockMessages, key = { it.id }) { message ->
                ChatBubble(message = message)
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isFromMe) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isFromMe) AmberPrimary else MaterialTheme.colorScheme.surface
    val textColor = if (message.isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val cornerShape = if (message.isFromMe) {
        RoundedCornerShape(16.dp, 4.dp, 16.dp, 16.dp)
    } else {
        RoundedCornerShape(4.dp, 16.dp, 16.dp, 16.dp)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            shape = cornerShape,
            color = bubbleColor,
            tonalElevation = if (!message.isFromMe) 2.dp else 0.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (message.isFromMe) textColor.copy(alpha = 0.6f) else ClayGray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
