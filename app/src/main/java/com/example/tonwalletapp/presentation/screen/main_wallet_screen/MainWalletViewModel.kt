package com.example.tonwalletapp.presentation.screen.main_wallet_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tonwalletapp.domain.mapper.toFormattedAddress
import com.example.tonwalletapp.domain.usecase.wallet_usecase.UseGetTransactionFee
import com.example.tonwalletapp.domain.usecase.wallet_usecase.UseGetTransactionsByAddress
import com.example.tonwalletapp.domain.usecase.wallet_usecase.UseGetWalletInfo
import com.example.tonwalletapp.domain.usecase.wallet_usecase.UseGetWallets
import com.example.tonwalletapp.until.Constants.IS_NOT_AUTHORIZED
import com.example.tonwalletapp.until.Constants.TRANSACTIONS_BOTTOM_SHEET_CONTENT
import com.example.tonwalletapp.until.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainWalletViewModel @Inject constructor(
    private val useGetWallets: UseGetWallets,
    private val useGetWalletInfo: UseGetWalletInfo,
    private val useGetTransactionsByAddress: UseGetTransactionsByAddress,
    private val useGetTransactionFee: UseGetTransactionFee
):ViewModel() {

    private val _walletUIState = MutableStateFlow(WalletUIState())
    val walletUIState:StateFlow<WalletUIState> = _walletUIState

    private val _transactionsUIState = MutableStateFlow(TransactionsUIState())
    val transactionsUIState:StateFlow<TransactionsUIState> = _transactionsUIState

    private val _walletAddressUIState = MutableStateFlow<String?>(null)
    val walletAddressUIState:StateFlow<String?> = _walletAddressUIState

    private val _currentBottomSheetContentUIState = MutableStateFlow(TRANSACTIONS_BOTTOM_SHEET_CONTENT)
    val currentBottomSheetContentUIState:StateFlow<Int> = _currentBottomSheetContentUIState

    init {
         setupMainWallet()
    }

    private fun setupMainWallet(){
        _walletUIState.value = WalletUIState(isLoading = true)
        useGetWallets.invoke().onEach {res ->
            when(res){
                is Resource.Loading -> {
                    _walletUIState.value = WalletUIState(isLoading = true)
                    _transactionsUIState.value = TransactionsUIState(isLoading = true)
                }
                is Resource.Success -> {
                    val wallets = res.data
                    _walletAddressUIState.value = wallets!![0].address
                    getWalletDetailInfo()
                    getTransactions()
                }
                is Resource.Error -> {
                    _walletUIState.value = WalletUIState(error = res.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
    private fun getTransactions(){

        useGetTransactionsByAddress.invoke(walletAddressUIState.value!!).onEach {res ->
            when(res){
                is Resource.Loading -> {
                    _transactionsUIState.value = TransactionsUIState(isLoading = true)
                }
                is Resource.Success -> {
                    _transactionsUIState.value = TransactionsUIState(txt = res.data ?: listOf())
                }
                is Resource.Error -> {
                    _transactionsUIState.value = TransactionsUIState(error = res.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }

    fun changeBottomSheetContext(content:Int){
        _currentBottomSheetContentUIState.value = content
    }

    fun formatAddress(address:String):String{
        return address.toFormattedAddress()
    }

    private fun getWalletDetailInfo(){
        useGetWalletInfo.invoke(walletAddressUIState.value!!).onEach {res ->
            when(res){
                is Resource.Loading -> {
                    _walletUIState.value = WalletUIState(isLoading = true)
                }
                is Resource.Success -> {
                    _walletUIState.value = WalletUIState(walletDetail = res.data)
                }
                is Resource.Error -> {
                    _walletUIState.value = WalletUIState(error = res.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun getTxtFee(amount:Float):Float{
        return useGetTransactionFee.execute(amount = amount)
    }


}