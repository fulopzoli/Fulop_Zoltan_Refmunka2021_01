package com.example.fulop_zoltan_simplexion.DataModels.Json

data class Current(
    val condition: Condition,
    val temp_c: Double,
    val wind_dir: String,
    val wind_kph: Double,
)