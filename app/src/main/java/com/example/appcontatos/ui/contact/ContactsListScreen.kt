package  com.example.appcontatos.ui.contact

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.SurfaceCoroutineScope
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.appcontatos.R
import com.example.appcontatos.data.Contact
import com.example.appcontatos.ui.theme.AppContatosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope()) {
    val isInitialComposition : MutableState<Boolean> = rememberSaveable { mutableStateOf(true) }
    val isLoading : MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    val hasError : MutableState<Boolean> = rememberSaveable { mutableStateOf( false)}
    val contacts: MutableState<List<Contact>> = remember{ mutableStateOf(generateContacts()) }

    val loadContacts: () -> Unit = {
        isLoading.value = true
        hasError.value = false

        coroutineScope.launch {
            delay(2000)
             hasError.value = Random.nextBoolean()
            if(!hasError.value){
                val isEmpty = Random.nextBoolean()
                if (isEmpty){
                    contacts.value = listOf()
                }else{
                    contacts.value = generateContacts()
                }
                    contacts.value = generateContacts()
            }
            isLoading.value = false
            hasError.value = false
        }
    }
    
    if(isInitialComposition.value){
        loadContacts()
        isInitialComposition.value = false
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { AppBar(
            onRefreshPressed = loadContacts
        ) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                contacts.value = contacts.value.plus(Contact(firstName = "Teste", lastName = "Teste"))
            }) {
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
        if (isLoading.value){
            LoadingContent()
        }else if (hasError.value){
            ErrorContent(
                modifier = defaultModifier,
                onTryAgainPressed = {}
            )
        }else if(contacts.value.isEmpty()){
            EmptyList()
        }else{
            List(
                contacts = contacts.value
            )
        }
    }

}

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

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(60.dp)

        )
        Spacer(Modifier.size(8.dp))
        Text(
            text= stringResource(R.string.loading_contacts),
            style = MaterialTheme.typography.titleLarge.copy(
                color =  Color.Blue
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
fun LoadingContentPreview() {
    AppContatosTheme {
        LoadingContent()
    }
}

@Composable
fun ErrorContent(
    modifier: Modifier = Modifier,
    onTryAgainPressed: ()-> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val defaultColor = MaterialTheme.colorScheme.primary
        Icon(
            imageVector = Icons.Filled.CloudOff,
            contentDescription = stringResource(R.string.loading_error),
            tint = MaterialTheme.colorScheme.primary,
            modifier =  Modifier.size(80.dp)
        )
        val textPadding = PaddingValues(top = 8.dp, start = 8.dp,end=8.dp)
        Text(
            modifier = Modifier.padding( textPadding),
            text = stringResource(R.string.loading_error),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(textPadding),
            text = stringResource(R.string.wait_and_try_again),
            style = MaterialTheme.typography.titleSmall,
            color = defaultColor
        )
        ElevatedButton(
            onClick = onTryAgainPressed ,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.try_again))
        }


    }
}

@Preview(showBackground = true, heightDp = 400)
@Composable
fun ErrorContentPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        ErrorContent(
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


@Composable
private fun List (
    modifier: Modifier = Modifier,
    contacts: List<Contact>
) {

    LazyColumn(
        modifier = modifier.verticalScroll(rememberScrollState())
    ){
        items(contacts){ contact ->
            ContactListItem(contact = contact)
        }

    }
}

@Composable
private fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact
) {
    val isFavorite : MutableState<Boolean> =
        rememberSaveable {
            mutableStateOf(contact.isFavorite)
        }
    ListItem(
        modifier = modifier,
        headlineContent = {Text(contact.fullName)},
        leadingContent = {},
        trailingContent = {
            IconButton(onClick = {
                isFavorite.value = !isFavorite.value
            }) {
                Icon(imageVector = if(isFavorite.value){
                    Icons.Filled.Favorite
                }else{
                    Icons.Filled.FavoriteBorder
                }, contentDescription = "Favoritar",
                    tint = if (isFavorite.value){
                        Color.Red
                    }else{
                        LocalContentColor.current
                    }

                )

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ListPreview(modifier: Modifier = Modifier) {
    AppContatosTheme {
        List(
            contacts = generateContacts()
        )
    }
}

private fun generateContacts():List<Contact>{
    val firstNames = listOf("Joao","José","Everton","Marcos","Andre")
    val lastNames = listOf("Do carmo","Oliveira","Dos Santos","Da Silva","Brasil")

    val contacts: MutableList<Contact> = mutableListOf()
    for (i in 0..19) {
        var generatedNewContact = false
        while (!generatedNewContact) {

            val firsNameIndex = Random.nextInt(firstNames.size)
            val lastNameIndex = Random.nextInt(lastNames.size)
            val newContact = Contact(
                id = i + 1,
                firstName = firstNames[firsNameIndex],
                lastName = lastNames[lastNameIndex]
            )
            if (!contacts.any { it.fullName == newContact.fullName }) {
                contacts.add(newContact)
                generatedNewContact = true
            }
        }
    }
    return contacts
}