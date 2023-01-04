package edu.vt.cs5254.fancygallery

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.imageLoader
import edu.vt.cs5254.fancygallery.databinding.FragmentGalleryBinding
import kotlinx.coroutines.launch

class GalleryFragment : Fragment(){

    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "binding is null, can not access!"
        }

    //use activityViewModels -- attach viewmodel to activity/ not fragment
    //so vm is accessible from map fragment
    private val vm: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater,container,false)
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3)

        requireActivity().addMenuProvider(object : MenuProvider {
            //load refresh icon
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_gallery, menu)
            }

            //when click
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.reload_menu -> {
                        requireContext().imageLoader.memoryCache?.clear()
                        vm.reloadGalleryItems()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start using api in coroutine lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.galleryItems.collect{ items ->
                    binding.photoGrid.adapter = GalleryItemAdapter(items){ photoPageUri ->
                        findNavController().navigate(
                            GalleryFragmentDirections.showPhoto(
                                photoPageUri
                            )
                        )
                    }
                }
            }
        }
    }

}