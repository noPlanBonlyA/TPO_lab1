package lab1

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DomainModelNewTest {

    @Nested
    @DisplayName("Entity Tests")
    inner class EntityTests {

        @Test
        @DisplayName("Person creation works correctly")
        fun `test person creation`() {
            val person = Person("Алиса", "ничего не делает")
            assertNotNull(person)
            assertEquals("Алиса", person.name)
            assertEquals("ничего не делает", person.state)
        }

        @Test
        @DisplayName("Place creation works correctly")
        fun `test place creation`() {
            val place = Place("Лес", "Густой и таинственный")
            assertNotNull(place)
            assertEquals("Лес", place.name)
            assertEquals("Густой и таинственный", place.description)
            assertEquals("", place.state)
        }

        @Test
        @DisplayName("Thing creation works correctly")
        fun `test thing creation`() {
            val thing = Thing("Меч", "острый")
            assertNotNull(thing)
            assertEquals("Меч", thing.name)
            assertEquals("острый", thing.state)
        }
    }

    @Nested
    @DisplayName("Action Tests")
    inner class ActionTests {

        @Test
        @DisplayName("MoveAction updates Person state correctly")
        fun `test move action`() {
            val person = Person("Боб", "ожидает")
            val place = Place("Город", "оживлённый")
            val moveAction = MoveAction(person, place)
            val result = moveAction.execute()

            assertEquals("Боб перемещается в Город", result)
            assertEquals("перемещается в Город", person.state)
        }

        @Test
        @DisplayName("InteractAction updates states correctly")
        fun `test interact action`() {
            val person = Person("Чарли", "ожидает")
            val thing = Thing("Книга", "неиспользована")
            val interactAction = InteractAction(person, thing)
            val result = interactAction.execute()

            assertEquals("Чарли взаимодействует с Книга", result)
            assertEquals("взаимодействует с Книга", person.state)
            assertEquals("использован персонажем Чарли", thing.state)
        }

        @Test
        @DisplayName("IlluminateAction updates Person, Thing and Environment states correctly")
        fun `test illuminate action`() {
            val person = Person("Диана", "в темноте")
            val flashlight = Thing("Фонарь", "выключен")
            val environment = Place("Пещера", "темная")
            val illuminateAction = IlluminateAction(person, flashlight, environment)
            val result = illuminateAction.execute()

            assertEquals("Диана освещает Фонарь и изменяет обстановку в Пещера", result)
            assertEquals("освещает Фонарь", person.state)
            assertEquals("включен", flashlight.state)
            assertEquals("Освещенная Пещера, мрак рассеян", environment.state)
        }
    }

    @Nested
    @DisplayName("Story DSL Tests")
    inner class StoryDslTests {

        @Test
        @DisplayName("DSL adds actions and prints narrative correctly")
        fun `test story DSL output`() {
            val person = Person("Ева", "стоит")
            val place = Place("Замок", "старый и заброшенный")
            val thing = Thing("Ключ", "неиспользован")

            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val originalOut = System.out
            System.setOut(printStream)
            try {
                story {
                    addAction(MoveAction(person, place))
                    addAction(InteractAction(person, thing))
                }
            } finally {
                System.setOut(originalOut)
            }

            val output = outputStream.toString().trim().lines()
            assertTrue(output.any { it.contains("Ева перемещается в Замок") })
            assertTrue(output.any { it.contains("Ева взаимодействует с Ключ") })
            assertEquals("взаимодействует с Ключ", person.state)
            assertEquals("использован персонажем Ева", thing.state)
        }

        @Test
        @DisplayName("Story narrate method produces full narrative")
        fun `test story narrate`() {
            val person = Person("Фрэнк", "начальное состояние")
            val place = Place("Город", "оживлённый")
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val originalOut = System.out
            System.setOut(printStream)
            try {
                val storyInstance = Story()
                storyInstance.addAction(MoveAction(person, place))
                storyInstance.narrate()
            } finally {
                System.setOut(originalOut)
            }
            val output = outputStream.toString()
            assertTrue(output.contains("Нарратив истории:"))
            assertTrue(output.contains("Фрэнк перемещается в Город"))
        }
    }

    @Nested
    @DisplayName("Full Domain Scenario Test")
    inner class FullScenarioTest {

        @Test
        @DisplayName("Full scenario runs without errors and updates all states correctly")
        fun `test full domain scenario`() {
            // Создаем основные сущности
            val zafod = Person("Зафод", "осматривается")
            val marvin = Person("Марвин", "ждёт команды")
            val cave = Place("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")
            val whale = Thing("Кит", "упал, образовав провал")
            val debris = Thing("Обломки", "завалены")
            val flashlight = Thing("Фонарь", "выключен")

            // Перенаправляем вывод, чтобы DSL блок не засорял консоль
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val originalOut = System.out
            System.setOut(printStream)
            try {
                story {
                    addAction(MoveAction(zafod, cave))
                    addAction(InteractAction(marvin, debris))
                    addAction(InteractAction(zafod, flashlight))
                    addAction(IlluminateAction(zafod, flashlight, cave))
                }
            } finally {
                System.setOut(originalOut)
            }

            assertEquals("освещает Фонарь", zafod.state)
            assertEquals("взаимодействует с Обломки", marvin.state)
            assertEquals("использован персонажем Марвин", debris.state)
            assertEquals("включен", flashlight.state)
            assertEquals("Освещенная Пещера, мрак рассеян", cave.state)
        }
    }
}
