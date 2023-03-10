package com.example.tictactoegame.model

data class GameStatements(
    val playerCircleCount: Int = 0,
    val playerCrossCount: Int = 0,
    val drawCount: Int = 0,
    val playerTurnHint: String = "Player 'O' turn",
    val currentTurn: BoardCellValue = BoardCellValue.CIRCLE,
    val victoryType: VictoryType = VictoryType.NONE,
    val IfWon: Boolean = false

)

enum class BoardCellValue{
    CIRCLE,
    CROSS,
    NONE

}
enum class VictoryType{
    HORIZONTALLINE1,
    HORIZONTALLINE2,
    HORIZONTALLINE3,
    VERTICALLINE1,
    VERTICALLINE2,
    VERTICALLINE3,
    DISCOUNTLINE1,
    DISCOUNTLINE2,
    NONE


}