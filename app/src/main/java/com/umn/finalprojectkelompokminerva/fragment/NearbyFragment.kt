
package com.umn.finalprojectkelompokminerva.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.umn.finalprojectkelompokminerva.R
import com.umn.finalprojectkelompokminerva.model.UserModel
class UsersAdapter(private val users: List<UserModel>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userName: TextView = itemView.findViewById(R.id.userName)
    private val userLocation: TextView = itemView.findViewById(R.id.user_location)

    fun bind(user: UserModel) {
        userName.text = user.name
        userLocation.text = user.location
    }
}



class NearbyFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var usersList: MutableList<UserModel>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nearby, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        usersList = mutableListOf()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()

                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    user?.let { usersList.add(it) }
                }

                recyclerView.adapter = UsersAdapter(usersList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
