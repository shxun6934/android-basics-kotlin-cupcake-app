package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    private val _quantity: MutableLiveData<Int> = MutableLiveData()
    val quantity: LiveData<Int> get() = _quantity

    private val _flavor: MutableLiveData<String> = MutableLiveData()
    val flavor: LiveData<String> get() = _flavor

    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> get() = _date

    val dateOptions = getPickupOptions()

    private val _price: MutableLiveData<Double> = MutableLiveData()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance(Locale.JAPAN).format(it)
    }

    init {
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun hasNoFlavorSet(): Boolean = _flavor.value.isNullOrBlank()

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("MM/dd (E)", Locale.JAPAN)
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }
}