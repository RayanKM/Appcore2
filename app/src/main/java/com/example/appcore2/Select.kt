package com.example.appcore2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.appcore2.databinding.ActivityMainBinding
import com.example.appcore2.databinding.ActivitySelectBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import java.util.Calendar

class Select : AppCompatActivity() {
    private lateinit var binding: ActivitySelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var total = 0
        var days = 1
        val selectedItem = intent.getParcelableExtra<DataModel>("selectedItem")
        binding.price.text = "$${selectedItem?.price}"

        binding.selector.addOnChangeListener { slider, value, fromUser ->
            // 'value' contains the selected value from the Slider
            // You can use it as needed
            // For example, you can display it in a TextView
            days = value.toInt()
            total = selectedItem?.price?.times(value)!!.toInt()
            binding.price.text = "$${total}"
        }

        binding.save.setOnClickListener {
            if (days == 0){
                MotionToast.createColorToast(
                    this,
                    "Error!",
                    "Must select some days",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(
                        this,
                        www.sanju.motiontoast.R.font.helvetica_regular
                    )
                )
            }else{
                val currentDate = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = dateFormat.format(currentDate)
                val calendar = Calendar.getInstance()
                calendar.time = dateFormat.parse(formattedDate)
                calendar.add(Calendar.DAY_OF_YEAR, days)
                val dateAfterDays = calendar.time
                val formattedDateAfterDays = dateFormat.format(dateAfterDays)

                // Update the borrow status of the selected item
                selectedItem?.borrow = "Due back $formattedDateAfterDays"

                // Create an intent to send the updated item back to MainActivity
                val intent = Intent()

                // Pass the updated item as an extra
                intent.putExtra("updatedItem", selectedItem)

                // Set the result code to indicate success
                setResult(RESULT_OK, intent)

                // Finish SecondActivity and return to MainActivity
                finish()
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        super.onBackPressed() // This will finish the current activity and go back to the main activity
    }
}