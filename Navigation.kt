package com.example.addressbook

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel,
               colorViewModel: ColorViewModel){

    NavHost(navController =navController as NavHostController, startDestination =Screen.HomeScreen.route ) {
        composable(Screen.HomeScreen.route) {

            HomeView(onAddClicked = { navController.navigate(Screen.AddScreen.route) },
                onViewClicked = { navController.navigate(Screen.SearchScreen.route) })
        }

        composable(Screen.AddScreen.route) {
            AddScreen(viewModel = viewModel,
                onAddClicked = { navController.navigate(Screen.HomeScreen.route) },
                onCancelClicked = { navController.navigate(Screen.HomeScreen.route) }
                , onAddScreenClicked = {navController.navigate(Screen.AddScreen.route)}
                , onSearchScreenClicked = {navController.navigate(Screen.SearchScreen.route)},
                onHomeScreenClicked = {navController.navigate(Screen.HomeScreen.route)},
                companyDetails = null)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(viewModel = viewModel,
                onSearchClicked = {
//                                  result,city->
                                  company, partyType, city, state ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("company",company)
                    navController.currentBackStackEntry?.savedStateHandle?.set("partyType",partyType)
                    navController.currentBackStackEntry?.savedStateHandle?.set("state",state)
                    navController.currentBackStackEntry?.savedStateHandle?.set("city",city)
//                    navController.currentBackStackEntry?.savedStateHandle?.set("result",result)
                    navController.navigate(Screen.ResultScreen.route)
                },
                onSearchScreenClicked = {navController.navigate(Screen.SearchScreen.route)},
                onAddClicked = {navController.navigate(Screen.AddScreen.route)},
                onHomeClicked = {navController.navigate(Screen.HomeScreen.route)},
                navController = navController
            )
        }

        composable(Screen.ResultScreen.route) {
//            val result = navController.previousBackStackEntry?.savedStateHandle?.get<List<CompanyDetails>>("result") ?: emptyList<CompanyDetails>()
            val city = navController.previousBackStackEntry?.savedStateHandle?.get<String>("city") ?: ""
            val company = navController.previousBackStackEntry?.savedStateHandle?.get<String>("company") ?: ""
            val state = navController.previousBackStackEntry?.savedStateHandle?.get<String>("state") ?: ""
            val partyType = navController.previousBackStackEntry?.savedStateHandle?.get<String>("partyType") ?: ""

            ResultScreen(viewModel,
                city=city,
                company=company,state=state,partyType = partyType,
//                result=result,
                onItemClicked = {companyDetails ->
                navController.currentBackStackEntry?.savedStateHandle?.set("companyDetails",companyDetails)
                    navController.navigate(Screen.ItemScreen.route)
            },
                noResultFound = {navController.navigate(Screen.SearchScreen.route)},
                onSearchClicked = {navController.navigate(Screen.SearchScreen.route)},
                onAddClicked = {navController.navigate(Screen.AddScreen.route)},
                onHomeClicked = {navController.navigate(Screen.HomeScreen.route)})
        }
        
        composable(Screen.ItemScreen.route){
            val companyDetails = navController.previousBackStackEntry?.savedStateHandle?.get<CompanyDetails>("companyDetails")?: 
            CompanyDetails("", emptyList(),"", emptyList<AddressDetails>(), emptyList(), emptyList(),"","","","",
                emptyList(),"")
            ItemScreen(companyName = companyDetails.companyName,viewModel,
                onEditClicked ={companyDetails ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("companyDetails",companyDetails)
                    navController.navigate(Screen.EditScreen.route)
                },
                companyNotFound = {navController.navigate(Screen.ResultScreen.route)},
                onDeleteClick = { navController.navigate(Screen.ResultScreen.route)},
                colorViewModel,)
        }

        composable(Screen.EditScreen.route){
            val companyDetails = navController.previousBackStackEntry?.savedStateHandle?.get<CompanyDetails>("companyDetails")?:
            CompanyDetails("", emptyList(),"", emptyList<AddressDetails>(), emptyList(), emptyList(),"","","","",
                emptyList(),"")
            AddScreen(viewModel = viewModel,
                onAddClicked = { navController.navigateUp() },
                onCancelClicked = { navController.navigateUp() },
                onHomeScreenClicked = {navController.navigate(Screen.HomeScreen.route)},
                onAddScreenClicked = {navController.navigate(Screen.AddScreen.route)},
                onSearchScreenClicked = {navController.navigate(Screen.SearchScreen.route)},
                companyDetails=companyDetails)
        }
    }
}