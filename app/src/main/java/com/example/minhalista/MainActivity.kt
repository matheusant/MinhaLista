package com.example.minhalista

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.VideoView
import com.example.minhalista.databinding.ActivityMainBinding
import java.net.URI

class MainActivity : AppCompatActivity() {

    val video_sample: String = "https://www.youtube.com/watch?v=IEXSGmX8YJo"

    private lateinit var videoView: VideoView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        videoView = binding.vvPlayer

        var ctrl = MediaController(this)
        ctrl.setMediaPlayer(videoView)
        videoView.setMediaController(ctrl)

    }

    override fun onStart() {
        super.onStart()
        initiVideo()
    }

    private fun initiVideo() {
        var uri = getMedia(video_sample)

        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener {
            videoView.start()
        }
    }

    private fun getMedia(videoSample: String): Uri? {
        if (URLUtil.isValidUrl(videoSample)) {
            return Uri.parse(videoSample)
        }
        else {
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + videoSample)
        }
    }

}