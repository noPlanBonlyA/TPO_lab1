package lab1

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DomainModelTest {

    @Nested
    @DisplayName("Character Tests")
    inner class CharacterTests {
        @Test
        @DisplayName("Character creation works correctly")
        fun `test character creation`() {
            val zafod = Character("Зафод", "расчищает проход")
            assertNotNull(zafod)
            assertEquals("Зафод", zafod.name)
            assertEquals("расчищает проход", zafod.action)
        }
    }

    @Nested
    @DisplayName("Location Tests")
    inner class LocationTests {
        @Test
        @DisplayName("Location creation works correctly")
        fun `test location creation`() {
            val cave = Location("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")
            assertNotNull(cave)
            assertEquals("Пещера", cave.name)
            assertEquals("Сеть галерей и переходов, заваленных обломками и потрохами", cave.description)
        }
    }

    @Nested
    @DisplayName("Object Tests")
    inner class ObjectTests {
        @Test
        @DisplayName("Object creation works correctly")
        fun `test object creation`() {
            val whale = Object("Кит", "упал, образовав провал")
            assertNotNull(whale)
            assertEquals("Кит", whale.name)
            assertEquals("упал, образовав провал", whale.state)
        }
    }

    @Nested
    @DisplayName("Environment Tests")
    inner class EnvironmentTests {
        @Test
        @DisplayName("Environment creation works correctly")
        fun `test environment creation`() {
            val depths = Environment("Сырой воздух из темных глубин, пыльный мрак")
            assertNotNull(depths)
            assertEquals("Сырой воздух из темных глубин, пыльный мрак", depths.description)
        }
    }

    @Nested
    @DisplayName("Character Actions")
    inner class CharacterActions {
        @Test
        @DisplayName("Character moves to location correctly")
        fun `test character move to location`() {
            val zafod = Character("Зафод", "ждёт команды")
            val cave = Location("Пещера", "Заваленная проходами")
            zafod.moveTo(cave)
            assertEquals("Зафод перемещается в Пещера", zafod.action)
        }

        @Test
        @DisplayName("Character interacts with object correctly")
        fun `test character interact with object`() {
            val marvin = Character("Марвин", "ждёт команды")
            val debris = Object("Обломки", "завалены")
            marvin.interactWith(debris)
            assertEquals("Марвин взаимодействует с Обломки", marvin.action)
            assertEquals("использован персонажем Марвин", debris.state)
        }
    }

    @Nested
    @DisplayName("Object Behavior")
    inner class ObjectBehavior {
        @Test
        @DisplayName("Object state changes correctly")
        fun `test object state change`() {
            val flashlight = Object("Фонарь", "выключен")
            flashlight.changeState("включен")
            assertEquals("включен", flashlight.state)
        }
    }

    @Nested
    @DisplayName("Environment Behavior")
    inner class EnvironmentBehavior {
        @Test
        @DisplayName("Environment updates correctly")
        fun `test environment update`() {
            val environment = Environment("Темно и сыро")
            environment.update("Освещенная пещера, мрак рассеян")
            assertEquals("Освещенная пещера, мрак рассеян", environment.description)
        }
    }

    @Nested
    @DisplayName("Full Scenario Test")
    inner class FullScenario {
        @Test
        @DisplayName("Full scenario runs without errors")
        fun `test full scenario`() {
            createDomainModel()
            assertTrue(true)
        }
    }
}
