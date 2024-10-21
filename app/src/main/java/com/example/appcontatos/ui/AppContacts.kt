package com.example.appcontatos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcontatos.ui.contact.details.ContactDetailsScreen
import com.example.appcontatos.ui.contact.list.ContactsListScreen

private object Screens{
    const val CONTACTS_LIST = "constactsList"
    const val CONTACT_DETAILS = "contactDetails"
}

private object Arguments{
    const val CONTACT_ID = "contactId"
}

private object Routes{
    const val CONTACTS_LIST = Screens.CONTACTS_LIST
    const val CONTACT_DETAILS = "${Screens.CONTACT_DETAILS}/{${Arguments.CONTACT_ID}}"
}

@Composable
fun AppContacts(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.CONTACTS_LIST
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        ){
        composable(route = Routes.CONTACTS_LIST){
            ContactsListScreen(
                onAddPressed = {
                },
                onContactPressed = {
                    contact ->
                    navController.navigate("${Screens.CONTACT_DETAILS}/${contact.id}")
                }
            )
            composable(
                route = Routes.CONTACT_DETAILS,
                arguments = listOf(
                    navArgument(name = Arguments.CONTACT_ID){
                        type = NavType.IntType
                    }
                )
            ){  navBackStackEntry ->
                val contactId : Int =navBackStackEntry.arguments?.getInt(Arguments.CONTACT_ID)?: 0
                ContactDetailsScreen(
                    contactId = contactId,
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    onEditPressed = {},
                    onContactDeleted = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}