package com.example.gamblingapp


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


//In this part I added the click events on the images in the menu
//We need to create settings class

//Im not sure about what we gona add dice, slot and more games section because of that
//im doing the same staff which i did with settings and roulette part
class GameMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_menu)


        // Roulette part

        val rouletteGame = findViewById<ImageView>(R.id.rouletteGame)
        rouletteGame.setOnClickListener {
            // Starting the RouletteActivity when clicked
            val intent = Intent(this,RouletteActivity::class.java)
            startActivity(intent)
        }
        val slotGame: ImageView = findViewById(R.id.slotGame)
        slotGame.setOnClickListener {
            val intent = Intent(this, SlotActivity::class.java)
            startActivity(intent)

        }
        val blackJack: ImageView = findViewById(R.id.blackjackGame)
        blackJack.setOnClickListener {
            val intent = Intent(this, BlackjackActivity::class.java)
            startActivity(intent)

        }
    }



}


        //Settings part

 /*
        val settingsIcon: ImageView = findViewById(R.id.settingsIcon)
        settingsIcon.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}




}

        //Dice part

        val diceGame : ImageView = findViewById(R.id.diceGame)
        diceGame.setOnClickListener {
            val intent = Intent(this, DiceActivity::class.java)
            startActivity(intent)
        }

        )

        }
        //More coming section
        val moreComing: ImageView = findViewById(R.id.moreComing)
        moreComing.setOnClickListener {
            val intent = Intent(this, MoreComingActivity::class.java)
            startActivity(intent)
        }


}

  */
