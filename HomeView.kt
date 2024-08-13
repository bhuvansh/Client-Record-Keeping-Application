package com.example.addressbook

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeView(onAddClicked: () -> Unit,
             onViewClicked:()->Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier=Modifier.fillMaxSize()){
        Column(modifier=Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                "ADDRESS",
                fontSize = 70.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                "BOOK",
                fontSize = 70.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Image(painter = painterResource(id = R.drawable.er_logo), contentDescription =null )

//            Text("ELEGANT", fontSize = 70.sp, fontWeight = FontWeight.ExtraBold)
//            Text("RAYONS", fontSize = 70.sp, fontWeight = FontWeight.ExtraBold)
            Text(
                "Shiv Nagar",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                "Batla Road",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                "Amritsar",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                "143001",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Text("Phone No:-98144 02426", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)){

            //View Button
            Button(onClick={onViewClicked()}
                ,modifier=Modifier.size(height=80.dp, width=150.dp)){
                Text("View",fontSize = 30.sp)
            }

            //Add Button
            Button(onClick = { onAddClicked() },
                modifier=Modifier.size(height=80.dp, width=150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)) {
                Text(text = "Add", fontSize = 30.sp)
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeViewPreview(){
//    HomeView() {}
//}