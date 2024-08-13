package com.example.addressbook

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
//import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ItemScreen(companyName: String,
               viewModel: MainViewModel,
               onEditClicked:(CompanyDetails)->Unit,
               companyNotFound:()->Unit,
               onDeleteClick:()->Unit,
               colorViewModel: ColorViewModel,) {

    val companyDetails: CompanyDetails? =
        viewModel.addressList.value.find { it.companyName == companyName }

    if (companyDetails == null) {
        companyNotFound()
    } else {
        val scrollState = rememberScrollState()
        val mainScrollState = rememberScrollState()

        val context = LocalContext.current

        var headingColor by remember { mutableStateOf(colorViewModel.itemHeadingColor) }
        var subHeadingBgColor by remember { mutableStateOf(colorViewModel.itemSubHeadingBgColor) }
        var subHeadingTextColor by remember { mutableStateOf(colorViewModel.itemSubHeadingTextColor) }
        var valueTextColor by remember { mutableStateOf(colorViewModel.itemValueTextColor) }
        var valueBgColor by remember { mutableStateOf(colorViewModel.itemValueBgColor) }
        var screenColor by remember { mutableStateOf(colorViewModel.itemScreenColor) }

        var showHeadingColorPicker by remember { mutableStateOf(false) }
        var showSubHeadingTextColorPicker by remember { mutableStateOf(false) }
        var showSubHeadingBgColorPicker by remember { mutableStateOf(false) }
        var showValueTextColorPicker by remember { mutableStateOf(false) }
        var showValueBgColorPicker by remember { mutableStateOf(false) }
        var showThemeDialog by remember { mutableStateOf(false) }
        var showScreenColorPicker by remember { mutableStateOf(false) }
        var changeHeadingTextColor by remember { mutableStateOf(false) }
        var colorEnv by remember { mutableStateOf(ColorEnvelope(Color.Black,"",false)) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(mainScrollState)
//                .padding(8.dp)
                .background(screenColor),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(
//                    text= "Hdvfjgjnbgjnbgbgbngikbngbngib   mg bm mnbv n i",
                    text = companyDetails.companyName,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = headingColor,
                    modifier = Modifier.weight(1f),
                    lineHeight = 42.sp
                )

                Row()
                {
                    IconButton(onClick = {
                        val message: String = SendMessage(companyDetails)
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.putExtra(Intent.EXTRA_TEXT, message)

                        intent.type = "text/plain"

                        context.startActivity(Intent.createChooser(intent, "Share to:-"))

                    }, modifier = Modifier.align(Alignment.CenterVertically)) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null,tint=if(screenColor==Color.Black)Color.White else Color.Black)
                    }

                    IconButton(onClick = {showThemeDialog=true}) {
                        Icon(imageVector = Icons.Default.Edit,contentDescription = null,tint = if(screenColor==Color.Black)Color.White else Color.Black)
                    }
                }
            }


