package com.adityagupta.foodorderingapp.data

class CategoryItem(name: String, category: String, price: Double) {

    var name: String = name
        set(value) {
            require(value.trim().isNotEmpty()) { "NULLLL" }
            field = value
        }

    var category: String = category
        set(value) {
            require(value.trim().isNotEmpty()) { "VEG" }
            field = value
        }

    var price: Double = price
        set(value) {
            require(value.isFinite()) { 0.0 }
            field = value
        }
}