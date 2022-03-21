package com.example.cupcake.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModelTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun flavor_vanilla() {
        val viewModel = OrderViewModel()
        viewModel.flavor.observeForever {}
        viewModel.setFlavor("Vanilla")
        assertEquals("Vanilla", viewModel.flavor.value)
    }

    @Test
    fun has_no_flavor() {
        val viewModel = OrderViewModel()
        assertEquals(true, viewModel.hasNoFlavorSet())
        viewModel.setFlavor("Vanilla")
        assertEquals(false, viewModel.hasNoFlavorSet())
    }

    @Test
    fun price_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        viewModel.setQuantity(12)
        assertEquals("￥27", viewModel.price.value)
    }

    @Test
    fun cancel_order() {
        val viewModel = OrderViewModel()
        viewModel.quantity.observeForever {}
        viewModel.flavor.observeForever {}
        viewModel.date.observeForever {}
        viewModel.price.observeForever {}

        viewModel.setQuantity(12)
        viewModel.setFlavor("Vanilla")
        viewModel.setDate("03/01 (月)")

        assertEquals(12, viewModel.quantity.value)
        assertEquals("Vanilla", viewModel.flavor.value)
        assertEquals("03/01 (月)", viewModel.date.value)
        assertEquals("￥24", viewModel.price.value)

        viewModel.resetOrder()

        val formatter = SimpleDateFormat("MM/dd (E)", Locale.JAPAN)
        val calendar = Calendar.getInstance()
        val today = formatter.format(calendar.time)

        assertEquals(0, viewModel.quantity.value)
        assertEquals("", viewModel.flavor.value)
        assertEquals(today, viewModel.date.value)
        assertEquals("￥0", viewModel.price.value)
    }
}