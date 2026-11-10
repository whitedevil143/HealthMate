package com.healthmate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF173B36)
private val Teal = Color(0xFF168978)
private val Canvas = Color(0xFFF4F8F6)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { HealthMateApp() } }
    }
}

@Composable fun HealthMateApp() {
    var page by remember { mutableStateOf("Welcome") }
    var signedIn by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Home") }
    Surface(Modifier.fillMaxSize(), color = Canvas) {
        when (page) {
            "Welcome" -> Welcome { page = it }
            "Sign in" -> AuthScreen(false, { signedIn = true; page = "Home" }, { page = "Welcome" })
            "Create account" -> AuthScreen(true, { page = "OTP verification" }, { page = "Welcome" })
            "OTP verification" -> OtpScreen { page = "Profile setup" }
            "Profile setup" -> ProfileSetup { signedIn = true; page = "Home" }
            "Home" -> MainScreen(selectedTab, { selectedTab = it }, { page = it }, { signedIn = false; page = "Welcome" })
            else -> ModuleScreen(page, { page = "Home" })
        }
    }
}

@Composable private fun Welcome(go:(String)->Unit) {
    Column(Modifier.fillMaxSize().padding(28.dp), verticalArrangement=Arrangement.Center) {
        Text("✚", color=Teal, fontSize=50.sp, fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(14.dp)); Text("HealthMate", color=Ink, fontSize=34.sp, fontWeight=FontWeight.Bold)
        Text("Your health, thoughtfully organised.", color=Color.DarkGray, fontSize=16.sp)
        Spacer(Modifier.height(30.dp))
        Button(onClick={go("Create account")}, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text("Create account") }
        OutlinedButton(onClick={go("Sign in")}, modifier=Modifier.fillMaxWidth()) { Text("Sign in") }
        Spacer(Modifier.height(12.dp)); Text("HealthMate stores information you provide. It does not diagnose or replace professional medical advice.", fontSize=12.sp, color=Color.Gray)
    }
}

@Composable private fun AuthScreen(register:Boolean, onSubmit:()->Unit, back:()->Unit) {
    var email by remember { mutableStateOf("") }; var password by remember { mutableStateOf("") }; var name by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center) {
        Text("‹  Back", Modifier.clickable { back() }, color=Teal); Spacer(Modifier.height(20.dp))
        Text(if(register) "Create your account" else "Welcome back", fontSize=28.sp, fontWeight=FontWeight.Bold, color=Ink)
        Text("A private space for your health information", color=Color.Gray)
        if(register) { Spacer(Modifier.height(14.dp)); OutlinedTextField(name,{name=it},label={Text("Full name")},modifier=Modifier.fillMaxWidth()) }
        Spacer(Modifier.height(12.dp)); OutlinedTextField(email,{email=it},label={Text("Email or mobile number")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp)); OutlinedTextField(password,{password=it},label={Text("Password")},modifier=Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp)); Button(onClick={ if(register) onSubmit() else onSubmit() }, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text(if(register) "Continue to verification" else "Sign in") }
        if(register) Text("Demo preview: OTP delivery requires a configured backend/SMS provider.", Modifier.padding(top=12.dp), fontSize=12.sp, color=Color.Gray)
    }
}

@Composable private fun OtpScreen(done:()->Unit) { var otp by remember { mutableStateOf("") }; Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center) { Text("Verify your account", fontSize=28.sp, fontWeight=FontWeight.Bold, color=Ink); Text("Enter the verification code sent to your phone."); Spacer(Modifier.height(20.dp)); OutlinedTextField(otp,{otp=it},label={Text("6-digit code")}); TextButton(onClick={}) { Text("Resend code") }; Button(onClick=done, colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text("Verify & continue") } } }
@Composable private fun ProfileSetup(done:()->Unit) { var location by remember { mutableStateOf("") }; Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center) { Text("Set up your profile", fontSize=28.sp, fontWeight=FontWeight.Bold, color=Ink); Text("You can update these details later."); Spacer(Modifier.height(15.dp)); OutlinedTextField(location,{location=it},label={Text("City / area (optional)")}); Spacer(Modifier.height(18.dp)); Button(onClick=done, colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text("Finish setup") } } }

