package com.klima7.services.common.di

import com.klima7.services.common.components.areavisual.AreaVisualizationViewModel
import com.klima7.services.common.components.comments.CommentsViewModel
import com.klima7.services.common.components.faildialog.FailureDialogViewModel
import com.klima7.services.common.components.login.LoginViewModel
import com.klima7.services.common.components.profile.ProfileViewModel
import com.klima7.services.common.components.profile.area.ProfileAreaViewModel
import com.klima7.services.common.components.profile.comments.ProfileCommentsLatestViewModel
import com.klima7.services.common.components.profile.comments.ProfileCommentsViewModel
import com.klima7.services.common.components.profile.contact.ProfileContactViewModel
import com.klima7.services.common.components.profile.header.ProfileHeaderViewModel
import com.klima7.services.common.components.profile.rating.ProfileRatingViewModel
import com.klima7.services.common.components.profile.services.ProfileServicesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { FailureDialogViewModel() }
    viewModel { ProfileViewModel(get()) }
    viewModel { ProfileServicesViewModel(get()) }
    viewModel { AreaVisualizationViewModel() }
    viewModel { ProfileContactViewModel() }
    viewModel { ProfileHeaderViewModel() }
    viewModel { ProfileRatingViewModel() }
    viewModel { ProfileCommentsLatestViewModel(get()) }
    viewModel { ProfileCommentsViewModel() }
    viewModel { CommentsViewModel(get()) }
    viewModel { ProfileAreaViewModel() }
    viewModel { LoginViewModel() }

}
