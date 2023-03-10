package com.example.tictactoegame.actions

sealed class UserActions {
    object PlayAgainButton: UserActions()
    data class GridTapped(val cell: Int) : UserActions()


}