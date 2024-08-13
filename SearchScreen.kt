package com.example.addressbook

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchScreen(viewModel: MainViewModel,
//                 onSearchClicked:(List<CompanyDetails>,String)->Unit,
                 onSearchClicked:(company:String,partyType:String,city:String,state:String)->Unit,
                 navController: NavController,
                 onAddClicked:()->Unit,
                 onHomeClicked:()->Unit,
                 onSearchScreenClicked:()->Unit){
    var companyName by  remember{ mutableStateOf("") }
    var partyType by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    var companyNameDropDownMenu by remember { mutableStateOf(false) }
    var partyTypeDropDown by remember { mutableStateOf(false) }
    var cityDropDown by remember { mutableStateOf(false) }
    var stateDropDown by remember { mutableStateOf(false) }

    val scrollState =  rememberScrollState()

    val focusRequester1 = remember{FocusRequester()}
    val focusRequester2 = remember{FocusRequester()}
    val focusRequester3 = remember{FocusRequester()}
    val focusRequester4 = remember{FocusRequester()}
    val focusRequester5 = remember{FocusRequester()}

    val context = LocalContext.current

    Column(modifier= Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        Column(modifier=Modifier.weight(1f).padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = "Search", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = "Address Book", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

            //Company name Search field
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("Company Name", modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp)

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester1),
                    label = { Text(text = "name") }, value = companyName,
                    onValueChange = {
                        companyNameDropDownMenu = true
                        companyName = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            companyNameDropDownMenu =
                                false // to close the dropdown menu if its open before moving to the next filed
                            focusRequester2.requestFocus()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            companyNameDropDownMenu = !companyNameDropDownMenu
                        }) {
                            if (!companyNameDropDownMenu)
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            else
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowdropup),
                                    contentDescription = null
                                )
                        }
                    }
                )
                //for displaying the existing company Names
                if (companyNameDropDownMenu) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 150.dp)
                            .verticalScroll(scrollState)
                            .padding(8.dp)
                    ) {
                        viewModel.filterCompanyName(companyName).forEach {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clickable {
                                    companyName = it
                                    companyNameDropDownMenu = false
                                })
                            {
                                Text(it)
                            }
                            Divider()
                        }
                    }
                }
            }

            //Party Type Search field
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("Party Type", modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp)

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester2),
                    label = { Text(text = "type") }, value = partyType,
                    onValueChange = {
                        partyTypeDropDown = true
                        partyType = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            partyTypeDropDown =
                                false // to close the dropdown menu if its open before moving to the next filed
                            focusRequester3.requestFocus()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = { partyTypeDropDown = !partyTypeDropDown }) {
                            if (!partyTypeDropDown)
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            else
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowdropup),
                                    contentDescription = null
                                )
                        }
                    }
                )
                //for displaying the existing party types
                if (partyTypeDropDown) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 150.dp)
                            .verticalScroll(scrollState)
                            .padding(8.dp)
                    ) {
                        viewModel.filterPartyType(partyType).forEach {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clickable {
                                    partyType = it
                                    partyTypeDropDown = false
                                })
                            {
                                Text(it)
                            }
                            Divider()
                        }
                    }
                }

            }

            //city Search Field
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("City", modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp)

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester3),
                    label = { Text(text = "city") }, value = city,
                    onValueChange = {
                        cityDropDown = true
                        city = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            cityDropDown =
                                false // to close the dropdown menu if its open before moving to the next filed
                            focusRequester4.requestFocus()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = { cityDropDown = !cityDropDown }) {
                            if (!cityDropDown)
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            else
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowdropup),
                                    contentDescription = null
                                )
                        }
                    }
                )
                //for displaying the existing party types
                if (cityDropDown) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 150.dp)
                            .verticalScroll(scrollState)
                            .padding(8.dp)
                    ) {
                        viewModel.filterCity(city).forEach {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clickable {
                                    city = it
                                    cityDropDown = false
                                })
                            {
                                Text(it)
                            }
                            Divider()
                        }
                    }
                }
            }

            //state Search Field
            Column(modifier = Modifier.fillMaxWidth()) {

                Text("State", modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp)

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester4),
                    label = { Text(text = "State") }, value = state,
                    onValueChange = {
                        stateDropDown = true
                        state = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            stateDropDown =
                                false // to close the dropdown menu if its open before moving to the next filed
                            focusRequester5.requestFocus()
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = { stateDropDown = !stateDropDown }) {
                            if (!stateDropDown)
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            else
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowdropup),
                                    contentDescription = null
                                )
                        }
                    }
                )
                //for displaying the existing party types
                if (stateDropDown) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 0.dp, max = 150.dp)
                            .verticalScroll(scrollState)
                            .padding(8.dp)
                    ) {
                        viewModel.filterState(state).forEach {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                                .clickable {
                                    state = it
                                    stateDropDown = false
                                })
                            {
                                Text(it)
                            }
                            Divider()
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(24.dp))
            //Search Button
            Button(
                onClick =
                {
                val result = viewModel.getFilteredResults(companyName.trim(),partyType.trim(),city.trim(),state.trim())
//                if(result.isEmpty())
//                    Toast.makeText(context,"No entry Found",Toast.LENGTH_SHORT).show()
//                else
//                    onSearchClicked(result,city)
//              else
                  onSearchClicked(companyName.trim(), partyType.trim(), city.trim(), state.trim())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .focusRequester(focusRequester5)
            ) {
                Text("Search")
            }
        }

        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color.Black).padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            //Search Button
            IconButton(onClick = { onSearchScreenClicked() }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription =null,
                    tint=Color.Green
                    ,modifier=Modifier.size(30.dp))
            }

            //Home button
            IconButton(onClick = { onHomeClicked() }) {
                Icon(imageVector = Icons.Default.Home,
                    contentDescription =null,
                    tint=Color.White,
                    modifier=Modifier.size(30.dp))
            }

            //Add Button
            IconButton(onClick = {onAddClicked()}) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription =null,
                    tint=Color.White,
                    modifier=Modifier.size(30.dp))
            }

        }

    }
}

//@Preview
//@Composable
//fun SearchScreenPreview(){
//    SearchScreen(viewModel = viewModel())
//}