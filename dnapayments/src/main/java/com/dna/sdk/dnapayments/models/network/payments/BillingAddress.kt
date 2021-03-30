package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep

@Keep
class BillingAddress(
    val firstName: String?,
    val lastName: String?,
    val streetAddress1: String?,
    val streetAddress2: String?,
    val postalCode: String?,
    val city: String?,
    val country: String?
) {
    @Keep
    data class Builder(
        var firstName: String? = null,
        var lastName: String? = null,
        var streetAddress1: String? = null,
        var streetAddress2: String? = null,
        var postalCode: String? = null,
        var city: String? = null,
        var country: String? = null
    ) {

        fun setFirstName(firstName: String) = apply { this.firstName = firstName }
        fun setLastName(lastName: String) = apply { this.lastName = lastName }
        fun setStreetAddress1(streetAddress1: String) =
            apply { this.streetAddress1 = streetAddress1 }

        fun setStreetAddress2(streetAddress2: String) =
            apply { this.streetAddress2 = streetAddress2 }

        fun setPostalCode(postalCode: String) = apply { this.postalCode = postalCode }
        fun setCity(city: String) = apply { this.city = city }
        fun setCountry(country: String) = apply { this.country = country }

        fun build() = BillingAddress(
            firstName,
            lastName,
            streetAddress1,
            streetAddress2,
            postalCode,
            city,
            country
        )
    }
}