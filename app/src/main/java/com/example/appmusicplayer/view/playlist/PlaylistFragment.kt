package com.example.appmusicplayer.view.playlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.FragmentPlaylistBinding
import com.example.appmusicplayer.view.search.SearchActivity

class PlaylistFragment : Fragment() {
    private var binding: FragmentPlaylistBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSearch?.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        createPlaylist()
    }

    fun createPlaylist() {
        binding?.btnCratePlaylist?.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_create_playlist, null)

            val edtName = dialogView.findViewById<EditText>(R.id.edtCreatePlaylist)
            val btnCancel = dialogView.findViewById<TextView>(R.id.btn_cancel)
            val btnSave = dialogView.findViewById<TextView>(R.id.btn_save)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create()

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                val name = edtName.text.toString()
                if (name.isNotEmpty()) {
                    // TODO: xử lý lưu playlist
                    dialog.dismiss()
                }
            }

            dialog.show()
        }
    }
}