private val modules = listOf("Health Issues" to "Record conditions and personal notes", "Health Tracking" to "Log measurements and view history", "Allergies" to "Keep allergy details together", "Medical Documents" to "Manage your medical files", "Meditation Reminders" to "Plan a moment for mindfulness", "QR Scanner" to "Scan supported QR codes", "Subscription" to "Plans and membership status", "Blood Donation" to "Set up your donor information", "Find Blood Donors" to "Search by blood group and location", "Profile & Settings" to "Your account and preferences")

@Composable private fun MainScreen(tab:String, setTab:(String)->Unit, open:(String)->Unit, logout:()->Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(horizontal=20.dp, vertical=18.dp), horizontalArrangement=Arrangement.SpaceBetween, verticalAlignment=Alignment.CenterVertically) { Column { Text("HEALTHMATE", fontSize=12.sp, color=Teal, fontWeight=FontWeight.Bold); Text(if(tab=="Home") "Good day" else tab, fontSize=25.sp, color=Ink, fontWeight=FontWeight.Bold) }; Text("●", color=Teal, fontSize=24.sp) }
        if(tab=="Home") LazyColumn(contentPadding=PaddingValues(18.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { Card(colors=CardDefaults.cardColors(containerColor=Ink), shape=RoundedCornerShape(22.dp)) { Column(Modifier.padding(20.dp)) { Text("A healthier routine starts here", color=Color.White, fontSize=20.sp, fontWeight=FontWeight.Bold); Text("Your records, reminders and donor tools in one place.", color=Color(0xFFD8ECE6), modifier=Modifier.padding(top=6.dp)); Text("View health records  →", Modifier.clickable { open("Health Tracking") }.padding(top=15.dp), color=Color.White, fontWeight=FontWeight.SemiBold) } } }
            item { Text("YOUR HEALTH", color=Teal, fontSize=12.sp, fontWeight=FontWeight.Bold) }
            items(modules.size) { i -> ModuleCard(modules[i].first,modules[i].second) { open(modules[i].first) } }
            item { Text("HealthMate is not for diagnosis, treatment recommendations or emergencies.", fontSize=12.sp, color=Color.Gray, modifier=Modifier.padding(6.dp)) }
        } else LazyColumn(contentPadding=PaddingValues(18.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) { items(modules.size) { i -> ModuleCard(modules[i].first,modules[i].second) { open(modules[i].first) } }; item { TextButton(onClick=logout) { Text("Log out") } } }
        NavigationBar(containerColor=Color.White) { listOf("Home","Health","Blood","Profile").forEach { item -> NavigationBarItem(selected=tab==item,onClick={setTab(if(item=="Health") "Health Tracking" else if(item=="Blood") "Find Blood Donors" else item)},icon={Text(when(item){"Home"->"⌂";"Health"->"♡";"Blood"->"✚";else->"◉"})},label={Text(item)}) } }
    }
}
@Composable private fun ModuleCard(title:String, subtitle:String, click:()->Unit) { Card(Modifier.fillMaxWidth().clickable { click() }, colors=CardDefaults.cardColors(containerColor=Color.White), shape=RoundedCornerShape(18.dp)) { Row(Modifier.padding(16.dp), verticalAlignment=Alignment.CenterVertically) { Box(Modifier.size(42.dp).background(Color(0xFFE6F4EF),RoundedCornerShape(14.dp)),contentAlignment=Alignment.Center) { Text("✚", color=Teal) }; Column(Modifier.weight(1f).padding(start=13.dp)) { Text(title, color=Ink,fontWeight=FontWeight.SemiBold); Text(subtitle, color=Color.Gray,fontSize=12.sp) }; Text("›", fontSize=25.sp,color=Teal) } } }

