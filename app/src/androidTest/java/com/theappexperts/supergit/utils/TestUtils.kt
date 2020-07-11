package com.theappexperts.supergit.utils

import android.net.Uri
import com.theappexperts.supergit.mappers.asDbMoodel
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.network.TransferModel.UserTransfer

object TestUtils {
    val GIT_USER_1 = GitUser(
        name = "robert",
        photo = Uri.Builder().scheme("http")
            .authority("www.clipart-library.com/data_images/320465.png")
            .build()
    ).asDbMoodel()

    val GIT_USER_2 = GitUser(
        name = "james",
        photo = Uri.Builder().scheme("http")
            .authority("www.clipart-library.com/data_images/320474.png")
            .build()
    ).asDbMoodel()

    val USER_TRANSFER_1 =
        UserTransfer(
            login = "robert",
            avatar_url = "http://www.clipart-library.com/data_images/320474.png"
        )
    val USER_TRANSFER_2 =
        UserTransfer(
            login = "ramon",
            avatar_url = "http://www.clipart-library.com/data_images/320474.png"
        )

    val TEST_0_GIT_USER_TRANSFERT = listOf(USER_TRANSFER_1, USER_TRANSFER_2)
    val TEST1_GIT_USER_LIST = listOf(GIT_USER_1, GIT_USER_2)
    val TEST2_GIT_USER_LIST = listOf(GIT_USER_1, GIT_USER_2)
}