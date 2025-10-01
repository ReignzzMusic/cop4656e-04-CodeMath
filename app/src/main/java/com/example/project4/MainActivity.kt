package com.example.project4

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.project4.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private var currentTipPercent: Int = 0;
    private var currentBasePayment: Float = 0.0f;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater);
        binding.Slider.addOnChangeListener { slider, values , user ->
            var currentVal = binding.Slider.values[0]

            currentTipPercent = currentVal.toInt();

            binding.PercentLabel.setText("${currentVal}%");
            processTotal();
        }

        binding.BasePayTextbox.addTextChangedListener { newText ->
            if (newText.toString() == "") {
                currentBasePayment = 0.0f;
                processTotal();
            } else {
                currentBasePayment = newText.toString().toFloat()
            }
            processTotal();
        }

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun processTotal()
    {
        var decimalFormat = DecimalFormat("0.##");
        var tipAmount: Float = (currentTipPercent.toFloat()/100) * currentBasePayment;
        var total: Float = tipAmount+currentBasePayment;

        if (currentBasePayment == 0.0f) {
            total = 0.0f;
            tipAmount = 0.0f;
        }

        binding.TipAmount.setText(decimalFormat.format(tipAmount));
        binding.TotalAmount.setText(decimalFormat.format(total));
    }


}