@Composable private fun ModuleScreen(title:String, back:()->Unit) {
    var input by remember { mutableStateOf("") }; var query by remember { mutableStateOf("") }; var group by remember { mutableStateOf("O+") }
    val blood = title.contains("Blood") || title.contains("Donor")
    Column(Modifier.fillMaxSize()) {
        Text("‹  Dashboard", Modifier.clickable { back() }.padding(20.dp), color=Teal)
        LazyColumn(Modifier.weight(1f), contentPadding=PaddingValues(horizontal=22.dp), verticalArrangement=Arrangement.spacedBy(13.dp)) {
            item { Text(title, fontSize=28.sp, color=Ink,fontWeight=FontWeight.Bold); Text("Manage your information securely.", color=Color.Gray) }
            if (blood) {
                item { Text("Blood group",fontWeight=FontWeight.SemiBold,color=Ink); Row(horizontalArrangement=Arrangement.spacedBy(7.dp)) { listOf("A+","A−","B+","B−","AB+","AB−","O+","O−").forEach { g -> FilterChip(selected=group==g,onClick={group=g},label={Text(g)}) } } }
                item { OutlinedTextField(query,{query=it},label={Text("Location or city")},modifier=Modifier.fillMaxWidth()) }
                item { OutlinedTextField(input,{input=it},label={Text(if(title=="Blood Donation") "Availability / contact preference" else "Search radius (km)")},modifier=Modifier.fillMaxWidth()) }
                item { Button(onClick={},modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text(if(title=="Blood Donation") "Save donor details" else "Search donors") } }
                item { Text(if(title=="Blood Donation") "Your donor profile can be made discoverable after verification is configured." else "Matching donor results will appear here when connected to the HealthMate service.", color=Color.Gray) }
            } else if (title.contains("QR")) {
                item { Card(colors=CardDefaults.cardColors(containerColor=Color.White)) { Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment=Alignment.CenterHorizontally) { Text("▦",fontSize=70.sp,color=Teal); Text("Camera scanner setup required",color=Ink,fontWeight=FontWeight.SemiBold); Text("Supported QR format and scan action are to be confirmed.",color=Color.Gray) } } }
            } else if(title.contains("Reminder") || title.contains("Meditation")) {
                item { OutlinedTextField(input,{input=it},label={Text("Reminder time (e.g. 08:00)")},modifier=Modifier.fillMaxWidth()) }
                item { Button(onClick={}, colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text("Save reminder") } }
            } else if(title=="Subscription") {
                item { Text("Membership plans",fontWeight=FontWeight.Bold,color=Ink); Text("Pricing, duration, benefits and renewal rules await final confirmation. Purchases are not active in this preview.",color=Color.Gray) }
            } else if(title.contains("Documents")) {
                item { Text("No documents added yet",fontWeight=FontWeight.SemiBold,color=Ink); Text("Secure upload and viewing require backend storage configuration.",color=Color.Gray); OutlinedButton(onClick={}) { Text("Choose document (not connected)") } }
            } else {
                item { OutlinedTextField(input,{input=it},label={Text(when {title.contains("Allerg") -> "Allergy name and reaction";title.contains("Issue") -> "Condition / health issue";else -> "Measurement and value (e.g. Weight, 68 kg)"})},modifier=Modifier.fillMaxWidth(),minLines=2) }
                item { OutlinedTextField(query,{query=it},label={Text("Date or notes (optional)")},modifier=Modifier.fillMaxWidth()) }
                item { Button(onClick={},modifier=Modifier.fillMaxWidth(),colors=ButtonDefaults.buttonColors(containerColor=Teal)) { Text("Save entry") } }
                item { Text("No saved entries in this preview. Secure personal records need the Laravel API.",color=Color.Gray) }
            }
            item { Text("For health information management only. Not a diagnosis, medical advice or emergency service.",fontSize=12.sp,color=Color.Gray,modifier=Modifier.padding(vertical=12.dp)) }
        }
    }
}
