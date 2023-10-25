package com.example.tonwalletapp.data.remote

import com.example.tonwalletapp.data.database.entity.WalletEntity
import com.example.tonwalletapp.data.remote.model.TransactionDetailTon
import com.example.tonwalletapp.data.remote.model.WalletTon

interface TonModule {
    suspend fun makeTransfer(walletTon: WalletTon, address:String, amount:Double)
    suspend fun getWalletTransaction(address: String):List<TransactionDetailTon>
    suspend fun getWalletBalance(address: String):Float
}