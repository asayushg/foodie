package ayush.foodie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ayush.foodie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}