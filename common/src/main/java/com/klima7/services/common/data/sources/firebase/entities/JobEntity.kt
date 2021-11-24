package com.klima7.services.common.data.sources.firebase.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class JobEntity(
    var clientId: String = "",
    var clientName: String = "",
    var creation: Timestamp = Timestamp.now(),
    var description: String = "",
    var location: LocationEntity? = null,
    var realizationTime: String = "",
    var serviceName: String = "",
    var serviceId: String = "",
    var active: Boolean = false,
) {

    data class LocationEntity(
        var coordinates: GeoPoint = GeoPoint(0.0, 0.0),
        var locationName: String = "",
        var locationId: String = "",
    )

}
