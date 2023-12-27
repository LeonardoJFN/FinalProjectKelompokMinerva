package com.umn.finalprojectkelompokminerva.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class User(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val image: String = ""
)

class NearbyFragment : Fragment() {

    private val viewModel: NearbyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_nearby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view.findViewById<ComposeView>(R.id.lazyColumnView)
        composeView.setContent {
            NearbyScreen(viewModel)
        }
    }
}

@Composable
fun NearbyScreen(viewModel: NearbyViewModel) {
    val users by viewModel.users.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Nearby Users")
        users.forEach { user ->
            Text(text = user.name)
            // You can also display other user properties here
        }
    }
}

class NearbyViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val usersRef = db.collection("users")

    val users = usersRef
        .orderBy("location") // You can use any criteria to sort the nearby users
        .limit(10) // You can limit the number of users to display
        .asFlow()
        .map { querySnapshot ->
            querySnapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<User>()
            }
        }
        .flowOn(Dispatchers.IO)
        .asLiveData()
}
