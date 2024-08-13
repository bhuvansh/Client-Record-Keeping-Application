package com.example.addressbook

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun AddScreen(viewModel: MainViewModel,
              onAddClicked:()->Unit,
              onCancelClicked:()->Unit,
              companyDetails: CompanyDetails? = null,
              onAddScreenClicked:()->Unit,
              onHomeScreenClicked:()->Unit,
              onSearchScreenClicked:()->Unit){

    var companyName by remember{mutableStateOf(companyDetails?.companyName?:"")}
    var ownerNames by remember{ mutableStateOf<List<String>>(companyDetails?.ownerName?:emptyList()) }
    var partyType by remember{ mutableStateOf(companyDetails?.partyType?:"") }
    var addresses by remember{ mutableStateOf<List<AddressDetails>>(companyDetails?.Addresses?:emptyList()) }
    var phoneNumbers by remember { mutableStateOf<List<String>>(companyDetails?.phoneNumbers?:emptyList()) }
    var emails by remember{ mutableStateOf<List<String>>(companyDetails?.emails?:emptyList()) }
    var introducedBy by remember{ mutableStateOf(companyDetails?.introducedBy?:"") }
    var GSTN by remember{ mutableStateOf(companyDetails?.GSTN?:"") }
    var PAN by remember{ mutableStateOf(companyDetails?.PAN?:"") }
    var MSME by remember{ mutableStateOf(companyDetails?.MSME?:"") }
    var websites by remember{ mutableStateOf<List<String>>(companyDetails?.websites?:emptyList()) }
    var remark by remember{ mutableStateOf(companyDetails?.remarks?:"") }



//    for AddressDetails
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    // old address for Edit Address
    var oldAddress by remember{ mutableStateOf(AddressDetails("","","","")) }
    var ifEditAddressCancelled by remember{ mutableStateOf(false) }


    var showCompanyMenu by remember{ mutableStateOf(false) }

    var addressDialogBox by remember{ mutableStateOf(false) }
    var updateAddressDialogBox by remember{ mutableStateOf(false) }
    var ownerDialogBox by remember{ mutableStateOf(false) }
    var websiteDialogBox by remember{ mutableStateOf(false) }
    var emailDialogBox by remember{ mutableStateOf(false) }
    var phoneDialogBox by remember{ mutableStateOf(false) }
    var partyTypeDropDown by remember{ mutableStateOf(false) }
    var scrollState = rememberScrollState()


    //for going to the next field when enter is clicked
    val focusRequester1 = remember{FocusRequester()}
    val focusRequester2 = remember{FocusRequester()}
    val focusRequester3 = remember{FocusRequester()}
    val focusRequester4 = remember{FocusRequester()}
    val focusRequester5 = remember{FocusRequester()}
    val focusRequester6 = remember{FocusRequester()}
    val focusRequester7 = remember{FocusRequester()}
    val focusRequester8 = remember{FocusRequester()}

    val context = LocalContext.current

    Column(modifier=Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(state = scrollState, enabled = true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (companyDetails == null) {
                Text(
                    "New Entry",
                    fontSize = 30.sp, fontWeight = FontWeight.ExtraBold
                )
            } else {
                Text(
                    "Edit Details",
                    fontSize = 30.sp, fontWeight = FontWeight.ExtraBold
                )
            }

            //Company Filed
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Company",
                        modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                    )

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester1),
                    label = { Text(text = "company name") }, value = companyName,
                    onValueChange = { companyName = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester2.requestFocus() }
                    )
                )
            }

            //OWNER Field
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Owners",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = { ownerDialogBox = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add Owner")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 100.dp)
                ) {
                    items(ownerNames) { owner ->
                        OwnerItem(owner = owner,
                            onDeleteClick = {
                                ownerNames = ownerNames - owner
                            })
                    }
                }
            }

            //PARTY TYPE field
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Party Type",
                        modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                    )

                    //to mark it as necessary field
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(start = 2.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

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

            //ADDRESS Filed
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row() {
                        Text(
                            "Address",
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 20.sp
                        )

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .size(15.dp)
                                .padding(start = 2.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Button(
                        onClick = { addressDialogBox = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add Address")
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 250.dp)
                ) {
                    items(addresses) {

                        AddressItem(it,
                            onDeleteClick={ addresses = addresses - it },
                            onEditAddressClick = {
                                address = it.Address
                                city=it.City
                                pincode = it.Pincode
                                state=it.State
                                oldAddress = it.copy()
                                updateAddressDialogBox=true
                            }
                        )
                    }
                }
            }

            //Phone Number field
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Phone Numbers",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = { phoneDialogBox = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add number")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 100.dp)
                ) {
                    items(phoneNumbers) { number ->
                        OwnerItem(owner = number,
                            onDeleteClick = {
                                phoneNumbers = phoneNumbers - number
                            })
                    }
                }
            }

            //INTRODUCED BY Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Introduced By",
                    modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester3),
                    label = { Text(text = "name") }, value = introducedBy,
                    onValueChange = { introducedBy = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester4.requestFocus() }
                    ))
            }

            //GST Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "GST Number",
                    modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester4),
                    label = { Text(text = "GSTN") }, value = GSTN,
                    onValueChange = { GSTN = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester5.requestFocus() }
                    ))
            }

            //PAN Filed
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "PAN Number",
                    modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester5),
                    label = { Text(text = "PAN") }, value = PAN,
                    onValueChange = { PAN = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester6.requestFocus() }
                    ))
            }

            //MSME Filed
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "MSME Number",
                    modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester6),
                    label = { Text(text = "MSME") }, value = MSME,
                    onValueChange = { MSME = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester7.requestFocus() }
                    ))
            }

            //Website Field
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "websites",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = { websiteDialogBox = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add Website")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 100.dp)
                ) {
                    items(websites) { website ->
                        OwnerItem(owner = website,
                            onDeleteClick = { websites = websites - website })
                    }
                }
            }

            //emails
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "email id's",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 20.sp
                    )

                    Button(
                        onClick = { emailDialogBox = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text("Add email")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 100.dp)
                ) {
                    items(emails) { email ->
                        OwnerItem(owner = email,
                            onDeleteClick = { emails = emails - email })
                    }
                }
            }


            //REMARK field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Remarks",
                    modifier = Modifier.padding(top = 8.dp), fontSize = 20.sp
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester7),
                    label = { Text(text = "remark") }, value = remark,
                    onValueChange = { remark = it })
            }

            //for add and cancel button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                //Add the new Address entry to the address book
                Button(onClick = {
                    if (companyName.isNotEmpty() and partyType.isNotEmpty() and addresses.isNotEmpty()) {
                        if (viewModel.getACompany(companyName) != null) {
                            Toast.makeText(
                                context,
                                "Company Name Already Exists",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val newAddressEntry = CompanyDetails(
                                companyName = companyName,
                                ownerName = ownerNames,
                                partyType = partyType,
                                Addresses = addresses,
                                emails = emails,
                                phoneNumbers = phoneNumbers,
                                introducedBy = introducedBy,
                                GSTN = GSTN,
                                PAN = PAN,
                                MSME = MSME,
                                websites = websites,
                                remarks = remark
                            )

                            if (companyDetails != null) {
                                viewModel.deleteCompany(companyDetails)
                            }
                            viewModel.addNewAddressItem(newAddressEntry)

                            companyName = ""
                            ownerNames = emptyList()
                            partyType = ""
                            addresses = emptyList()
                            emails = emptyList()
                            phoneNumbers = emptyList()
                            introducedBy = ""
                            GSTN = ""
                            PAN = ""
                            MSME = ""
                            websites = emptyList()
                            remark = ""

                            onAddClicked()
                        }
                    } else {
                        Toast.makeText(context, "missing necessary data", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Add")
                }

                //Cancel Button
                Button(onClick = {
                    companyName = ""
                    ownerNames = emptyList()
                    partyType = ""
                    addresses = emptyList()
                    emails = emptyList()
                    phoneNumbers = emptyList()
                    introducedBy = ""
                    GSTN = ""
                    PAN = ""
                    MSME = ""
                    websites = emptyList()
                    remark = ""
                    onCancelClicked()

                }) {
                    Text("Cancel")
                }
            }
        }
        Row(modifier= Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween){
            //Search Button
            IconButton(onClick = { onSearchScreenClicked() }) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Search,
                    contentDescription =null,
                    tint= Color.White
                    ,modifier=Modifier.size(30.dp))
            }

            //Home button
            IconButton(onClick = { onHomeScreenClicked()}) {
                androidx.compose.material3.Icon(imageVector = Icons.Default.Home,
                    contentDescription =null,
                    tint= Color.White,
                    modifier=Modifier.size(30.dp))
            }

            //Add Button
            if(companyDetails==null) {
                IconButton(onClick = { onAddScreenClicked() }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else {
                IconButton(onClick = { onAddScreenClicked() }) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

        }
    }

    if(addressDialogBox){

        val context = LocalContext.current

        AlertDialog(
            title = { Text("Add Address") },
            text = {
                Column() {
                    OutlinedTextField(value = address,
                        onValueChange = { address = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Address") })

                    OutlinedTextField(value = city,
                        onValueChange = { city = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("City") })

                    OutlinedTextField(value = pincode,
                        onValueChange = { pincode = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Pincode") })

                    OutlinedTextField(value = state,
                        onValueChange = { state = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("State") })
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Add Button
                    Button(onClick = {
                        if(city.isNotEmpty() && state.isNotEmpty())
                        {
                            val temp = AddressDetails(Address=address,
                                City = city,
                                Pincode=pincode,
                                State = state)

                            addresses = addresses + temp

                            address=""
                            city=""
                            pincode=""
                            state=""

                            addressDialogBox = false
                        }
                        else{
                            Toast.makeText(context,"missing Data",Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Text("Add")
                    }

                    //Cancel Button
                    Button(onClick = {
                        address=""
                        city=""
                        pincode=""
                        state=""
                        addressDialogBox = false
                    }) {
                        Text("Cancel")
                    }
                }
            },
            onDismissRequest = {},
        )
    }

    if(ownerDialogBox){

        var owner by remember{mutableStateOf("") }
        val context = LocalContext.current
        AlertDialog(
            title={Text("Add Owner")},
            onDismissRequest = { /*TODO*/ },
            text={
                 Column(modifier=Modifier.fillMaxWidth()){
                     OutlinedTextField(label={Text("Name")},value =owner, onValueChange ={owner=it},
                         modifier= Modifier
                             .fillMaxWidth()
                             .padding(8.dp))
                 }
            },
            confirmButton = {
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    //Add Button
                    Button(onClick = {
                        if(owner.isNotEmpty()) {
                            ownerNames = ownerNames + owner
                            owner = ""
                            ownerDialogBox=false
                        }
                        else
                        {
                            Toast.makeText(context,"No name entered",Toast.LENGTH_SHORT).show()
                        }
                    }){
                        Text("Add")
                    }
                    //Cancel Button
                    Button(onClick={
                        owner = ""
                        ownerDialogBox=false
                    }){
                        Text("Cancel")
                    }
                }
            })
    }

    if(websiteDialogBox){

        var website by remember{mutableStateOf("") }
        val context = LocalContext.current
        AlertDialog(
            title={Text("Add website")},
            onDismissRequest = { /*TODO*/ },
            text={
                Column(modifier=Modifier.fillMaxWidth()){
                    OutlinedTextField(label={Text("website url")},value =website, onValueChange ={website=it},
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp))
                }
            },
            confirmButton = {
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    //Add Button
                    Button(onClick = {
                        if(website.isNotEmpty()) {
                            websites = websites + website
                            website = ""
                            websiteDialogBox=false
                        }
                        else
                        {
                            Toast.makeText(context,"No website URL entered",Toast.LENGTH_SHORT).show()
                        }
                    }){
                        Text("Add")
                    }
                    //Cancel Button
                    Button(onClick={
                        website = ""
                        websiteDialogBox=false
                    }){
                        Text("Cancel")
                    }
                }
            })
    }

    if(emailDialogBox){

        var email by remember{mutableStateOf("") }
        val context = LocalContext.current
        AlertDialog(
            title={Text("Add email")},
            onDismissRequest = { /*TODO*/ },
            text={
                Column(modifier=Modifier.fillMaxWidth()){
                    OutlinedTextField(label={Text("email id")},value =email, onValueChange ={email=it},
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp))
                }
            },
            confirmButton = {
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    //Add Button
                    Button(onClick = {
                        if(email.isNotEmpty()) {
                            emails = emails + email
                            email = ""
                            emailDialogBox=false
                        }
                        else
                        {
                            Toast.makeText(context,"No email id entered",Toast.LENGTH_SHORT).show()
                        }
                    }){
                        Text("Add")
                    }
                    //Cancel Button
                    Button(onClick={
                        email = ""
                        emailDialogBox=false
                    }){
                        Text("Cancel")
                    }
                }
            })
    }

    if(phoneDialogBox){

        var phone by remember{mutableStateOf("") }
        val context = LocalContext.current
        AlertDialog(
            title={Text("Add number")},
            onDismissRequest = { /*TODO*/ },
            text={
                Column(modifier=Modifier.fillMaxWidth()){
                    OutlinedTextField(label={Text("number")},value =phone, onValueChange ={phone=it},
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp))
                }
            },
            confirmButton = {
                Row(modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    //Add Button
                    Button(onClick = {
                        if(phone.isNotEmpty()) {
                            phoneNumbers = phoneNumbers + phone
                            phone = ""
                            phoneDialogBox=false
                        }
                        else
                        {
                            Toast.makeText(context,"No number entered",Toast.LENGTH_SHORT).show()
                        }
                    }){
                        Text("Add")
                    }
                    //Cancel Button
                    Button(onClick={
                        phone = ""
                        phoneDialogBox=false
                    }){
                        Text("Cancel")
                    }
                }
            })
    }

    if(updateAddressDialogBox){

        val context = LocalContext.current
        AlertDialog(
            title = { Text("Edit Address") },
            text = {
                Column() {
                    OutlinedTextField(value = address,
                        onValueChange = { address = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Address") })

                    OutlinedTextField(value = city,
                        onValueChange = { city = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("City") })

                    OutlinedTextField(value = pincode,
                        onValueChange = { pincode = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Pincode") })

                    OutlinedTextField(value = state,
                        onValueChange = { state = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("State") })
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Add Button
                    Button(onClick = {
                        if(city.isNotEmpty() && state.isNotEmpty())
                        {
                            val newAddress = AddressDetails(Address=address,
                                City = city,
                                Pincode=pincode,
                                State = state)

                            println(oldAddress)
                            addresses = addresses - oldAddress
                            addresses = addresses + newAddress

                            address=""
                            city=""
                            pincode=""
                            state=""

                            updateAddressDialogBox = false
                        }
                        else{
                            Toast.makeText(context,"missing Data",Toast.LENGTH_LONG).show()
                        }
                    }) {
                        Text("Save")
                    }

                    //Cancel Button
                    Button(onClick = {
                        address=""
                        city=""
                        pincode=""
                        state=""
                        updateAddressDialogBox = false
                    }) {
                        Text("Cancel")
                    }
                }
            },
            onDismissRequest = {},
        )
    }

}



@Composable
fun AddressItem(address : AddressDetails,
                onDeleteClick: () -> Unit,
                onEditAddressClick:()->Unit) {
    Card(
        modifier= Modifier
            .fillMaxSize()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(10.dp)){
        Row(modifier=Modifier.fillMaxWidth())
        {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .clickable {onEditAddressClick()}
            ) {
                Text(address.Address.trim())
                Text(address.City.trim())
                Text(address.Pincode.trim())
                Text(address.State.trim())
            }

            IconButton(onClick = { onDeleteClick() }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null,modifier=Modifier.size(40.dp))
            }
        }
    }
}

@Composable
fun OwnerItem(owner:String,
              onDeleteClick:()->Unit){
    Card(modifier=Modifier.padding(2.dp)){
        Row(modifier = Modifier
//            .clickable {}
            .padding(8.dp)) {
            Text(owner.trim())
            IconButton(onClick = {onDeleteClick()},
                Modifier.size(25.dp)) {
                Icon(imageVector = Icons.Default.Clear, contentDescription =null )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddScreenPreview(){
//    AddScreen(viewModel=viewModel(),{},{})
//}