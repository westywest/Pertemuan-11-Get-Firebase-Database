package com.maharanid17.pertemuan11.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maharanid17.pertemuan11.R
import com.maharanid17.pertemuan11.adapter.HomeAdapter
import com.maharanid17.pertemuan11.databinding.FragmentHomeBinding
import com.maharanid17.pertemuan11.model.Mahasiswa

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var adapter : HomeAdapter
    lateinit var databaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(ArrayList())
        binding.rvUser.adapter = adapter
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())

        databaseRef = FirebaseDatabase.getInstance().getReference("mahasiswa")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mahasiswaList = ArrayList<Mahasiswa>()
                for (snapshot in dataSnapshot.children) {
                    val mahasiswa = snapshot.getValue(Mahasiswa::class.java)
                    mahasiswa?.let { mahasiswaList.add(it) }
                }
                adapter.setData(mahasiswaList)
            }

            override fun onCancelled(error: DatabaseError) {
                //Handler error
            }

        })
    }
}