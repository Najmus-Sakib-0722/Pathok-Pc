package com.pathok.app.theme

import androidx.compose.ui.graphics.Color

data class PathokColors(
    val bg: Color,
    val bg2: Color,
    val text: Color,
    val text2: Color,
    val barBg: Color,
    val divider: Color,
    val accent: Color,
    val accent2: Color,
    val accentGlow: Color,
    val drawerBg: Color,
    val surfaceBorder: Color,
    val hoverBg: Color,
    val cardBg: Color,
    val muted: Color,
    val shadow: Color
)

// ডিফল্ট থিম (sepia/paper — index.html-এর :root)
val SepiaColors = PathokColors(
    bg = Color(0xFFFDF8F0),
    bg2 = Color(0xFFF5EDE0),
    text = Color(0xFF2C1E10),
    text2 = Color(0xFF6B5240),
    barBg = Color(0xEBFDF8F0),
    divider = Color(0xFFC8AA88),
    accent = Color(0xFF8B5E3C),
    accent2 = Color(0xFFC4946A),
    accentGlow = Color(0x268B5E3C),
    drawerBg = Color(0xFFFDF8F0),
    surfaceBorder = Color(0xFFDDD0BC),
    hoverBg = Color(0xFFEDE3D5),
    cardBg = Color(0xFFFFFFFF),
    muted = Color(0xFF9A7F68),
    shadow = Color(0x1F8B5E3C)
)

val LightColors = PathokColors(
    bg = Color(0xFFFFFFFF),
    bg2 = Color(0xFFF7F7F7),
    text = Color(0xFF1A1A1A),
    text2 = Color(0xFF555555),
    barBg = Color(0xEBFFFFFF),
    divider = Color(0xFFD0D0D0),
    accent = Color(0xFF7A4A28),
    accent2 = Color(0xFFB5803C),
    accentGlow = Color(0x1A7A4A28),
    drawerBg = Color(0xFFFFFFFF),
    surfaceBorder = Color(0xFFE5E5E5),
    hoverBg = Color(0xFFF2F2F2),
    cardBg = Color(0xFFFAFAFA),
    muted = Color(0xFF888888),
    shadow = Color(0x0F000000)
)

val DarkColors = PathokColors(
    bg = Color(0xFF121212),
    bg2 = Color(0xFF1C1C1C),
    text = Color(0xFFE8DDD0),
    text2 = Color(0xFFA09080),
    barBg = Color(0xF2121212),
    divider = Color(0xFF3C3028),
    accent = Color(0xFFC4946A),
    accent2 = Color(0xFFE0B88A),
    accentGlow = Color(0x26C4946A),
    drawerBg = Color(0xFF1C1C1C),
    surfaceBorder = Color(0xFF2E2A25),
    hoverBg = Color(0xFF252220),
    cardBg = Color(0xFF1E1E1E),
    muted = Color(0xFF7A6A5A),
    shadow = Color(0x66000000)
)

val NightColors = PathokColors(
    bg = Color(0xFF0D0D14),
    bg2 = Color(0xFF12121C),
    text = Color(0xFFC8D4E8),
    text2 = Color(0xFF7A88A8),
    barBg = Color(0xF20D0D14),
    divider = Color(0xFF252538),
    accent = Color(0xFF7B9FD4),
    accent2 = Color(0xFF5B7FC4),
    accentGlow = Color(0x267B9FD4),
    drawerBg = Color(0xFF12121C),
    surfaceBorder = Color(0xFF222230),
    hoverBg = Color(0xFF1A1A28),
    cardBg = Color(0xFF14141E),
    muted = Color(0xFF5A6878),
    shadow = Color(0x80000000)
)

val ForestColors = PathokColors(
    bg = Color(0xFF0F1A12),
    bg2 = Color(0xFF141F16),
    text = Color(0xFFC8E0C0),
    text2 = Color(0xFF7A9870),
    barBg = Color(0xF20F1A12),
    divider = Color(0xFF243028),
    accent = Color(0xFF6AB870),
    accent2 = Color(0xFF4A9850),
    accentGlow = Color(0x266AB870),
    drawerBg = Color(0xFF141F16),
    surfaceBorder = Color(0xFF1F2E22),
    hoverBg = Color(0xFF1A2A1C),
    cardBg = Color(0xFF131C15),
    muted = Color(0xFF506050),
    shadow = Color(0x80000000)
)

// Spacing ও Radius token (index.html-এর --radius / --radius-sm থেকে, dp এককে)
object PathokDimens {
    val radiusLarge = 18
    val radiusSmall = 10
    val spaceXs = 4
    val spaceSm = 8
    val spaceMd = 16
    val spaceLg = 24
    val spaceXl = 32
}