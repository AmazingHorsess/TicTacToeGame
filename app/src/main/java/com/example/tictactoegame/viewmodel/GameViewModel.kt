package com.example.tictactoegame.viewmodel

import android.app.GameState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tictactoegame.actions.UserActions
import com.example.tictactoegame.model.BoardCellValue
import com.example.tictactoegame.model.GameStatements
import com.example.tictactoegame.model.VictoryType

class GameViewModel:ViewModel() {
    var state by mutableStateOf(GameStatements())

    val boardCellItem: MutableMap<Int, BoardCellValue> = mutableMapOf(
        1 to BoardCellValue.NONE,
        2 to BoardCellValue.NONE,
        3 to BoardCellValue.NONE,
        4 to BoardCellValue.NONE,
        5 to BoardCellValue.NONE,
        6 to BoardCellValue.NONE,
        7 to BoardCellValue.NONE,
        8 to BoardCellValue.NONE,
        9 to BoardCellValue.NONE,
    )


    fun onAction(action: UserActions){
        when (action){
            is UserActions.GridTapped -> {
                addValueToGrid(action.cell)
            }
            UserActions.PlayAgainButton -> {
                gameReset()
            }
        }

    }

    private fun gameReset() {
        boardCellItem.forEach { (i, _) ->
            boardCellItem[i] = BoardCellValue.NONE
        }
        state = state.copy(
            playerTurnHint = "Player 'O' turn",
            currentTurn = BoardCellValue.CIRCLE,
            victoryType = VictoryType.NONE,
            IfWon = false)
    }

    private fun addValueToGrid(cell: Int) {
        if (boardCellItem[cell] != BoardCellValue.NONE) {
            return
        }
        if (state.currentTurn == BoardCellValue.CIRCLE) {
            boardCellItem[cell] = BoardCellValue.CIRCLE
            if (checkVictory(BoardCellValue.CIRCLE)) {
                state = state.copy(
                    playerTurnHint =  "Player 'O' Won",
                    playerCircleCount = state.playerCircleCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    IfWon = true
                )
            } else if (hasBoardFull()) {
                state = state.copy(
                    playerTurnHint = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    playerTurnHint = "Player 'X' turn",
                    currentTurn = BoardCellValue.CROSS
                )
            }
        } else if (state.currentTurn == BoardCellValue.CROSS) {
            boardCellItem[cell] = BoardCellValue.CROSS
            if (checkVictory(BoardCellValue.CROSS)) {
                state = state.copy(
                    playerTurnHint = "Player 'X' Won",
                    playerCrossCount = state.playerCrossCount + 1,
                    currentTurn = BoardCellValue.NONE,
                    IfWon = true
                )
            } else if (hasBoardFull()) {
                state = state.copy(
                    playerTurnHint = "Game Draw",
                    drawCount = state.drawCount + 1
                )
            } else {
                state = state.copy(
                    playerTurnHint = "Player 'O' turn",
                    currentTurn = BoardCellValue.CIRCLE
                )
            }
        }
    }

    private fun checkVictory(boardValue: BoardCellValue): Boolean {
        when{
            boardCellItem[1] == boardValue && boardCellItem[2] == boardValue && boardCellItem[3] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTALLINE1)
                return true
            }
            boardCellItem[4] == boardValue && boardCellItem[5] == boardValue && boardCellItem[6] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTALLINE2)
                return true
            }
            boardCellItem[7] == boardValue && boardCellItem[8] == boardValue && boardCellItem[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.HORIZONTALLINE3)
                return true
            }
            boardCellItem[1] == boardValue && boardCellItem[4] == boardValue && boardCellItem[7] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICALLINE1)
                return true
            }
            boardCellItem[2] == boardValue && boardCellItem[5] == boardValue && boardCellItem[8] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICALLINE2)
                return true
            }
            boardCellItem[3] == boardValue && boardCellItem[6] == boardValue && boardCellItem[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.VERTICALLINE3)
                return true
            }
            boardCellItem[1] == boardValue && boardCellItem[5] == boardValue && boardCellItem[9] == boardValue -> {
                state = state.copy(victoryType = VictoryType.DISCOUNTLINE1)
                return true
            }
            boardCellItem[3] == boardValue && boardCellItem[5] == boardValue && boardCellItem[7] == boardValue -> {
                state = state.copy(victoryType = VictoryType.DISCOUNTLINE2)
                return true
            }
            else -> return false
        }

    }

    private fun hasBoardFull(): Boolean {
        if (boardCellItem.containsValue(BoardCellValue.NONE))
            return false
        return true

    }

}