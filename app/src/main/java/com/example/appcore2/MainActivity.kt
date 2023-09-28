package com.example.appcore2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import com.example.appcore2.databinding.ActivityMainBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val carList = mutableListOf<DataModel>()
    private var currentIndex = 0
    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation
    private lateinit var selectActivityResultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize animations here
        inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        // Populate the carList
        carList.add(DataModel("Symbol", "A practical compact sedan known for its efficiency and space.", 50, R.drawable.symbol, "Renault", "2016", "102Hp", 3.5,""))
        carList.add(DataModel("Picanto", "A nimble city car with modern features and easy parking.", 65, R.drawable.picanto, "KIA", "2018", "67Hp", 4.0,""))
        carList.add(DataModel("208", "A high-performance hatchback for speed enthusiasts.", 90, R.drawable.p, "Peugeot", "2020", "75Hp", 4.5,""))
        carList.add(DataModel("Golf 8 R", "A stylish and efficient urban compact car.", 120, R.drawable.golf8r, "Volkswagen", "2021", "315Hp", 5.0,""))

        // Initialize the viewFlipper with the first car
        addCarDetailView(carList[currentIndex])

        selectActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    MotionToast.createColorToast(
                        this,
                        "Borrowing completed successfully",
                        "Good choice!",
                        MotionToastStyle.SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(
                            this,
                            www.sanju.motiontoast.R.font.helvetica_regular
                        )
                    )
                    // Handle the result data from the Select activity
                    val updatedItem = data.getParcelableExtra<DataModel>("updatedItem")
                    if (updatedItem != null) {
                        // Update the UI or carList based on the received data
                        carList[currentIndex] = updatedItem
                        binding.borrow.setBackgroundResource(R.drawable.shape2)
                        binding.borrow.text = updatedItem.borrow
                    }
                }
            }
            else if (result.resultCode == RESULT_CANCELED) {
                // Handle the case when the Select activity was canceled
                MotionToast.createColorToast(
                    this,
                    "Back to menu",
                    "Keep exploring for the right car",
                    MotionToastStyle.INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular)
                )
            }
        }

        // Set click listeners for Next and Previous buttons
        binding.next.setOnClickListener {
            if (currentIndex < carList.size - 1) {
                currentIndex++
                addCarDetailView(carList[currentIndex])
                binding.viewFlipper.showNext()
            }
        }

        binding.previous.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                addCarDetailView(carList[currentIndex])
                binding.viewFlipper.showPrevious()
            }
        }
        binding.borrow.setOnClickListener {
            val intent = Intent(this, Select::class.java)
            intent.putExtra("selectedItem", carList[currentIndex])
            selectActivityResultLauncher.launch(intent)
        }
    }

    fun addCarDetailView(car: DataModel) {
        binding.viewFlipper.inAnimation = inAnimation
        binding.viewFlipper.outAnimation = outAnimation
        val imageView = ImageView(this)
        imageView.setImageResource(car.image)
        imageView.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        binding.viewFlipper.addView(imageView)
        // Update car details
        binding.carname.text = car.name
        binding.cardis.text = car.description
        binding.price.text = "$${car.price}"
        binding.brand.text = "Brand: ${car.brand}"
        binding.hp.text = "${car.hp}"
        binding.year.text = "Year: ${car.year}"
        binding.ratingBar.rating = car.rating.toFloat()
        if (car.borrow != ""){
            binding.borrow.text = car.borrow
            binding.borrow.setBackgroundResource(R.drawable.shape2)
        }else{
            binding.borrow.text = "Borrow"
            binding.borrow.setBackgroundResource(R.drawable.shape)
        }
    }
}
