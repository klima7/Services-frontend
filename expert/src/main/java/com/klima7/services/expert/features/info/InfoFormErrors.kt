package com.klima7.services.expert.features.info

class InfoFormErrors {
    enum class NameError { NotProvided }
    enum class PhoneError { TooShort }
    enum class EmailError { InvalidFormat }
    enum class WebsiteError { InvalidFormat }
}
