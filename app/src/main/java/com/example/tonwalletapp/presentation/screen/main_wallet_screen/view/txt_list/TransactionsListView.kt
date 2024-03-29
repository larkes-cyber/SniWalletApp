package com.example.tonwalletapp.presentation.screen.main_wallet_screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tonwalletapp.domain.model.TransactionDetail
import com.example.tonwalletapp.presentation.component.TransactionItemList
import com.example.tonwalletapp.presentation.component.TransactionsLoadingSpinner
import com.example.tonwalletapp.presentation.component.WalletJustCreatedSplash
import com.example.tonwalletapp.ui.theme.AppTheme

@Composable
fun TransactionsListView(
    isLoading:Boolean,
    txt:List<TransactionDetail>?,
    justCreatedWallet:Boolean,
    walletAddress:String?,
    onTxtClick:(Int) -> Unit
) {

        if(isLoading){
            Column(
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TransactionsLoadingSpinner(
                    tonIconModifier = Modifier
                        .width(46.dp)
                        .height(45.dp),
                    dollarIconModifier = Modifier
                        .width(39.dp)
                        .height(51.dp),
                    arrowIconModifier = Modifier
                        .width(40.dp)
                        .height(38.dp)
                )
            }
        }
    LazyColumn(
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            itemsIndexed(txt ?: listOf()){ index, item ->
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onTxtClick(index)
                    }
                ) {
                    TransactionItemList(transactionDetail = item)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(AppTheme.colors.secondFormColor)
                )
            }
        }
        if(justCreatedWallet){
            WalletJustCreatedSplash(address = walletAddress!!)
        }
    }
