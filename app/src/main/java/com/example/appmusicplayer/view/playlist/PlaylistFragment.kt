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
import androidx.lifecycle.ViewModelProvider
import com.example.appmusicplayer.R
import com.example.appmusicplayer.databinding.FragmentPlaylistBinding
import com.example.appmusicplayer.model.playlist.PlaylistEntity
import com.example.appmusicplayer.model.playlist.PlaylistEntity.Companion.PLAYLIST_FAVOURITE_ID
import com.example.appmusicplayer.view.playlistFolder.PlaylistFolderActivity
import com.example.appmusicplayer.view.search.SearchActivity
import com.example.appmusicplayer.viewmodel.PlaylistViewModel

class PlaylistFragment : Fragment(), onPlaylistClick {
    private var binding: FragmentPlaylistBinding? = null
    private lateinit var adapter: PlaylistAdapter
    private lateinit var viewModel: PlaylistViewModel

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

        adapter = PlaylistAdapter(mutableListOf(), this)
        binding?.rvPlaylist?.adapter = adapter

        viewModel = ViewModelProvider(this)[PlaylistViewModel::class.java]
        viewModel.init(requireContext())

        observeData()

    }

    fun observeData() {
        viewModel.playlistList.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    override fun onCreatePlaylist() {
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
                viewModel.createPlaylist(name)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onClickFavourite() {
        val intent = Intent(requireContext(), PlaylistFolderActivity::class.java)
        intent.putExtra("playlist_id", PLAYLIST_FAVOURITE_ID)
        intent.putExtra("playlist_name", "Favourite")
        intent.putExtra("playlist_is_system", true)
        startActivity(intent)
    }

    override fun onClickPlaylist(playlist: PlaylistEntity) {
        val intent = Intent(requireContext(), PlaylistFolderActivity::class.java)
        intent.putExtra("playlist_id", playlist.id)
        intent.putExtra("playlist_name", playlist.name)
        intent.putExtra("playlist_is_system", playlist.isSystem)
        startActivity(intent)
    }
}