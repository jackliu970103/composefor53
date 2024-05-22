package com.example.cps53

import android.nfc.Tag
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cps53.ui.theme.Cps53Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = DBHelper(this)

        setContent {
            Cps53Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController=rememberNavController()
                    NavHost(navController = navController, startDestination = "home"){
                        composable("home"){
                                HomeScreen(navController)
                        }
                        composable("signup"){
                            secondScreen(navController)
                        }
                        composable("Splashscreen"){
                            SplashScreen(navController)
                        }
                        composable("main"){
                            mainscreen(navController)
                        }
                        composable("aboutBuilding"){
                            aboutbuilding(navController)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun HomeScreen(navController: NavController){
    Column {
        topImage()
        tag(inside = "登入")
        LoginScreen(DBHelper(LocalContext.current),navController)

    }
}

@Composable
fun topImage(){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.topimage), contentDescription = null)
    }
}

@Composable
fun tag(inside: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = inside, fontSize = 50.sp)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun LoginScreen(dbHelper: DBHelper,navController: NavController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = username,
            onValueChange = { newUsername -> username = newUsername },
            label = { Text(text = "輸入Email") }
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text(text = "請輸入密碼") }
        )
        Spacer(modifier = Modifier.height(150.dp))
        FilledTonalButton(
            onClick = {
                val valid = dbHelper.checkUserPass(username, password)
                if (valid) {
                    navController.navigate("Splashscreen")
                } else {
                    message = "Invalid credentials"
                }
            },
            modifier = Modifier.widthIn(max = 200.dp)
        ) {
            Text(text = "登入")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("沒有任何帳號嗎?")
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("註冊帳號")
            }
        }
        Text(text = message, color = Color.Red)
    }
}
@Composable
fun secondScreen(navController: NavController){
    topImage()
    tag(inside = "註冊")
    forsqlres(navController)
}

@Composable
fun forsqlres(navController: NavController){
    var resUsername by remember {
        mutableStateOf("")
    }
    var resPassword by remember {
        mutableStateOf("")
    }
    Column {
        TextField(value =resUsername , onValueChange = {newresUsername ->resUsername =newresUsername}, label = { Text(
            text = "輸入Email"
        )})
        Spacer(modifier = Modifier.height(50.dp))
        TextField(value = resPassword, onValueChange = {newresPassword ->resPassword =newresPassword}, label = { Text(
            "請輸入密碼"
        )})
        FilledTonalButton(onClick = { navController.navigate("Splashscreen") }) {
            Text("註冊")
        }
    }
}
//Splashscreen
@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(true){
        delay(3000)
        navController.navigate("main")
    }
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(R.drawable.topimage) , contentDescription = null)
    }
}
//main主畫面
@Composable
fun mainscreen(navController: NavController){
Column (){
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {

                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "關於展覽") },
                    selected = false,
                    onClick = { navController.navigate("aboutBuilding") }
                )
                NavigationDrawerItem(label = { Text(text = "樓層立體圖") },
                    selected = false,
                    onClick = { /*TODO*/ })
            }
        }
    ) {
        // Screen content
    }
}
}
//aboutbuilding
@Composable
fun aboutbuilding(navController: NavController){
    var currectImage by remember {
        mutableStateOf(R.drawable.thefirstfloor)
    }
    Column {
        Row {
            Button(onClick = { currectImage=R.drawable.thefirstfloor }) {
                Text(text = "一館")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { currectImage=R.drawable.thefloorsecond }) {
                Text(text = "二館")
            }
        }
        Image(painter = painterResource(currectImage), contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Cps53Theme {
        Column {
            topImage()
            tag(inside = "登入")

        }
    }
}
