package edu.vt.cs5254.dreamcatcher

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.view.doOnLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import edu.vt.cs5254.dreamcatcher.databinding.FragmentPhotoDialogBinding
import java.io.File

class PhotoDialogFragment : DialogFragment(){


    private val args: PhotoDialogFragmentArgs by navArgs()

    //have to late initialize
    private var photoFileName: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentPhotoDialogBinding.inflate(layoutInflater)
        photoFileName=args.dreamPhotoFilename

//        if(binding.photoDetail.tag!= photoFileName){
            val photoFile =
                File(requireContext().applicationContext.filesDir, photoFileName)

            if(photoFile.exists()){
                binding.root.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    binding.photoDetail.setImageBitmap(scaledBitmap)
                    binding.photoDetail.tag = photoFileName
                }
            }else{
                binding.photoDetail.setImageBitmap(null)
                binding.photoDetail.tag = null
            }
//        }


        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .show()
    }

}