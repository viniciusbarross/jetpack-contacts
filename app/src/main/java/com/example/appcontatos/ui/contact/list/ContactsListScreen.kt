package  com.example.appcontatos.ui.contact.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcontatos.R
import com.example.appcontatos.data.Contact
import com.example.appcontatos.data.ContactDatasource
import com.example.appcontatos.data.generateContacts
import com.example.appcontatos.data.groupByInitial
import com.example.appcontatos.ui.theme.AppContatosTheme
import com.example.appcontatos.ui.utils.composables.DefaultErrorContent
import com.example.appcontatos.ui.utils.composables.DefaultLoadingContent
import com.example.appcontatos.ui.utils.composables.FavoriteIconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier,
    onAddPressed :()-> Unit,
    onContactPressed:(Contact)-> Unit,
    viewModel: ContactsListViewModel = viewModel()
) {
    val contentModifier = modifier.fillMaxSize()
    if (viewModel.uiState.isLoading){
        DefaultLoadingContent()
    }else if (viewModel.uiState.hasError){
        DefaultErrorContent(
            modifier = contentModifier,
            onTryAgainPressed = {}
        )
    }else{
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { AppBar(
                onRefreshPressed = viewModel::loadContacts
            ) },
            floatingActionButton = {
                ExtendedFloatingActionButton(onClick = onAddPressed) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Adicionar"
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Novo contato")

                }
            }
        ) { paddingValues ->


        val defaultModifier = Modifier.padding(paddingValues)
         if(viewModel.uiState.contacts.isEmpty()){
            EmptyList(modifier = modifier)
        }else {
             List(
                 modifier = defaultModifier,
                 contacts = viewModel.uiState.contacts,
                 onFavoritePressed = viewModel::toggleFavorite,
                 onContactPressed = onContactPressed
             )
         }
    }


}}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onRefreshPressed: ()-> Unit
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text = stringResource(R.string.contatos)) },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = onRefreshPressed) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }
        }
        
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppContatosTheme {
        AppBar(
            onRefreshPressed = {  }
        )
    }
}


@Preview(showBackground = true, heightDp = 300)
@Composable
fun LoadingContentPreview() {
    AppContatosTheme {
        DefaultLoadingContent()
    }
}



@Preview(showBackground = true, heightDp = 400)
@Composable
fun ErrorContentPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        DefaultErrorContent(
            onTryAgainPressed = {}
        )
    }
}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.no_data),
            contentDescription = stringResource(R.string.no_data)
        )
        Text(
            text = stringResource(R.string.no_data),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text= stringResource(R.string.no_data_hint),
            modifier= Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun EmptyListPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        EmptyList()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun List (
    modifier: Modifier = Modifier,
    contacts: Map<String, List<Contact>>,
    onFavoritePressed: (Contact) -> Unit,
    onContactPressed: (Contact) -> Unit
) {

    LazyColumn(
        modifier = modifier.verticalScroll(rememberScrollState())
    ){
        contacts.forEach { (initial, contacts) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = initial,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            items(contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onFavoritePressed = onFavoritePressed,
                    onContactPressed = onContactPressed,
                )
            }
        }
    }
}

@Composable
private fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    onFavoritePressed: (Contact) -> Unit,
    onContactPressed: (Contact) -> Unit
) {

    ListItem(
        modifier = modifier.clickable { onContactPressed(contact) },
        headlineContent = {Text(contact.fullName)},
        leadingContent = {},
        trailingContent = {
            FavoriteIconButton(
                isFavorite = contact.isFavorite,
                onPressed = {onFavoritePressed(contact)}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ListPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        List(
            contacts = generateContacts().groupByInitial(),
            onFavoritePressed = {},
            onContactPressed = {}
        )
    }
}
