package com.klima7.services.common.data.firebase.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class JobEntity(
    var clientId: String? = null,
    var clientName: String = "",
    var creation: Timestamp = Timestamp.now(),
    var description: String = "",
    var location: LocationEntity? = null,
    var realizationTime: String = "",
    var serviceName: String = "",
    var serviceId: String = "",
    var active: Boolean = false,
    var finishDate: Timestamp = Timestamp.now(),
    var unreadOffers: List<String> = listOf(),
) {

    data class LocationEntity(
        var coordinates: GeoPoint = GeoPoint(0.0, 0.0),
        var locationName: String = "",
        var locationId: String = "",
    )

}
