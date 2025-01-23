import com.example.gamblingapp.data.User
import com.example.gamblingapp.data.UsersRepository
import com.example.gamblingapp.ui.GamblingAppViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RouletteViewModelSimpleTest {

    private lateinit var viewModel: GamblingAppViewModel
    private lateinit var usersRepository: UsersRepository

    @Before
    fun setUp() = runTest {
        usersRepository = mock()

        val mockUser = User(
            email = "JohnSmith@gmail.com",
            password = "JohnSmith123",
            fullName = "John Smith",
            balance = 1000F,
            birthDate = "10/02/2000",
            pesel = "12312312365"
        )

        // Mocking user repository methods
        whenever(usersRepository.getUserStream("JohnSmith@gmail.com", "JohnSmith123"))
            .thenReturn(flow { emit(mockUser) })
        whenever(usersRepository.doesUserExist("JohnSmith@gmail.com", "JohnSmith123"))
            .thenReturn(true)

        viewModel = GamblingAppViewModel(usersRepository)
    }

    @Test
    fun `onRouletteBetChange updates chosenRouletteBet`() = runTest {
        // Simulate a successful login
        val loginResult = viewModel.getUserData()
        assertEquals(true, loginResult) // Ensure login is successful

        // Given
        val newBet = "200"

        // When
        viewModel.onRouletteBetChange(newBet)

        // Then
        assertEquals(newBet, viewModel.appState.value.chosenRouletteBet)
    }
}
