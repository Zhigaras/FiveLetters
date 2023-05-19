package com.zhigaras.fiveletters.core

import com.zhigaras.fiveletters.R

abstract class FiveLettersException : Exception() {
    
    abstract val messageId: Int
    
}

class NetworkException : FiveLettersException() {
    override val messageId: Int = R.string.check_internet_connection
}

abstract class AuthException : FiveLettersException()

class RegistrationFailed : AuthException() {
    override val messageId = R.string.registration_failed
}

class SigningInFailed : AuthException() {
    override val messageId: Int = R.string.signing_in_failed
}

abstract class OneTapSignInException : AuthException()

class CouldNotStartOneTapSignIn : OneTapSignInException() {
    override val messageId: Int = R.string.couldn_t_start_signing_in_with_google
}

class CouldNotGetCredentials : OneTapSignInException() {
    override val messageId: Int = R.string.couldn_t_get_credentials
}

class NoGoogleAccountsFound : OneTapSignInException() {
    override val messageId: Int = R.string.no_google_accounts_found
}

class TokenNotReceived : OneTapSignInException() {
    override val messageId: Int = R.string.token_not_received
}
