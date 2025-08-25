package com.himanshu.login
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
//import com.bumptech.glide.integration.compose.GlideImage
//
//@Composable
////fun SignUpScreen(
//    navController: NavController
//) {
//
//    val viewModel = hiltViewModel<UserViewModel>()
//    val uiState = viewModel.uiState
//
//    LaunchedEffect(Unit) {
//        viewModel.checkUserStatus()
//    }
//
//    LaunchedEffect(uiState.value.user) {
////        if(uiState.value.user != null){
////            navController.currentBackStackEntry?.savedStateHandle?.set("userId", uiState.value.user!!.deviceId)
////            navController.navigate(Screen.RandomMatch.route){
//////                popUpTo(navController.graph.startDestinationId) { inclusive = true }
////            }
////        }
////    }
//
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(horizontal = 20.dp)
//        ,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(200.dp))
//
//        if(uiState.value.loading){
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ){
//                CircularProgressIndicator()
//            }
//        }
//
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth().border(
//                width = 1.dp,
//                color = Color.Black,
//                shape = RoundedCornerShape(5.dp)
//            ),
//            placeholder = { Text("enter your name") },
//            value = uiState.value.userName,
//            onValueChange = viewModel::onUserNameUpdate
//        )
//
//        GenderSelection(
//            selectedGender = uiState.value.selectedGender,
//            onGenderSelected = viewModel::onGenderSelected
//        )
//        Text(text = "Select Profile Picture", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//        Spacer(modifier = Modifier.height(8.dp))
//        ProfilePicturesWidget(
//            pictures = uiState.value.filteredPictures,
//            selectedImage = uiState.value.selectedImage,
//            onImageSelected = viewModel::onImageSelected
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(onClick = {
//            if(uiState.value.userName.isEmpty()) return@Button
//            viewModel.saveUser() })
//        {
//            Text("Submit")
//        }
//    }
//}
//
//@Composable
//fun GenderSelection(
//    selectedGender : String,
//    onGenderSelected : (String) -> Unit
//) {
//
//    val genderList = arrayOf("boy","girl")
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = "Select Gender", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//        genderList.forEach {
//            Row(
//                modifier = Modifier
//                    .clickable { onGenderSelected(it) }
//                    .fillMaxWidth()
//                    .padding(top = 4.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RadioButton(
//                    selected = selectedGender == it,
//                    onClick = { onGenderSelected(it) }
//                )
//                Text(
//                    text = it,
//                    modifier = Modifier.padding(start = 8.dp),
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalLayoutApi::class, ExperimentalGlideComposeApi::class)
//@Composable
//fun ProfilePicturesWidget(
//    pictures : ArrayList<String> ,
//    selectedImage : String,
//    onImageSelected : (String)-> Unit
//){
//
//
//    FlowRow(
//        maxItemsInEachRow = 6,
//        horizontalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(10))
//            .background(Color.DarkGray)
//    ) {
//
//        pictures.forEach {
//            GlideImage(
//                modifier = Modifier
//                    .size(80.dp)
//                    .padding(6.dp)
//                    .then(
//                        if (selectedImage == it) Modifier.border(3.dp, Color.Black, RoundedCornerShape(100))
//                        else Modifier
//                    )
//                    .clip(androidx.compose.foundation.shape.CircleShape)
//                    .clickable { onImageSelected(it) }
//
//                ,
//                model = it,
//                contentDescription = null
//            )
//        }
//    }
//}