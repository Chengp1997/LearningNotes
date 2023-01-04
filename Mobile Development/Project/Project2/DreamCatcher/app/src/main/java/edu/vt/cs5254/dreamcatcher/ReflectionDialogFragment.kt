package edu.vt.cs5254.dreamcatcher

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import edu.vt.cs5254.dreamcatcher.databinding.FragmentReflectionDialogBinding

class ReflectionDialogFragment : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentReflectionDialogBinding.inflate(layoutInflater)

        //if positive -- add and update
        //if cancel, ignore
        val positiveListener = DialogInterface.OnClickListener { _, _  ->
            val resultText = binding.reflectionText.text.toString() //result to be sent back
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to resultText)// set the result, and sent back
            )
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(R.string.reflection_dialog_title)
            .setPositiveButton(R.string.reflection_dialog_positive, positiveListener)//add listener
            .setNegativeButton(R.string.reflection_dialog_negative, null)
            .show()
    }

    companion object {
        const val REQUEST_KEY = "REQUEST_KEY"
        const val BUNDLE_KEY = "BUNDLE_KEY"
    }
}