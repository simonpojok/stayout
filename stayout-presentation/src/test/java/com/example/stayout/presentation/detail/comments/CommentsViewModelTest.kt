package com.example.stayout.presentation.detail.comments

import androidx.lifecycle.SavedStateHandle
import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.domain.usecase.GetCommentsUseCase
import com.example.stayout.presentation.detail.PropertyDetailViewModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommentsViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val getCommentsUseCase: GetCommentsUseCase = mockk()
    private val postId = 42
    private val savedStateHandle = SavedStateHandle(mapOf(PropertyDetailViewModel.ARG_PROPERTY_ID to postId))

    private val comments =
        listOf(
            CommentDomainModel(id = 1, postId = postId, user = mockk(), body = "Great stay!"),
        )

    private lateinit var viewModel: CommentsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { getCommentsUseCase(postId) } returns Single.just(comments)
        viewModel = CommentsViewModel(getCommentsUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial load transitions to Success with comments`() {
        val state = viewModel.state.value as CommentsState.Success
        assertEquals(comments, state.comments)
    }

    @Test
    fun `load failure transitions to Error state`() {
        every { getCommentsUseCase(postId) } returns Single.error(RuntimeException("network error"))

        val failingVm = CommentsViewModel(getCommentsUseCase, savedStateHandle)
        val state = failingVm.state.value

        assertTrue(state is CommentsState.Error)
        assertEquals("network error", (state as CommentsState.Error).message)
    }

    @Test
    fun `Retry intent reloads comments`() {
        val refreshed = listOf(CommentDomainModel(id = 2, postId = postId, user = mockk(), body = "Loved it!"))
        every { getCommentsUseCase(postId) } returns Single.just(refreshed)

        viewModel.onIntent(CommentsIntent.Retry)

        assertEquals(refreshed, (viewModel.state.value as CommentsState.Success).comments)
    }
}
