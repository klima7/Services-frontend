package com.klima7.services.expert

import android.app.Activity
import android.content.Intent
import com.klima7.services.expert.features.home.HomeActivity

class ExpertNavigator {

    fun showLoginScreen() {

    }

    fun showHomeScreen(activity: Activity) {
        val intent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(intent)
    }

    fun showSetupScreen() {

    }

    fun showNoInternetScreen() {

    }

    fun showJobDetailsScreen() {

    }

    fun showOfferScreen() {

    }

    fun showRatingScreen() {

    }

    fun showCommentsScreen() {

    }

    fun showSettingsScreen() {

    }

    fun showModifyInfoScreen() {

    }

    fun showModifyServicesScreen() {

    }

    fun showModifyLocationScreen() {

    }

}