package com.klima7.services.common.di

import com.klima7.services.common.components.areavisual.AreaVisualizationViewModel
import com.klima7.services.common.components.comment.CommentViewModel
import com.klima7.services.common.components.ratings.RatingsViewModel
import com.klima7.services.common.components.faildialog.FailureDialogViewModel
import com.klima7.services.common.components.login.LoginViewModel
import com.klima7.services.common.components.msgsend.SendMessageViewModel
import com.klima7.services.common.components.msgviewer.MessageViewerViewModel
import com.klima7.services.common.components.profile.area.ProfileAreaViewModel
import com.klima7.services.common.components.profile.comments.ProfileCommentsLatestViewModel
import com.klima7.services.common.components.profile.comments.ProfileCommentsViewModel
import com.klima7.services.common.components.profile.contact.ProfileContactViewModel
import com.klima7.services.common.components.profile.description.ProfileDescriptionViewModel
import com.klima7.services.common.components.profile.header.ProfileHeaderViewModel
import com.klima7.services.common.components.profile.rating.ProfileRatingViewModel
import com.klima7.services.common.components.profile.services.ProfileServicesViewModel
import com.klima7.services.common.components.yesnodialog.YesNoDialogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { FailureDialogViewModel() }
    viewModel { YesNoDialogViewModel() }
    viewModel { ProfileServicesViewModel(get()) }
    viewModel { AreaVisualizationViewModel() }
    viewModel { ProfileContactViewModel() }
    viewModel { ProfileHeaderViewModel() }
    viewModel { ProfileDescriptionViewModel() }
    viewModel { ProfileRatingViewModel() }
    viewModel { ProfileCommentsLatestViewModel(get()) }
    viewModel { ProfileCommentsViewModel() }
    viewModel { RatingsViewModel(get()) }
    viewModel { ProfileAreaViewModel() }
    viewModel { LoginViewModel() }
    viewModel { SendMessageViewModel(get(), get(), get()) }
    viewModel { MessageViewerViewModel(get()) }
    viewModel { CommentViewModel(get()) }

}
