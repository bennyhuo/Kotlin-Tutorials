package com.bennyhuo.kotlin.update16.sealedwhen

/**
 * Created by benny.
 */
sealed class Contact {
    data class PhoneCall(val number: String) : Contact()
    data class TextMessage(val number: String) : Contact()
}

//fun Contact.messageCost(): Int =
//    when (this) { // Error: 'when' expression must be exhaustive
//        is Contact.PhoneCall -> 42
//    }

fun sendMessage(contact: Contact, message: String) {
    // Starting with 1.6.0

    // Warning: Non exhaustive 'when' statements on Boolean will be
    // prohibited in 1.7, add 'false' branch or 'else' branch instead
    when (message.isEmpty()) {
        true -> return
    }
    // Warning: Non exhaustive 'when' statements on sealed class/interface will be
    // prohibited in 1.7, add 'is TextMessage' branch or 'else' branch instead
    when (contact) {
        is Contact.PhoneCall -> TODO()
    }
}