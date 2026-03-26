package com.potaninpm.navigation

data class NavItemData(
    val item: MainMenuBottomNavItems,
    val label: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)