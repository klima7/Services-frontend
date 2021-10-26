package com.klima7.services.common.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class JobEntity(
    var clientId: String = "",
    var clientName: String = "",
    var creation: Timestamp = Timestamp.now(),
    var description: String = "",
    var location: LocationEntity? = null,
    var preferredExpertsIds: List<String> = listOf(),
    var realizationTime: String = "",
    var serviceName: String = "",
    var serviceId: String = ""
) {

    data class LocationEntity(
        var coordinates: GeoPoint = GeoPoint(0.0, 0.0),
        var locationName: String = "",
        var locationId: String = "",
    )

}