//            Spacer(Modifier.height(16.dp))
//
//            Button(
//                onClick = {
////                    changeHeadingTextColor = true
//                    showHeadingColorPicker=true
//                }) {
//                Text("Select Heading Color")
//            }

            Spacer(Modifier.height(16.dp))


            //Party Type
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = " Party Type:",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = subHeadingTextColor,
                            modifier = Modifier.background(subHeadingBgColor)
                        )
                    }

                    Spacer(modifier = Modifier.width(45.dp))
                    Text(
                        text = "${companyDetails.partyType}",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = valueTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Addresses
            Box(
                modifier = Modifier
                    .heightIn(max = 200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(7))
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(valueBgColor)
                ) {
                    LazyColumn() {
                        itemsIndexed(companyDetails.Addresses) { index, address ->
                            Column(Modifier.padding(8.dp)) {
                                Text(
                                    "Address ${index + 1}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = subHeadingTextColor,
                                    modifier = Modifier.background(subHeadingBgColor)
                                )
                                Text(
                                    address.Address,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = valueTextColor
                                )
                                Text(
                                    "${address.City},${address.Pincode}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = valueTextColor
                                )
                                Text(
                                    address.State,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    color = valueTextColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = Color.Black)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            //Phone Numbers
            Card(
                modifier = Modifier
//            .weight(1f)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = " Phone Numbers:",
                        fontSize = 25.sp,
                        modifier = Modifier
//                        .padding(start = 8.dp)
                            .background(subHeadingBgColor),
                        fontWeight = FontWeight.Bold,
                        color = subHeadingTextColor
                    )
                    companyDetails.phoneNumbers.forEach {
                        Text(
                            it,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(),
                            color = valueTextColor,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //owner Names
            Card(
                modifier = Modifier
//            .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = " Owners:",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding()
                            .background(subHeadingBgColor),
                        color = subHeadingTextColor,
                        fontWeight = FontWeight.Bold
                    )
                    companyDetails.ownerName.forEach {
                        Text(
                            it,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(),
                            fontWeight = FontWeight.Bold,
                            color = valueTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //GSTN
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = " GST:",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold, color = subHeadingTextColor,
                            modifier = Modifier.background(subHeadingBgColor)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "${companyDetails.GSTN}",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = valueTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //PAN
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = " PAN:",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = subHeadingTextColor,
                            modifier = Modifier.background(subHeadingBgColor)
                        )
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "${companyDetails.PAN}",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = valueTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //MSME
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(subHeadingBgColor)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = " MSME :",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = subHeadingTextColor
                        )
                    }

                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = "${companyDetails.MSME}",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = valueTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //email id's
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 8.dp)
                ) {
                    Text(
                        text = " Mail Id(s):",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = subHeadingTextColor,
                        modifier = Modifier
                            .padding()
                            .background(subHeadingBgColor)
                    )
                    companyDetails.emails.forEach {
                        Text(
                            it,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(top = 8.dp),
                            fontWeight = FontWeight.Bold,
                            color=valueTextColor
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //websites
            Card(
                modifier = Modifier
//            .weight(1f)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = " Website(s):",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding()
                            .background(subHeadingBgColor),
                        color = subHeadingTextColor,
                        fontWeight = FontWeight.Bold
                    )
                    companyDetails.websites.forEach {
                        Text(it, fontSize = 25.sp, modifier = Modifier.padding(top = 8.dp),color=valueTextColor)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Introduced By
            Card(
                modifier = Modifier
//            .weight(1f)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                ) {
                    Text(
                        text = "Introduced By:",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(subHeadingBgColor),
                        color = subHeadingTextColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${companyDetails.introducedBy}",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = valueTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Remarks
            Card(
                modifier = Modifier
//            .weight(1f)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(20.dp),
                colors = CardDefaults.cardColors(containerColor = valueBgColor)
            )
            {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Remarks:",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp)
                            .background(subHeadingBgColor),
                        fontWeight = FontWeight.Bold,
                        color = subHeadingTextColor
                    )
                    Text(
                        text = companyDetails.remarks,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(8.dp),
                        color = valueTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Edit Button
                Button(onClick = { onEditClicked(companyDetails) }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text("Edit", fontSize = 20.sp)
                    }
                }

                Button(onClick = {
                    viewModel.deleteCompany(companyDetails)
                    Toast.makeText(
                        context,
                        "${companyDetails.companyName} Address Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    onDeleteClick()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    Text("Delete", fontSize = 20.sp)
                }
            }
        }

        if(showThemeDialog){

            AlertDialog(
                title={
                      Row(modifier=Modifier.fillMaxWidth(),
                          horizontalArrangement = Arrangement.Center){
                          Text("Setting", fontWeight = FontWeight.Bold)
                      }
                },
                text={
                     Column(modifier= Modifier
                         .verticalScroll(scrollState)){

                         //Choosing from Pre-set Themes
                         Card(modifier= Modifier
                             .padding(5.dp)
                             .fillMaxWidth(),
                             elevation = CardDefaults.cardElevation(20.dp)){

                             //for selecting from default themes
                             Column(horizontalAlignment = Alignment.CenterHorizontally,
                                 verticalArrangement = Arrangement.Center){
                                 Text("Themes")


                                 //Default Theme
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ){
                                     Text("Default",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Box(
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                             .clickable {
                                                 headingColor = Color.Black
                                                 subHeadingTextColor = Color.White
                                                 subHeadingBgColor = Color.DarkGray
                                                 valueTextColor = Color.Black
                                                 valueBgColor = Color.Gray
                                             }
                                             .border(1.dp, Color.Black, RoundedCornerShape(20))
                                             .clip(RoundedCornerShape(20))
                                     ) {
                                         Row(modifier = Modifier.fillMaxSize()) {
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Gray)
                                                     .fillMaxHeight()
                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Black)
                                                     .fillMaxHeight()
                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.White)
                                                     .fillMaxHeight()
                                             )
                                         }
                                     }
                                 }

                                 //Black and White Theme
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ){
                                     Text("Minimilist",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Box(
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                             .clickable {
                                                 screenColor=Color.Black
                                                 headingColor = Color.White
                                                 subHeadingTextColor = Color.White
                                                 subHeadingBgColor = Color.DarkGray
                                                 valueTextColor = Color.White
                                                 valueBgColor = Color.Black
                                             }
                                             .border(1.dp, Color.Black, RoundedCornerShape(20))
                                             .clip(RoundedCornerShape(20))
                                     ) {
                                         Row(modifier = Modifier.fillMaxSize()) {
//                                             Box(
//                                                 modifier = Modifier
//                                                     .weight(1f)
//                                                     .background(Color.Gray)
//                                                     .fillMaxHeight()
//                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Black)
                                                     .fillMaxHeight()
                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.White)
                                                     .fillMaxHeight()
                                             )
                                         }
                                     }
                                 }

                                 //Tacky Theme
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ){
                                     Text("SpiderMan",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Box(
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                             .clickable {
                                                 headingColor = Color.Black
                                                 subHeadingTextColor = Color.White
                                                 subHeadingBgColor = Color.Red
                                                 valueTextColor = Color.Black
                                                 valueBgColor = Color.Blue
                                             }
                                             .border(1.dp, Color.Black, RoundedCornerShape(20))
                                             .clip(RoundedCornerShape(20))
                                     ) {
                                         Row(modifier = Modifier.fillMaxSize()) {
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Black)
                                                     .fillMaxHeight()
                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Red)
                                                     .fillMaxHeight()
                                             )
                                             Box(
                                                 modifier = Modifier
                                                     .weight(1f)
                                                     .background(Color.Blue)
                                                     .fillMaxHeight()
                                             )
                                         }
                                     }
                                 }
                             }
                         }

                         //for customization
                         Card(modifier= Modifier
                             .padding(5.dp)
                             .fillMaxWidth(),
                             elevation = CardDefaults.cardElevation(20.dp)){
                             Column(modifier=Modifier.fillMaxSize()) {

                                 //for page Background Color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Screen Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showScreenColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = screenColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }

                                 //for company Name heading color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Company Name Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showHeadingColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = headingColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }

                                 //for subHeading Color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Sub-Heading Font Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showSubHeadingTextColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = subHeadingTextColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }

                                 //for sub heading Backround Color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Sub-Heading Highlight Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showSubHeadingBgColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = subHeadingBgColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }

                                 //for value text Color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Value Text Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showValueTextColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = valueTextColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }

                                 //for value Background Color
                                 Row(
                                     modifier = Modifier.fillMaxWidth(),
                                     horizontalArrangement = Arrangement.SpaceBetween,
                                     verticalAlignment = Alignment.CenterVertically
                                 ) {
                                     Text("Card Color",modifier=Modifier.padding(8.dp), fontSize = 15.sp,fontWeight = FontWeight.SemiBold)
                                     Button(
                                         onClick = { showValueBgColorPicker = true },
                                         shape = RoundedCornerShape(20),
                                         border = BorderStroke(1.dp, Color.Black),
                                         colors = ButtonDefaults.buttonColors(containerColor = valueBgColor),
                                         modifier = Modifier
                                             .height(50.dp)
                                             .width(50.dp)
                                             .padding(8.dp)
                                     ) {}
                                 }
                             }
                         }
                     }
                },
                onDismissRequest = {},
                confirmButton = {
                    Row(modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        //Confirm Button
                        Button(onClick = {
                            colorViewModel.itemHeadingColor = headingColor
                            colorViewModel.itemSubHeadingBgColor = subHeadingBgColor
                            colorViewModel.itemSubHeadingTextColor = subHeadingTextColor
                            colorViewModel.itemValueTextColor = valueTextColor
                            colorViewModel.itemValueBgColor = valueBgColor
                            colorViewModel.itemScreenColor = screenColor

                            showThemeDialog = false
                        }) {
                            Text("Save")
                        }

                        //Cancel Button
                        Button(onClick = {
                            headingColor = colorViewModel.itemHeadingColor
                            subHeadingBgColor = colorViewModel.itemSubHeadingBgColor
                            subHeadingTextColor = colorViewModel.itemSubHeadingTextColor
                            valueTextColor = colorViewModel.itemValueTextColor
                            valueBgColor = colorViewModel.itemValueBgColor
                            screenColor = colorViewModel.itemScreenColor

                            showThemeDialog = false
                        }) {
                            Text("Cancel")
                        }
                    }
                })
        }

        if (showHeadingColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showHeadingColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            headingColor = colorEnv.color
                            showHeadingColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showHeadingColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }

        if (showSubHeadingTextColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showHeadingColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            subHeadingTextColor = colorEnv.color
                            showSubHeadingTextColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showSubHeadingTextColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }

        if (showSubHeadingBgColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showSubHeadingBgColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            subHeadingBgColor = colorEnv.color
                            showSubHeadingBgColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showSubHeadingBgColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }

        if (showValueTextColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showValueTextColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            valueTextColor = colorEnv.color
                            showValueTextColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showValueTextColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }

        if (showValueBgColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
//                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showValueBgColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            valueBgColor = colorEnv.color
                            showValueBgColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showValueBgColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }

        if (showValueBgColorPicker) {
            val controller = rememberColorPickerController()

            AlertDialog(
                title={Text("Choose Color")},
                text = {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AlphaTile(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                controller = controller
                            )
                        }
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(10.dp),
                            controller = controller,
                            onColorChanged = {
                                colorEnv = it
//                                Log.d("Color", it.hexCode)
                            }
                        )
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                            tileOddColor = Color.White,
                            tileEvenColor = Color.Black
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                    }
                },
                onDismissRequest = {showScreenColorPicker = false },
                confirmButton = {
                    Row (modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        //Select Button
                        Button(onClick = {
                            screenColor = colorEnv.color
                            showScreenColorPicker=false
                        }) {
                            Text(text = "Select")
                        }

                        //Cancel Button
                        Button(onClick = {
                            showScreenColorPicker=false}) {
                            Text(text = "Cancel")
                        }
                    }
                }
            )
        }


    }
}

fun SendMessage(companyDetails: CompanyDetails): String {
    var result = "company Name:-"
    result +=(companyDetails.companyName+'\n')
    result+="company Type:-${companyDetails.partyType}\n"
    companyDetails.Addresses.forEachIndexed { index, address ->
        result +="Address ${index+1}\n"
        result+="address.Address\n${address.City} ${address.Pincode}\n ${address.State}\n\n"
    }
    result+="Contact Details:-\n"
    companyDetails.phoneNumbers.forEachIndexed { index, number ->
        result+="${number}\n"
    }

    result +="Owners:-\n"
    companyDetails.ownerName.forEachIndexed { index, name ->
        result+="${name}\n"
    }

    result+="GST Number:-${companyDetails.GSTN}\n"
    result+="PAN Number:-${companyDetails.PAN}\n"
    result+="MSME Number:-${companyDetails.MSME}\n"

    result +="Mail ID:-\n"
    companyDetails.emails.forEachIndexed { index, id ->
        result+="${id}\n"
    }

    result +="Company Websites:-\n"
    companyDetails.websites.forEachIndexed { index, website ->
        result+="${website}\n"
    }

    result+="Introduced By:-${companyDetails.introducedBy}\n"


    return result
}


