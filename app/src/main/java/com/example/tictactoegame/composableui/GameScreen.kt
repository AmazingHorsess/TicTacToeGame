package com.example.tictactoegame
import android.app.GameState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.tictactoegame.actions.UserActions
import com.example.tictactoegame.model.BoardCellValue
import com.example.tictactoegame.model.GameStatements
import com.example.tictactoegame.model.VictoryType
import com.example.tictactoegame.ui.theme.BlueCustom
import com.example.tictactoegame.ui.theme.GrayBackground
import com.example.tictactoegame.viewmodel.GameViewModel


@Composable
fun GameScreen(viewModel: GameViewModel) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player: 'O' ${state.playerCircleCount}", fontSize = 16.sp)
            Text(text = "Draw : ${state.drawCount}", fontSize = 16.sp)
            Text(text = "Player: 'X' ${state.playerCrossCount}", fontSize = 16.sp)
        }
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = BlueCustom
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackground),
            contentAlignment = Alignment.Center
        ) {
            GridBase()
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3)
            ){
                viewModel.boardCellItem.forEach {(cell, boardCellValue) ->
                    item{
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) { viewModel.onAction(UserActions.GridTapped(cell)) },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                            AnimatedVisibility(visible =viewModel.boardCellItem[cell] != BoardCellValue.NONE ) {
                                if (boardCellValue == BoardCellValue.CIRCLE ){
                                    Circle()
                                }else if (boardCellValue == BoardCellValue.CROSS){
                                    Cross()
                                }
                                
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f),
            verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = state.IfWon, enter = fadeIn(tween(2000))) {
                    DrawVictoryLine(state = state)

                }

            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.playerTurnHint,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
            Button(
                onClick = {
                          viewModel.onAction(UserActions.PlayAgainButton)
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueCustom,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 16.sp
                )
            }

        }
    }
}
@Composable
fun DrawVictoryLine(
    state: GameStatements
) {
    when (state.victoryType) {
        VictoryType.HORIZONTALLINE1 -> WinHorizontalLine1()
        VictoryType.HORIZONTALLINE2 -> WinHorizontalLine2()
        VictoryType.HORIZONTALLINE3 -> WinHorizontalLine3()
        VictoryType.VERTICALLINE1 -> WinVerticalLine1()
        VictoryType.VERTICALLINE2 -> WinVerticalLine2()
        VictoryType.VERTICALLINE3 -> WinVerticalLine3()
        VictoryType.DISCOUNTLINE1 -> WinDiagonalLine1()
        VictoryType.DISCOUNTLINE2 -> WinDiagonalLine2()
        VictoryType.NONE -> {}
    }
}



