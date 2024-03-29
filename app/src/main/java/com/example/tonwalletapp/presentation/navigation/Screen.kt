package com.example.tonwalletapp.presentation.navigation

sealed class Screen(val route:String) {
    object StartScreen:Screen("start_screen")
    object CongratsScreen:Screen("congrats_screen")
    object RecoveryPhraseScreen:Screen("recovery_phrase_screen")
    object TestTimeScreen:Screen("test_time_screen")
    object SuccessScreen:Screen("success_screen")
    object SetPasswordScreen:Screen("set_password_screen")
    object ReadyToGoScreen:Screen("ready_to_go_screen")
    object ImportPhraseScreen:Screen("import_phrase_screen")
    object ImportSuccessScreen:Screen("import_success_screen")
    object WrongPhraseScreen:Screen("wrong_phrase_screen")
    object MainWalletScreen:Screen("main_wallet_screen")
    object SplashScreen:Screen("splash_screen")
    object SettingsScreen:Screen("settings_screen")
    object EnterPassCodeScreen:Screen("enter_passcode_screen")
    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}