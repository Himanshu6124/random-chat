package com.himanshu.whatsapp.dummydata

import androidx.compose.runtime.mutableStateListOf
import com.himanshu.whatsapp.R
import com.himanshu.whatsapp.ui.theme.components.ChatCardData
import com.himanshu.whatsapp.ui.theme.components.Message
import com.himanshu.whatsapp.ui.theme.components.MessageStatus

val chatList = listOf(
    ChatCardData("1","Aman", "Hey, are you free today?", R.drawable.user_profile, "10:30 AM"),
    ChatCardData("2","Priya", "Let's catch up soon!", R.drawable.user_profile, "Yesterday"),
    ChatCardData("3","Rohit", "Did you check the new update?", R.drawable.user_profile, "8:45 AM"),
    ChatCardData("4","Sneha", "Happy Birthday! 🎉", R.drawable.user_profile, "Sunday"),
    ChatCardData("5","Vikas", "Call me when you're free.", R.drawable.user_profile, "Saturday"),
    ChatCardData("6","Neha", "Had a great time yesterday!", R.drawable.user_profile, "Friday"),
    ChatCardData("7","Rahul", "Check out this cool article!", R.drawable.user_profile, "Thursday"),
    ChatCardData("8","Simran", "Are we still on for dinner?", R.drawable.user_profile, "Wednesday"),
    ChatCardData("9","Ankit", "Don't forget the meeting at 5.", R.drawable.user_profile, "Tuesday"),
    ChatCardData("10","Kavita", "How's your new job going?", R.drawable.user_profile, "Monday")  ,
    ChatCardData("1","Aman", "Hey, are you free today?", R.drawable.user_profile, "10:30 AM"),
    ChatCardData("2","Priya", "Let's catch up soon!", R.drawable.user_profile, "Yesterday"),
    ChatCardData("3","Rohit", "Did you check the new update?", R.drawable.user_profile, "8:45 AM"),
    ChatCardData("4","Sneha", "Happy Birthday! 🎉", R.drawable.user_profile, "Sunday"),
    ChatCardData("5","Vikas", "Call me when you're free.", R.drawable.user_profile, "Saturday"),
    ChatCardData("6","Neha", "Had a great time yesterday!", R.drawable.user_profile, "Friday"),
    ChatCardData("7","Rahul", "Check out this cool article!", R.drawable.user_profile, "Thursday"),
    ChatCardData("8","Simran", "Are we still on for dinner?", R.drawable.user_profile, "Wednesday"),
    ChatCardData("9","Ankit", "Don't forget the meeting at 5.", R.drawable.user_profile, "Tuesday"),
    ChatCardData("10","Kavita", "How's your new job going?", R.drawable.user_profile, "Monday")
)
val dummyMessages = mutableStateListOf(
    Message("1", "Hey, how are you?", MessageStatus.SENT, 1711100100000, isByUser = true),
    Message("2", "I'm good! How about you?", MessageStatus.DELIVERED, 1711100200000, isByUser = false),
    Message("3", "I'm doing great! What’s up?", MessageStatus.READ, 1711100300000, isByUser = true),
    Message("4", "Nothing much, just chilling. You?", MessageStatus.READ, 1711100400000, isByUser = false),
    Message("5", "Same here! Planning anything for the weekend?", MessageStatus.SENT, 1711100500000, isByUser = true),
    Message("6", "Not really, maybe a short trip.", MessageStatus.DELIVERED, 1711100600000, isByUser = false),
    Message("7", "That sounds fun! Where to?", MessageStatus.DELIVERED, 1711100700000, isByUser = true),
    Message("8", "Thinking of Jaipur. Have you been?", MessageStatus.READ, 1711100800000, isByUser = false),
    Message("9", "Yeah! It's amazing. You’ll love it!", MessageStatus.SENT, 1711100900000, isByUser = true),
    Message("10", "Excited already! Thanks for the suggestion.", MessageStatus.DELIVERED, 1711101000000, isByUser = false),
    Message("11", "I went there last year. Had an awesome time.", MessageStatus.SENT, 1711101100000, isByUser = true),
    Message("12", "Nice! I should plan it soon.", MessageStatus.DELIVERED, 1711101200000, isByUser = false),
    Message("13", "We can plan together! Would be fun.", MessageStatus.READ, 1711101300000, isByUser = true),
    Message("14", "That sounds like a great idea!", MessageStatus.READ, 1711101400000, isByUser = false),
    Message("15", "Have you tried the food there?", MessageStatus.SENT, 1711101500000, isByUser = true),
    Message("16", "Yes! The Rajasthani Thali is a must-try!", MessageStatus.DELIVERED, 1711101600000, isByUser = false),
    Message("17", "I’m getting hungry just thinking about it.", MessageStatus.READ, 1711101700000, isByUser = true),
    Message("18", "Same here, I could eat a whole thali right now.", MessageStatus.READ, 1711101800000, isByUser = false),
    Message("19", "Maybe we can go for dinner this weekend?", MessageStatus.SENT, 1711101900000, isByUser = true),
    Message("20", "Sounds perfect! Let me know the time.", MessageStatus.DELIVERED, 1711102000000, isByUser = false)
)


