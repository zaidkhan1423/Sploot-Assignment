package com.zaid.splootassignment.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zaid.splootassignment.data.model.response.Article
import com.zaid.splootassignment.ui.theme.SplootAssignmentTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onShowSnackBar: suspend (message: String, actionLabel: String?, duration: SnackbarDuration) -> Boolean,
    uiState: HomeScreenUiState,
    onMessageDisplay: () -> Unit,
    onEvent: (HomeScreenUiEvent) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState.snackBarMessage != null) {
            onShowSnackBar(uiState.snackBarMessage, null, SnackbarDuration.Short)
            onMessageDisplay()
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 15.dp),

        ) {

        Text(
            modifier = Modifier
                .widthIn(500.dp)
                .padding(top = 18.dp),
            text = "Top News",
            fontSize = MaterialTheme.typography.displayMedium.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.size(20.dp))

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = {
                onEvent(HomeScreenUiEvent.OnSearchTextChange(it))
                onEvent(HomeScreenUiEvent.OnSearchNews)
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    text = "Search News"
                )
            },
            modifier = Modifier
                .widthIn(500.dp)
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.size(18.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {

            if (uiState.loading) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            items(uiState.article.size) { index ->
                val article = uiState.article[index]!!
                Box(
                    modifier = Modifier
                        .widthIn(500.dp)
                        .height(220.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .clickable {
                            showBottomSheet = true
                            onEvent(HomeScreenUiEvent.OnSelectArticle(article))
                        }
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
//                        model = article.urlToImage ?: "",
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.urlToImage ?: "")
                            .build(),

                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.9f),
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 10.dp, bottom = 10.dp)
                    ) {
                        Text(
                            text = article.title ?: "",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.W600,
                            color = Color.White,
                            lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = article.description ?: "",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.W300,
                            color = Color.LightGray,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }

    if (showBottomSheet && uiState.selectedArticle != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                onEvent(HomeScreenUiEvent.OnSelectArticle(null))
            },
            sheetState = sheetState
        ) {
            uiState.selectedArticle.let { article ->
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()

                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp)),
//                        model = article.urlToImage ?: "",
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.urlToImage ?: "")
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = article.title ?: "No title available",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.W500
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Description:- ${article.description ?: "No description available"}",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.W400,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Content :- ${article.content ?: "No Content available"}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.W300,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Source Id :- ${article.source!!.id ?: "No Source Id available"}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.W300,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Source Name :- ${article.source.name ?: "No Source Name available"}",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.W300,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "By ${article.author ?: "Unknown"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Text(
                        text = "Published on ${article.publishedAt ?: "Unknown date"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    SplootAssignmentTheme {
        HomeScreen(
            onShowSnackBar = { _, _, _ -> false },
            uiState = HomeScreenUiState(),
            onMessageDisplay = {},
            onEvent = {}
        )
    }
}