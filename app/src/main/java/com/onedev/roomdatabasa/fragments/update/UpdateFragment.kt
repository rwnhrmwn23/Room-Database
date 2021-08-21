package com.onedev.roomdatabasa.fragments.update

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
import androidx.navigation.fragment.navArgs
import com.onedev.roomdatabasa.R
import com.onedev.roomdatabasa.databinding.FragmentUpdateBinding
import com.onedev.roomdatabasa.model.User
import com.onedev.roomdatabasa.viewmodel.UserViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding
    private val args: UpdateFragmentArgs by navArgs()

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val data = args.currentUser
        binding?.edtFirstName?.setText(data.firstName)
        binding?.edtLastName?.setText(data.lastName)
        binding?.edtAge?.setText(data.age.toString())

        binding?.btnUpdate?.setOnClickListener {
            updateDataToDatabase()
        }
    }

    private fun updateDataToDatabase() {
        val firstName = binding?.edtFirstName?.text.toString()
        val lastName = binding?.edtLastName?.text.toString()
        val age = binding?.edtAge?.text

        if (inputCheck(firstName, lastName, age)) {
            // Create User Object
            val user = User(args.currentUser.id, firstName, lastName, Integer.parseInt(age.toString()))

            // Update Data to Database
            viewModel.updateUser(user)
            Toast.makeText(requireContext(), "Success Update Data", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

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