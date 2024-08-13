package com.example.addressbook

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(viewModel:MainViewModel,
//                 result:List<CompanyDetails>,
                 company:String,state:String,partyType:String,
                 city:String,
                 onItemClicked:(CompanyDetails)->Unit,
                 noResultFound:()->Unit,
                 onHomeClicked:()->Unit,
                 onSearchClicked:()->Unit,
                 onAddClicked:()->Unit){

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()
    val context = LocalContext.current

    val result = viewModel.getFilteredResults(company.trim(),partyType.trim(),city.trim(), state.trim())
    if(result.isEmpty())
    {
        Toast.makeText(context,"No Result Found",Toast.LENGTH_SHORT).show()
        noResultFound()
    }
    Column(modifier=Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .scrollable(horizontalScrollState, Orientation.Horizontal)
                .scrollable(verticalScrollState, Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Result", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

            result.forEach {
                ResultItem(it, city, onItemClicked)
            }
        }

        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color.Black).padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            //Search Button
            IconButton(onClick = { onSearchClicked() }) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Search,
                    contentDescription =null,
                    tint= Color.Green
                    ,modifier=Modifier.size(30.dp))
            }

            //Home button
            IconButton(onClick = { onHomeClicked()}) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Home,
                    contentDescription =null,
                    tint= Color.White,
                    modifier=Modifier.size(30.dp))
            }

            //Add Button
            IconButton(onClick = {onAddClicked()}) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Add,
                    contentDescription =null,
                    tint= Color.White,
                    modifier=Modifier.size(30.dp))
            }

        }
    }
}

@Composable
fun ResultItem(companyDetais: CompanyDetails,city:String,
               onItemClicked: (CompanyDetails) -> Unit) {
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .clickable { onItemClicked(companyDetais) }
            .padding(8.dp),
        elevation=20.dp){
        Column {
            Text(companyDetais.companyName,modifier=Modifier.padding(8.dp), fontSize = 20.sp)
            if(city.isNotEmpty()) {
                if(companyDetais.Addresses.size>1)
                    Text("$city (multiple Loc^)", modifier = Modifier.padding(start=8.dp,bottom=8.dp),fontSize=20.sp)
                else
                    Text("$city", modifier = Modifier.padding(start= 8.dp,bottom=8.dp),fontSize= 20.sp)

            }
            else {
                val cit = companyDetais.Addresses[0].City
                if(companyDetais.Addresses.size>1)
                    Text("$cit (multiple Loc^)", modifier = Modifier.padding(start=8.dp,bottom=8.dp), fontSize = 20.sp)
                else
                    Text("$cit", modifier = Modifier.padding(start=8.dp,bottom=8.dp), fontSize = 20.sp)
            }
        }
    }
}
