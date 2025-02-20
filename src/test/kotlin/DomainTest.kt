package lab1

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DomainModelTest {

    @Test
    fun testCharacterCreation() {
        val zaфod = Character("Зафод", "расчищает проход")
        assertNotNull(zaфod)
        assertEquals("Зафод", zaфod.name)
        assertEquals("расчищает проход", zaфod.action)
    }

    @Test
    fun testLocationCreation() {
        val cave = Location("Пещера", "Сеть галерей и переходов, заваленных обломками и потрохами")
        assertNotNull(cave)
        assertEquals("Пещера", cave.name)
        assertEquals("Сеть галерей и переходов, заваленных обломками и потрохами", cave.description)
    }

    @Test
    fun testObjectCreation() {
        val whale = Object("Кит", "упал, образовав провал")
        assertNotNull(whale)
        assertEquals("Кит", whale.name)
        assertEquals("упал, образовав провал", whale.state)
    }

    @Test
    fun testEnvironmentCreation() {
        val depths = Environment("Сырой воздух из темных глубин, пыльный мрак")
        assertNotNull(depths)
        assertEquals("Сырой воздух из темных глубин, пыльный мрак", depths.description)
    }

    @Test
    fun testFullDomainModel() {
        createDomainModel()
        assertTrue(true)
    }
}