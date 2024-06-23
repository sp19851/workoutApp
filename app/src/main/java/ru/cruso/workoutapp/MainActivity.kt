package ru.cruso.workoutapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.cruso.workoutapp.databinding.ActivityExercisesBinding
import ru.cruso.workoutapp.databinding.ActivityGymexercisBinding
import ru.cruso.workoutapp.databinding.ActivityLevelchooseBinding
import ru.cruso.workoutapp.databinding.ActivityPlacechooseBinding
import ru.cruso.workoutapp.databinding.ActivityProjectmainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityProjectmainBinding
    private lateinit var bindingLevelChoose: ActivityLevelchooseBinding
    private lateinit var bindingPlaceChoose: ActivityPlacechooseBinding
    private lateinit var bindingExercises: ActivityExercisesBinding
    private lateinit var bindingGymExercis: ActivityGymexercisBinding

    private var needTimer = false
    private var seconds_r  = 0
    private var seconds_ind_r  = ""
    private var minutes_r   = 0
    private var minutes_ind_r  = ""
    private var hours_r   = 0
    private var hours_ind_r  = ""

    private val gymExercis = arrayOf("Разминка", "Подъемы ног со скручиванием","Гиперэкстензия","Тяга к груди","Жим штанги лежа","Махи руками",
        "Разгибы рук","Жим платформа");
    private var gymExercisIndex = 0;

    var job: Job? = null // Для управления корутиной

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
            needTimer = true;
            startTimer()
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
                needTimer = false;
                stopTimer()
            }

        }
    }
    private fun updateTimerDisplay(hours: String, minutes: String, seconds: String) {
        // Функция для обновления отображения таймера
        bindingGymExercis.editTextTime.text = Editable.Factory.getInstance().newEditable("$hours:$minutes:$seconds")

    }
    private fun startTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (needTimer) {
                delay(1000) // Неблокирующая задержка на 1 секунду
                // Теперь можно безопасно обновлять UI
                withContext(Dispatchers.Main) {
                    // Ваш код обновления таймера
                    updateTimerDisplay(hours_ind_r, minutes_ind_r, seconds_ind_r)
                }
                // Инкремент времени
                incrementTime()
            }
        }
    }

    private fun incrementTime() {
        seconds_r++
        if (seconds_r >= 60) {
            seconds_r = 0
            minutes_r++
            if (minutes_r >= 60) {
                minutes_r = 0
                hours_r++
            }
        }
        // Форматирование строк
        seconds_ind_r = if (seconds_r < 10) "0$seconds_r" else "$seconds_r"
        minutes_ind_r = if (minutes_r < 10) "0$minutes_r" else "$minutes_r"
        hours_ind_r = "$hours_r"
    }

    // Для остановки таймера и корутины
    fun stopTimer() {
        job?.cancel()
    }
}