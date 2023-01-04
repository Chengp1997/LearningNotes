package edu.vt.cs5254.fancygallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.vt.cs5254.fancygallery.databinding.FragmentGalleryBinding
import edu.vt.cs5254.fancygallery.databinding.FragmentPhotoPageBinding

class PhotoPageFragment : Fragment() {
    private val args: PhotoPageFragmentArgs by navArgs()

    private var _binding: FragmentPhotoPageBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "binding is null, can not access!"
        }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoPageBinding.inflate(
            inflater,
            container,
            false
        )

        binding.apply {
            progressBar.max = 100
            //webview client is used to respond to rendering events
            webView.apply {
                settings.javaScriptEnabled = true
                //1. need to create client first, then load url
                webViewClient = WebViewClient()
                loadUrl(args.photoPageUri.toString())
                //used chrome client for style
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        if (newProgress == 100){
                            progressBar.visibility = View.GONE
                        }else{
                            progressBar.visibility = View.VISIBLE
                            progressBar.progress = newProgress
                        }
                    }

                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        setSubtitle(title)
                    }

                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setSubtitle(null)
        _binding = null
    }

    private fun setSubtitle(title: String?){
        val parent = requireActivity() as AppCompatActivity
        parent.supportActionBar?.subtitle = title
    }
}