package com.pathok.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pathok.app.R

val HindFamily = FontFamily(
    Font(R.font.hind_regular, FontWeight.Normal),
    Font(R.font.hind_bold, FontWeight.Bold)
)

val PathokTypography = Typography(
    bodyLarge = TextStyle(fontFamily = HindFamily, fontWeight = FontWeight.Normal, fontSize = 18.sp, lineHeight = 28.sp),
    titleLarge = TextStyle(fontFamily = HindFamily, fontWeight = FontWeight.Bold, fontSize = 22.sp, lineHeight = 30.sp),
    titleMedium = TextStyle(fontFamily = HindFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, lineHeight = 26.sp),
    labelSmall = TextStyle(fontFamily = HindFamily, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 18.sp)
)