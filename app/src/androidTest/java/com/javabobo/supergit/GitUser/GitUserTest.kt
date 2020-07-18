package com.javabobo.supergit.GitUser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.javabobo.supergit.persistence.DBUser
import com.javabobo.supergit.utils.LiveDataTestUtil
import com.javabobo.supergit.utils.SuperGitDatabaseTest
import com.javabobo.supergit.utils.TestUtils
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class GitUserTest : SuperGitDatabaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun test_git_user_is_inserted_in_the_database() {
        val git_user_1 =  TestUtils.GIT_USER_1
        gitRepoDao.insertUser(git_user_1)
        val liveDataTestUtil = LiveDataTestUtil<List<DBUser>>()
        val insertedPupil = liveDataTestUtil.getValue(gitRepoDao.getLocalUsers())
        checkNotNull(insertedPupil)
        assertEquals(git_user_1.username, insertedPupil[0].username)
        assertEquals(git_user_1.avatar_url, insertedPupil[0].avatar_url)
    }
}