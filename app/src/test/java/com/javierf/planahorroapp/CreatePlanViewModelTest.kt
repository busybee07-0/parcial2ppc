import androidx.lifecycle.ViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// ---------------------------
//  ViewModel para prueba
// ---------------------------
class CreatePlanViewModel : ViewModel() {

    var planName: String = ""
        private set

    var goal: Int = 0
        private set

    var isValid: Boolean = false
        private set

    fun updateName(name: String) {
        planName = name
        validate()
    }

    fun updateGoal(goalValue: String) {
        goal = goalValue.toIntOrNull() ?: 0
        validate()
    }

    private fun validate() {
        isValid = planName.isNotBlank() && goal > 0
    }
}

// ---------------------------
//  PRUEBAS UNITARIAS
// ---------------------------
class CreatePlanViewModelTest {

    @Test
    fun `updateName actualiza correctamente el nombre del plan`() {
        val vm = CreatePlanViewModel()

        vm.updateName("Ahorro Viaje")

        assertEquals("Ahorro Viaje", vm.planName)
    }

    @Test
    fun `updateGoal convierte correctamente el valor a entero`() {
        val vm = CreatePlanViewModel()

        vm.updateGoal("5000")

        assertEquals(5000, vm.goal)
    }

    @Test
    fun `validate retorna valido cuando hay nombre y meta`() {
        val vm = CreatePlanViewModel()

        vm.updateName("Plan 1")
        vm.updateGoal("10000")

        assertTrue(vm.isValid)
    }

    @Test
    fun `validate retorna falso cuando falta nombre`() {
        val vm = CreatePlanViewModel()

        vm.updateName("")
        vm.updateGoal("20000")

        assertEquals(false, vm.isValid)
    }

    @Test
    fun `validate retorna falso cuando la meta es cero o invalida`() {
        val vm = CreatePlanViewModel()

        vm.updateName("Plan 1")
        vm.updateGoal("abc") // convierte a 0

        assertEquals(false, vm.isValid)
    }
}
