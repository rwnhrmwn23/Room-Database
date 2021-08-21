package com.onedev.roomdatabasa.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.onedev.roomdatabasa.R
import com.onedev.roomdatabasa.databinding.FragmentAddBinding
import com.onedev.roomdatabasa.model.User
import com.onedev.roomdatabasa.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding?.btnSave?.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val firstName = binding?.edtFirstName?.text.toString()
        val lastName = binding?.edtLastName?.text.toString()
        val age = binding?.edtAge?.text

        if (inputCheck(firstName, lastName, age)) {
            // Create User Object
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))

            // Add Data to Database
            viewModel.addUser(user)
            Toast.makeText(requireContext(), "Success Add Data", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext(), "Please Full All Field", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable?): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age?.isEmpty() == true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}