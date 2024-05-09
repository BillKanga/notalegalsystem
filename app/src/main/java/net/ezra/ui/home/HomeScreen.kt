package net.ezra.ui.home


import android.content.ClipData.Item
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import net.ezra.navigation.ROUTE_ABOUT
import net.ezra.navigation.ROUTE_ADD_STUDENTS
import net.ezra.navigation.ROUTE_CALENDAR
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_SIGNUP
import net.ezra.navigation.ROUTE_VIEW_STUDENTS
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column ( modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    val context = LocalContext.current.applicationContext

//                    START OF TOP APP BAR
                    TopAppBar(
                        title = { Text(text = "Kanga & Co. Advocates",
                            color = Color.White
                            ) },
                        navigationIcon = {
                            IconButton(onClick = {

                                navController.navigate(ROUTE_HOME) {
                                    popUpTo(ROUTE_ADD_STUDENTS) { inclusive = true }
                                }

                            }) {
                                Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFFFFFFF),
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        ),
                        actions = {
                            IconButton(onClick = {Toast.makeText(context, "Search Document", Toast.LENGTH_SHORT).show()}) {
                                Icon(imageVector = Icons.Filled.Search, contentDescription = "search", tint = Color(0xFF386FFA))
                            }
                            IconButton(onClick = {Toast.makeText(context, "Calendar", Toast.LENGTH_SHORT).show() }) {
                                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calandar", tint= Color(0xFF386FFA))
                            }
                            IconButton(onClick = {Toast.makeText(context,"Login", Toast.LENGTH_SHORT).show()}) {
                                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "person", tint= Color(0xFF386FFA))

                            }
                        })
                }
                Image(
                    painter = painterResource(id = net.ezra.R.drawable.kangalogo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)

                )
            }
//            END OF TOP APP BAR





            Column(

                modifier = Modifier.padding(15.dp),

//                horizontalAlignment = Alignment.CenterHorizontally
            ){


            Card(
                modifier = Modifier
                    .width(420.dp)
                    .height(150.dp),
                colors = CardDefaults.cardColors(Color.Black)

            ) {

            }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Client",
                        textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.SansSerif
                    )
                Spacer(modifier = Modifier.height(2.dp))


                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(150.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {

                }
                Spacer(modifier = Modifier.height(20.dp))

Row (
    modifier = Modifier
        .fillMaxWidth(),


){
    Text(text = "Recent Documents",
        fontSize = 15.sp,
        color = Color.DarkGray,
        fontFamily = FontFamily.SansSerif
    )
Spacer(modifier = Modifier.width(190.dp))
//    Text(text = "See all",
//        textAlign = TextAlign.Right,
//        fontSize = 15.sp,
//        color = Color(0xFF386FFA),
//        fontFamily = FontFamily.SansSerif
//    )
}

                Spacer(modifier = Modifier.height(2.dp))


                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(180.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Scheduled events",
                    textAlign = TextAlign.Left,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(2.dp))

                Card(
                    modifier = Modifier
                        .width(420.dp)
                        .height(150.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    colors = CardDefaults.cardColors(Color.White)

                ) {


                }





                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_ABOUT) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    text = "VISIT OUR WEBSITE HERE OF THE APP"
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_SIGNUP) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    text = "SIGN UP SCREEN"
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_CALENDAR) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    text = "CALENDAR SCREEN"
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_VIEW_STUDENTS) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    text = "VIEW SCREEN"
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_ADD_STUDENTS) {
                                popUpTo(ROUTE_HOME) { inclusive = true }
                            }
                        },
                    text = "CLIENT SCREEN"
                )

































            }

        }
    }
}




















@Preview(showBackground = true)
@Composable
fun PreviewLight() {
    HomeScreen(rememberNavController())
}