package com.deco.licuadora_animada

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.deco.licuadora_animada.ui.theme.LicuadoraAnimadaTheme
import com.deco.licuadora_animada.ui.theme.Orange15

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LicuadoraAnimadaTheme {
                var selected by remember { mutableStateOf(false) }
                val licuadoraSound = MediaPlayer.create(LocalContext.current, R.raw.sound_licuadora)
                licuadoraSound.isLooping = true
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = Orange15) {
                    ImageExample(
                        selected = selected,
                        onClick = {
                            selected = !selected
                            if (selected) {
                                licuadoraSound.start()
                                println("start")
                            } else {
                                println("pause")
                                licuadoraSound.stop()
                                licuadoraSound.prepare()
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun ImageExample(
        modifier: Modifier = Modifier,
        selected: Boolean,
        idImg: Int =
            if (selected) R.drawable.licuadora
            else R.drawable.licuadoraimg,
        onClick: () -> Unit
    ) {
        val clickEnabled = remember { mutableStateOf(true) }
        val licuadoraButton = MediaPlayer.create(LocalContext.current, R.raw.boton)
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()



        Image(
            painter = rememberAsyncImagePainter(idImg, imageLoader),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = clickEnabled.value) {
                    licuadoraButton.start()
                    if (idImg != R.drawable.licuadora) {
                        Handler().postDelayed({
                        }, licuadoraButton.duration.toLong())
                    } else {
//                        licuadoraSound.stop()
                    }
                    onClick()
                }
        )
    }
}