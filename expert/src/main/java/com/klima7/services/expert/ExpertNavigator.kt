package com.klima7.services.expert

import android.app.Activity
import android.content.Intent
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.login.LoginActivity
import com.klima7.services.expert.features.setup.SetupActivity

class ExpertNavigator {

    fun showHomeScreen(activity: Activity) {
        val intent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(intent)
    }

    fun showLoginScreen(activity: Activity) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    fun showSetupScreen(activity: Activity) {
        val intent = Intent(activity, SetupActivity::class.java)
        activity.startActivity(intent)
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