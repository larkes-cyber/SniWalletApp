package com.example.tonwalletapp.presentation.screen.congrats_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tonwalletapp.R
import com.example.tonwalletapp.presentation.navigation.Screen
import com.example.tonwalletapp.presentation.component.InfoScreenSkeleton
import com.example.tonwalletapp.presentation.component.TopBarApp
import com.example.tonwalletapp.ui.theme.AppTheme
import com.example.tonwalletapp.until.Constants.ProceedBtnText
import com.example.tonwalletapp.until.Constants.TonCongratsText
import com.example.tonwalletapp.until.Constants.TonCongratsTitle

@Composable
fun CongratsScreen(
    navController: NavController,
    viewModel: CongratsViewModel
) {

    val walletUIState by viewModel.walletUIState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(walletUIState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if(walletUIState.finished) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.background)
                    .padding(horizontal = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                InfoScreenSkeleton(
                    image = R.drawable.ton_congrats_frame,
                    title = TonCongratsTitle,
                    subtitle = TonCongratsText,
                    btnTitle = ProceedBtnText,
                    visibleBtn = true,
                    visibleOptionalBtn = false,
                    onBtnClick = {
                        navController.navigate(Screen.RecoveryPhraseScreen.route)
                    }
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            TopBarApp() {
                navController.popBackStack()
            }
        }
        if(walletUIState.error.isNotEmpty()){
            Text(
                text = walletUIState.error,
                style = MaterialTheme.typography.caption.copy(color = Color.Red),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}