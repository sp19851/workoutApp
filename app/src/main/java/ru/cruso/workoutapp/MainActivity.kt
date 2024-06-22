package ru.cruso.workoutapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.cruso.workoutapp.databinding.ActivityExercisesBinding
import ru.cruso.workoutapp.databinding.ActivityGymexercisBinding
import ru.cruso.workoutapp.databinding.ActivityLevelchooseBinding
import ru.cruso.workoutapp.databinding.ActivityPlacechooseBinding
import ru.cruso.workoutapp.databinding.ActivityProjectmainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityProjectmainBinding
    private lateinit var bindingLevelChoose: ActivityLevelchooseBinding
    private lateinit var bindingPlaceChoose: ActivityPlacechooseBinding
    private lateinit var bindingExercises: ActivityExercisesBinding
    private lateinit var bindingGymExercis: ActivityGymexercisBinding

    private val gymExercis = arrayOf("Разминка", "Подъемы ног со скручиванием","Гиперэкстензия","Тяга к груди","Жим штанги лежа","Махи руками",
        "Разгибы рук","Жим платформа");
    private var gymExercisIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityProjectmainBinding.inflate(layoutInflater)
        bindingLevelChoose = ActivityLevelchooseBinding.inflate(layoutInflater)
        bindingPlaceChoose = ActivityPlacechooseBinding.inflate(layoutInflater)
        bindingExercises = ActivityExercisesBinding.inflate(layoutInflater)
        bindingGymExercis = ActivityGymexercisBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        /*enableEdgeToEdge()
        setContentView(R.layout.activity_projectmain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        bindingMain.firstButton.setOnClickListener{
            setContentView(bindingLevelChoose.root)
        }
        bindingLevelChoose.btnLv0.setOnClickListener{
            setContentView(bindingPlaceChoose.root)
        }
        bindingPlaceChoose.btnGym.setOnClickListener{
            setContentView(bindingExercises.root)
        }
        bindingExercises.startGym.setOnClickListener{
            setContentView(bindingGymExercis.root)
            bindingGymExercis.gymText.text = gymExercis[gymExercisIndex]

            bindingGymExercis.gymText.visibility = View.VISIBLE;
            bindingGymExercis.gymDoneText.visibility = View.INVISIBLE;
        }
        bindingGymExercis.gymBtnNextExercis.setOnClickListener{
            gymExercisIndex ++;
            if (gymExercisIndex <= gymExercis.count() - 1 )
            {
                bindingGymExercis.gymText.text = gymExercis[gymExercisIndex]

            }
            else
            {

                bindingGymExercis.gymText.visibility = View.INVISIBLE;
                bindingGymExercis.gymBtnNextExercis.visibility = View.INVISIBLE;

                bindingGymExercis.gymDoneText.visibility = View.VISIBLE;
            }

        }
    }
}