package com.onedev.roomdatabasa.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.roomdatabasa.R
import com.onedev.roomdatabasa.adapter.ListAdapter
import com.onedev.roomdatabasa.databinding.FragmentListBinding
import com.onedev.roomdatabasa.viewmodel.UserViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding?.floatingActionButton?.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        val adapter = ListAdapter()
        binding?.rvList?.adapter = adapter
        binding?.rvList?.layoutManager = LinearLayoutManager(requireContext())

        viewModel.readAllData.observe(viewLifecycleOwner, {
            adapter.setData(it)
            binding?.rvList?.smoothScrollToPosition(adapter.itemCount - 1)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAllUser()
                Toast.makeText(requireContext(), "Success Delete All Data", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete All Data")
            .setMessage("Are you sure you want to delete all data ?")
            .create()
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}