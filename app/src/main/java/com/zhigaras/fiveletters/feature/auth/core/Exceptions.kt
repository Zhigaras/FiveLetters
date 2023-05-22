package com.zhigaras.fiveletters.feature.auth.core

import com.zhigaras.fiveletters.R

abstract class FiveLettersException : Exception() {
    
    abstract val messageId: Int
    
}

class NetworkException : FiveLettersException() {
    override val messageId: Int = R.string.check_internet_connection
}

abstract class AuthException : FiveLettersException()

class NoSuchUser : AuthException() {
    override val messageId = R.string.no_such_user
}

abstract class RegistrationException : AuthException()

class RegistrationFailed : RegistrationException() {
    override val messageId = R.string.registration_failed
}

class EmailIsUsed : RegistrationException() {
    override val messageId: Int = R.string.email_is_used
}

abstract class LoginException : AuthException()

class SigningInFailed : LoginException() {
    override val messageId: Int = R.string.signing_in_failed
}

class InvalidUser : LoginException() {
    override val messageId: Int = R.string.invalid_credentials
}

class InvalidCredentials : LoginException() {
    override val messageId: Int = R.string.invalid_password
}

class TooManyRequests : LoginException() {
    override val messageId: Int = R.string.account_disabled
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

class OneTapSignInCanceled : OneTapSignInException() {
    override val messageId: Int = R.string.signing_in_with_google_canceled
}

class TooManyOneTapRequests : OneTapSignInException() {
    override val messageId: Int = R.string.too_many_google_signIn_requests
}
