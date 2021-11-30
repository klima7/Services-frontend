package com.klima7.services.common.components.offer

import androidx.databinding.ViewDataBinding
import com.klima7.services.common.platform.BaseFragment

abstract class BaseOfferFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    abstract override val viewModel: BaseOfferViewModel

}
